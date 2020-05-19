package ESharing.Server.Model.advertisement;

import ESharing.Server.Persistance.advertisement.AdvertisementDAO;
import ESharing.Server.Persistance.advertisement.AdvertisementDAOManager;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        Map<String, String> serverPaths = new HashMap<>();
        FileOutputStream outputStream;
        File mainImage;
        File subImage1;
        File subImage2;
        File subImage3;
        File subImage4;

        int result = advertisementDAO.create(advertisement);
        if(result != -1) {
            advertisement.setAdvertisementID(result);
            System.out.println(advertisement.getBytePhotos());
            try {
                mainImage = new File("E-Sharing/src/ESharing/Server/Resources/Advertisements/user" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement.getAdvertisementID() + "/mainImage.jpg");
                mainImage.getParentFile().mkdirs();
                outputStream = new FileOutputStream(mainImage);
                outputStream.write(advertisement.getBytePhotos().get(AdImages.MAIN_IMAGE.toString()));
                serverPaths.put(AdImages.MAIN_IMAGE.toString(), mainImage.getPath());
                if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE1.toString())) {
                    subImage1 = new File("E-Sharing/src/ESharing/Server/Resources/Advertisements/user" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement.getAdvertisementID() + "/subImage1.jpg");
                    outputStream = new FileOutputStream(subImage1);
                    outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE1.toString()));
                    serverPaths.put(AdImages.SUB_IMAGE1.toString(), subImage1.getPath());
                }
                if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE2.toString())) {
                    subImage2 = new File("E-Sharing/src/ESharing/Server/Resources/Advertisements/user" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement.getAdvertisementID() + "/subImage2.jpg");
                    outputStream = new FileOutputStream(subImage2);
                    outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE2.toString()));
                    serverPaths.put(AdImages.SUB_IMAGE2.toString(), subImage2.getPath());
                }
                if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE3.toString())) {
                    subImage3 = new File("E-Sharing/src/ESharing/Server/Resources/Advertisements/user" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement.getAdvertisementID() + "/subImage3.jpg");
                    outputStream = new FileOutputStream(subImage3);
                    outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE3.toString()));
                    serverPaths.put(AdImages.SUB_IMAGE3.toString(), subImage3.getPath());
                }
                if (advertisement.getBytePhotos().containsKey(AdImages.SUB_IMAGE4.toString())) {
                    subImage4 = new File("E-Sharing/src/ESharing/Server/Resources/Advertisements/user" + advertisement.getOwner().getUser_id() + "/advertisement" + advertisement.getAdvertisementID() + "/subImage4.jpg");
                    outputStream = new FileOutputStream(subImage4);
                    outputStream.write(advertisement.getBytePhotos().get(AdImages.SUB_IMAGE4.toString()));
                    serverPaths.put(AdImages.SUB_IMAGE4.toString(), subImage4.getPath());
                }
                outputStream.close();
                advertisement.setServerPath(serverPaths);
                advertisementDAO.addImagesAndDates(advertisement);
                support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, advertisement);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public void approveAdvertisement(Advertisement ad)
    {
        //send ad to be made approved in the database
        advertisementDAO.approveAdvertisement(ad);
        support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, ad);
    }

    @Override
    public boolean removeAdvertisement(Advertisement advertisement) {
        boolean result = advertisementDAO.removeAdvertisement(advertisement);
        if(result)
            support.firePropertyChange(Events.AD_REMOVED.toString(), null, advertisement);
        return result;
    }

    @Override
    public boolean editAdvertisement(Advertisement ad)
    {
        return false;
    }

    @Override
    public List<Advertisement> selectAllAdvertisements() {
       Map<String, byte[]> imagesByte = new HashMap<>();
       List<Advertisement> advertisements = advertisementDAO.getAllAdvertisements();
       for(Advertisement advertisement : advertisements) {
           try {
           for(int i = 0; i<advertisement.getServerPaths().size(); i++) {

                   advertisement.setImageByte(AdImages.MAIN_IMAGE.toString(), Files.readAllBytes(Paths.get(advertisement.getServerPath(AdImages.MAIN_IMAGE.toString()))));

           }
           return advertisements;

           } catch (IOException e) {
            e.printStackTrace(); }
       }
       return null;
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
}
