package ESharing.Server.Model.advertisement;

import ESharing.Server.Persistance.advertisement.AdvertisementDAO;
import ESharing.Server.Persistance.advertisement.AdvertisementDAOManager;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;

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

public class ServerAdvertisementModelManager implements ServerAdvertisementModel{

    private AdvertisementDAO advertisementDAO;
    private PropertyChangeSupport support;

    public ServerAdvertisementModelManager(AdvertisementDAO advertisementDAO)
    {
        this.advertisementDAO = advertisementDAO;
        support = new PropertyChangeSupport(this);
    }


    @Override
    public boolean addAdvertisement(Advertisement advertisement) {

        int result = advertisementDAO.create(advertisement);
        if(result != -1) {
                advertisement = uploadPhotos(advertisement, result);
                advertisementDAO.addImagesAndDates(advertisement);
                support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, advertisementDAO.getAdminAdCatalogue(advertisement.getAdvertisementID()));
                return true;
        }
        return false;
    }

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

    @Override
    public boolean approveAdvertisement(int id)
    {
        CatalogueAd catalogueAd = advertisementDAO.approveAdvertisement(id);
        if(catalogueAd != null) {
            try
            {
                catalogueAd.setMainImage(Files.readAllBytes(new File(catalogueAd.getMainImageServerPath()).toPath()));
            } catch (IOException e) {e.printStackTrace();}
            System.out.println("Appropved event fired");
            support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, catalogueAd);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int id) {
        int ownerID = advertisementDAO.removeAdvertisement(id);
        if(ownerID != -1) {
            // add removing part for pictures
            deleteDirectory(new File("E-Sharing/Resources/User" + ownerID + "/Advertisement" + id));
            support.firePropertyChange(Events.AD_REMOVED.toString(), null, id);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean editAdvertisement(Advertisement ad)
//    {
//        boolean result = advertisementDAO.updateAdvertisement(ad);
//        if(result)
//            support.firePropertyChange(Events.AD_UPDATED.toString(), null, ad);
//        return result;
//    }

    @Override
    public List<Advertisement> selectAllAdvertisements() {
       List<Advertisement> advertisements = advertisementDAO.getAllAdvertisements();
       for(Advertisement advertisement : advertisements) {
             advertisement.setPhotos(convertAdvertisementPictures(advertisement));
       }
           return advertisements;
    }

    @Override
    public List<CatalogueAd> getAdvertisementsCatalogue()
    {
        List<CatalogueAd> catalogueAds = advertisementDAO.getAdvertisementsCatalogue();
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
        List<CatalogueAd> catalogues = advertisementDAO.getAdvertisementsByUser(id);
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
        Advertisement advertisement = advertisementDAO.getAdvertisementById(id);
        advertisement.getOwner().setAvatar(convertUserAvatar(advertisement.getOwner().getAvatarServerPath()));
        advertisement.setPhotos(convertAdvertisementPictures(advertisement));
        return advertisement;
    }

    @Override
    public boolean addNewAdvertisementReport(int advertisementID) {
        System.out.println("REPORT ADDED");
        int reports = advertisementDAO.addNewAdvertisementReport(advertisementID);
        if(reports != -1) {
            support.firePropertyChange(Events.NEW_ADVERTISEMENT_REPORT.toString(), advertisementID, reports);
            return true;
        }
        return false;
    }

    @Override
    public List<AdCatalogueAdmin> getAdminAdCatalogue()
    {
        return advertisementDAO.getAdminAdCatalogue();
    }

    @Override
    public boolean addRating(int ad_id, int user_id, double rating)
    {
        return advertisementDAO.addRating(ad_id, user_id, rating);
    }

    @Override
    public double retrieveAdRating(int ad_id)
    {
        return advertisementDAO.retrieveAdRating(ad_id);
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

    private byte[] convertUserAvatar(String serverPath)
    {
        try {
            return Files.readAllBytes(new File(serverPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
