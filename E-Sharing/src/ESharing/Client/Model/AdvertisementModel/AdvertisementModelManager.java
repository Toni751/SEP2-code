package ESharing.Client.Model.AdvertisementModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.advertisement.ClientAdvertisement;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Vehicles;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class from the model layer which contains all advertisement features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class AdvertisementModelManager implements AdvertisementModel{

    private ClientAdvertisement clientAdvertisement;
    private PropertyChangeSupport support;

    /**
     * A constructor sets fields and assigns events
     */
    public AdvertisementModelManager() {
        this.clientAdvertisement = ClientFactory.getClientFactory().getClientAdvertisement();
        support = new PropertyChangeSupport(this);
        clientAdvertisement.addPropertyChangeListener(Events.NEW_AD_REQUEST.toString(), this::newAdRequest);
        clientAdvertisement.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), this::newApprovedAd);
        clientAdvertisement.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
        clientAdvertisement.addPropertyChangeListener(Events.NEW_ADVERTISEMENT_REPORT.toString(), this::newReportReceived);
    }

    @Override
    public boolean addNewAdvertisement(Advertisement advertisement) {
        return clientAdvertisement.addAdvertisement(advertisement);
    }

    @Override
    public Map<String, byte[]> convertedImages(Map<String, File> images) {
        Map<String, byte[]> convertedImages = new HashMap<>();
        convertedImages.put(AdImages.MAIN_IMAGE.toString(),
                    convertImageFile(images.get(AdImages.MAIN_IMAGE.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE1.toString()))
            convertedImages.put(AdImages.SUB_IMAGE1.toString(),
                    convertImageFile(images.get(AdImages.SUB_IMAGE1.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE2.toString()))
            convertedImages.put(AdImages.SUB_IMAGE2.toString(),
                    convertImageFile(images.get(AdImages.SUB_IMAGE2.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE3.toString()))
            convertedImages.put(AdImages.SUB_IMAGE3.toString(),
                    convertImageFile(images.get(AdImages.SUB_IMAGE3.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE4.toString()))
            convertedImages.put(AdImages.SUB_IMAGE4.toString(),
                    convertImageFile(images.get(AdImages.SUB_IMAGE4.toString())));
        return convertedImages;
    }

    @Override
    public boolean removeAdvertisement(int id) {
        return clientAdvertisement.removeAdvertisement(id);
    }

    @Override
    public List<Advertisement> selectAllAdvertisements() {
        return clientAdvertisement.selectAllAdvertisement();
    }

    @Override
    public boolean approveAdvertisement(int id) {
        return clientAdvertisement.approveAdvertisement(id);
    }

    @Override
    public boolean reportAdvertisement(int advertisementID) {
        return clientAdvertisement.reportAdvertisement(advertisementID);
    }

    @Override
    public List<CatalogueAd> getAllCatalogues() {
        return clientAdvertisement.getAllCatalogues();
//        List<CatalogueAd> dummyAds = new ArrayList<>();
//        dummyAds.add(new CatalogueAd(1, "Title1", "Path1", 100, Vehicles.Car.toString()));
//        dummyAds.add(new CatalogueAd(2, "Title2", "Path2", 100, Vehicles.Scooter.toString()));
//        dummyAds.add(new CatalogueAd(3, "Title3", "Path3", 100, Vehicles.Bike.toString()));
//        dummyAds.add(new CatalogueAd(4, "Title4", "Path4", 100, Vehicles.Car.toString()));
//        dummyAds.add(new CatalogueAd(5, "Title5", "Path5", 100, Vehicles.Scooter.toString()));
//        dummyAds.add(new CatalogueAd(6, "Title6", "Path6", 100, Vehicles.Bike.toString()));
//        dummyAds.add(new CatalogueAd(7, "Title7", "Path7", 100, Vehicles.Car.toString()));
//        dummyAds.add(new CatalogueAd(8, "Title8", "Path8", 100, Vehicles.Scooter.toString()));
//        dummyAds.add(new CatalogueAd(9, "Title9", "Path9", 100, Vehicles.Bike.toString()));
//        return dummyAds;
    }

    @Override
    public Advertisement getAdvertisement(int advertisementID) {
        return clientAdvertisement.getAdvertisement(advertisementID);
    }

    @Override
    public List<CatalogueAd> getAllCataloguesForUser(int user_id) {
        return clientAdvertisement.getAllUserCatalogues(user_id);
    }

    @Override
    public List<AdCatalogueAdmin> getAllAdminCatalogues() {
        return clientAdvertisement.getAdminAdCatalogue();
    }

    @Override
    public boolean addRating(int ad_id, int user_id, double rating)
    {
        return clientAdvertisement.addRating(ad_id, user_id, rating);
    }

    @Override
    public double retrieveAdRating(int ad_id)
    {
        return clientAdvertisement.retrieveAdRating(ad_id);
    }

    @Override
    public int getAdvertisementNumber() {
        return clientAdvertisement.getAdvertisementNumber();
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
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Starts when new reportedAd event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newReportReceived(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new removedAd event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void adRemoved(PropertyChangeEvent propertyChangeEvent)
    {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new newApprovedAd event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newApprovedAd(PropertyChangeEvent propertyChangeEvent)
    {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new adRequest event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newAdRequest(PropertyChangeEvent propertyChangeEvent)
    {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Converts given file into byte array and returns that
     * @param image the given file
     * @return the converted file as a byte array
     */
    private byte[] convertImageFile(File image)
    {
        try {
            return Files.readAllBytes(image.toPath().toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
