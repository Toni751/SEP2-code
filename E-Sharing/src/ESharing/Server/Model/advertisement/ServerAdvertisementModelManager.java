package ESharing.Server.Model.advertisement;

import ESharing.Server.Persistance.advertisement.AdvertisementDAO;
import ESharing.Server.Persistance.advertisement.AdvertisementDAOManager;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;
import jdk.jfr.Event;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The server model class for managing advertisements
 * @version 1.0
 * @author Group1
 */
public class ServerAdvertisementModelManager implements ServerAdvertisementModel{

    private AdvertisementDAO advertisementDAO;
    private PropertyChangeSupport support;
    private Lock lock;

    /**
     * One-argument constructor which initializes the advertisement DAO and the property change support instance
     * @param advertisementDAO the value to be set for the advertisement DAO
     */
    public ServerAdvertisementModelManager(AdvertisementDAO advertisementDAO)
    {
        this.advertisementDAO = advertisementDAO;
        support = new PropertyChangeSupport(this);
        lock = new ReentrantLock();
    }


    @Override
    public boolean addAdvertisement(Advertisement advertisement)
    {
        int result;
        synchronized (lock)
        {
            result = advertisementDAO.create(advertisement);
        }
        if(result != -1) {
                advertisement = uploadPhotos(advertisement, result);
            synchronized (lock)
            {
                advertisementDAO.addImagesAndDates(advertisement);
            }
            support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, advertisementDAO.getAdminAdCatalogue(advertisement.getAdvertisementID()));
                return true;
        }
        return false;
    }

    @Override
    public boolean approveAdvertisement(int id)
    {
        CatalogueAd catalogueAd;
        synchronized (lock)
        {
            catalogueAd = advertisementDAO.approveAdvertisement(id);
        }
        if(catalogueAd != null) {
            try
            {
                catalogueAd.setMainImage(Files.readAllBytes(new File(catalogueAd.getMainImageServerPath()).toPath()));
            } catch (IOException e) {e.printStackTrace();}
            System.out.println("Approved event fired");
            support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, catalogueAd);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int id) {
        List<Message> messages;
        synchronized (lock)
        {
            messages = advertisementDAO.removeAdvertisement(id);
        }
        if(messages != null) {
            // add removing part for pictures
            deleteDirectory(new File("E-Sharing/Resources/User" + messages.get(0).getSender().getUser_id() + "/Advertisement" + id));
            support.firePropertyChange(Events.AD_REMOVED.toString(), null, id);
            if(messages.size() == 1 && messages.get(0).getContent().equals(""))
                return true;
            for(Message message : messages) {
                support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getReceiver().getUser_id(), null, message);
                support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getSender().getUser_id(), null, message);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Advertisement> selectAllAdvertisements() {
        List<Advertisement> advertisements;
        synchronized (lock)
        {
            advertisements = advertisementDAO.getAllAdvertisements();
        }
        for(Advertisement advertisement : advertisements) {
             advertisement.setPhotos(convertAdvertisementPictures(advertisement));
       }
           return advertisements;
    }

    @Override
    public List<CatalogueAd> getAdvertisementsCatalogue()
    {
        List<CatalogueAd> catalogueAds;
        synchronized (lock)
        {
            catalogueAds = advertisementDAO.getAdvertisementsCatalogue();
        }
        if(catalogueAds != null) {
            for (CatalogueAd catalogueAd : catalogueAds) {
                String mainImageServerPath = catalogueAd.getMainImageServerPath();
                try {
                    catalogueAd.setMainImage(Files.readAllBytes(new File(mainImageServerPath).toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return catalogueAds;
    }

    @Override
    public List<CatalogueAd> getAdvertisementsCatalogueForUser(int id) {
        List<CatalogueAd> catalogues;
        synchronized (lock)
        {
            catalogues = advertisementDAO.getAdvertisementsByUser(id);
        }
        for(CatalogueAd catalogueAd : catalogues)
        {
            String mainImageServerPath = catalogueAd.getMainImageServerPath();
            try
            {
                catalogueAd.setMainImage(Files.readAllBytes(new File(mainImageServerPath).toPath()));
            } catch (IOException e) { e.printStackTrace(); }
        }
        return catalogues;
    }

    @Override
    public Advertisement getAdvertisementById(int id)
    {
        Advertisement advertisement;
        synchronized (lock)
        {
            advertisement = advertisementDAO.getAdvertisementById(id);
        }
        advertisement.getOwner().setAvatar(convertUserAvatar(advertisement.getOwner().getAvatarServerPath()));
        advertisement.setPhotos(convertAdvertisementPictures(advertisement));
        return advertisement;
    }

    @Override
    public boolean addNewAdvertisementReport(int advertisementID) {
        System.out.println("REPORT ADDED");
        int reports;
        synchronized (lock)
        {
            reports = advertisementDAO.addNewAdvertisementReport(advertisementID);
        }
        if(reports != -1) {
            support.firePropertyChange(Events.NEW_ADVERTISEMENT_REPORT.toString(), advertisementID, reports);
            return true;
        }
        return false;
    }

    @Override
    public List<AdCatalogueAdmin> getAdminAdCatalogue()
    {
        List<AdCatalogueAdmin> adminAdCatalogue;
        synchronized (lock)
        {
            adminAdCatalogue = advertisementDAO.getAdminAdCatalogue();
        }
        return adminAdCatalogue;
    }

    @Override
    public boolean addRating(int ad_id, int user_id, double rating)
    {
        boolean resultAddRating;
        synchronized (lock)
        {
            resultAddRating = advertisementDAO.addRating(ad_id, user_id, rating);
        }
        return resultAddRating;
    }

    @Override
    public double retrieveAdRating(int ad_id)
    {
        double adRating;
        synchronized (lock)
        {
            adRating = advertisementDAO.retrieveAdRating(ad_id);
        }
        return adRating;
    }

    @Override
    public int getAdvertisementNumber() {
        int advertisementsNumber;
        synchronized (lock)
        {
            advertisementsNumber = advertisementDAO.getAdvertisementsNumber();
        }
        return advertisementsNumber;
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Method for uploading the advertisement's photos on the server
     * @param advertisement the advertisement for which to upload photos
     * @param result the value of the advertisement's id
     * @return the updated advertisement, with the server paths set
     */
    private Advertisement uploadPhotos (Advertisement advertisement, int result)
    {
        Map<String, String> serverPaths = new HashMap<>();
        FileOutputStream outputStream;
        File mainImage;
        File subImage1;
        File subImage2;
        File subImage3;
        File subImage4;

        advertisement.setAdvertisementID(result);
        System.out.println(advertisement.getBytePhotos());
        try
        {
            mainImage = new File(
                "E-Sharing/Resources/User" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement
                    .getAdvertisementID() + "/mainImage.jpg");
            mainImage.getParentFile().mkdirs();
            outputStream = new FileOutputStream(mainImage);
            outputStream.write(advertisement.getBytePhotos().get(AdImages.MAIN_IMAGE.toString()));
            serverPaths.put(AdImages.MAIN_IMAGE.toString(), mainImage.getPath());
            if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE1.toString()))
            {
                subImage1 = new File(
                    "E-Sharing/Resources/User" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement
                        .getAdvertisementID() + "/subImage1.jpg");
                outputStream = new FileOutputStream(subImage1);
                outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE1.toString()));
                serverPaths.put(AdImages.SUB_IMAGE1.toString(), subImage1.getPath());
            }
            if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE2.toString()))
            {
                subImage2 = new File(
                    "E-Sharing/Resources/User" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement
                        .getAdvertisementID() + "/subImage2.jpg");
                outputStream = new FileOutputStream(subImage2);
                outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE2.toString()));
                serverPaths.put(AdImages.SUB_IMAGE2.toString(), subImage2.getPath());
            }
            if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE3.toString()))
            {
                subImage3 = new File(
                    "E-Sharing/Resources/User" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement
                        .getAdvertisementID() + "/subImage3.jpg");
                outputStream = new FileOutputStream(subImage3);
                outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE3.toString()));
                serverPaths.put(AdImages.SUB_IMAGE3.toString(), subImage3.getPath());
            }
            if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE4.toString()))
            {
                subImage4 = new File(
                    "E-Sharing/Resources/User" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement
                        .getAdvertisementID() + "/subImage4.jpg");
                outputStream = new FileOutputStream(subImage4);
                outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE4.toString()));
                serverPaths.put(AdImages.SUB_IMAGE4.toString(), subImage4.getPath());
            }
            outputStream.close();
            advertisement.setServerPath(serverPaths);
            return advertisement;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts an advertisement's server paths to the concrete images
     * @param advertisement the advertisement for which to retrieve the images
     * @return the advertisement's images
     */
    private Map<String, byte[]> convertAdvertisementPictures(Advertisement advertisement)
    {
        Map<String, byte[]> convertedImages = new HashMap<>();
        for(int i = 0; i < advertisement.getServerPaths().size(); i++) {
            System.out.println(advertisement.getServerPaths());
            try {
                convertedImages.put(AdImages.MAIN_IMAGE.toString(), Files.readAllBytes(new File(advertisement.getServerPath(AdImages.MAIN_IMAGE.toString())).toPath()));
                if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE1.toString()))
                    convertedImages.put(AdImages.SUB_IMAGE1.toString(), Files.readAllBytes(new File(advertisement.getServerPath(AdImages.SUB_IMAGE1.toString())).toPath()));
                if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE2.toString()))
                    convertedImages.put(AdImages.SUB_IMAGE2.toString(), Files.readAllBytes(new File(advertisement.getServerPath(AdImages.SUB_IMAGE2.toString())).toPath()));
                if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE3.toString()))
                    convertedImages.put(AdImages.SUB_IMAGE3.toString(), Files.readAllBytes(new File(advertisement.getServerPath(AdImages.SUB_IMAGE3.toString())).toPath()));
                if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE4.toString()))
                    convertedImages.put(AdImages.SUB_IMAGE4.toString(), Files.readAllBytes(new File(advertisement.getServerPath(AdImages.SUB_IMAGE4.toString())).toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertedImages;
    }

    /**
     * Retrieves from the server the user's avatar by the path
     * @param serverPath the server path of the avatar
     * @return the image contain the user's avatar
     */
    private byte[] convertUserAvatar(String serverPath)
    {
        try {
            return Files.readAllBytes(new File(serverPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes all the files/images inside a directory
     * @param directory the given directory
     * @return true if the files were deleted, false otherwise
     */
    private boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }

}
