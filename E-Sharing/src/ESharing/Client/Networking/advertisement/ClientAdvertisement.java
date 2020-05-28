package ESharing.Client.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface from networking layer which is responsible for sending and receiving requests related to advertisement to and form the server
 * and for sending/ receiving data to the server
 * @version 1.0
 * @author Group1
 */
public interface ClientAdvertisement extends PropertyChangeSubject {

    /**
     * Initializes a connection with the server
     */
    void initializeConnection();

    /**
     * Sends a request to add new advertisement
     * @param ad new advertisement
     * @return the result of the adding process
     */
    boolean addAdvertisement(Advertisement ad);

    /**
     * Sends a request to approve a given advertisement
     * @param id the id of the given advertisement
     * @return the result of the approving process
     */
    boolean approveAdvertisement(int id);

    /**
     * Sends a request to remove a given advertisement
     * @param id the id of the given advertisement
     * @return the result of the removing process
     */
    boolean removeAdvertisement(int id);

    /**
     * Returns a list with all advertisements existing in the system
     * @return the list with all advertisements existing in the system
     */
    List<Advertisement> selectAllAdvertisement();

    /**
     * Sends a request to report given advertisement
     * @param advertisementID the id of the given advertisement
     * @return the result of the reporting process
     */
    boolean reportAdvertisement(int advertisementID);

    /**
     * Returns a list of all ad catalogues
     * @return a list of all ad catalogues
     */
    List<CatalogueAd> getAllCatalogues();

    /**
     * Returns a list of all admin ad catalogues
     * @return a list of all admin ad catalogues
     */
    List<AdCatalogueAdmin> getAdminAdCatalogue();

    /**
     * Returns an advertisement by given id
     * @param advertisementID the given id
     * @return the advertisement object
     */
    Advertisement getAdvertisement(int advertisementID);

    /**
     * Returns a number of all advertisements
     * @return the number of all advertisements
     */
    int getAdvertisementNumber();

    /**
     * Registers all call backs from the server
     */
    void registerForCallBack();

    /**
     * Returns a list with ad catalogues for a given user
     * @param user_id the id of the given user
     * @return list with ad catalogues for a given user
     */
    List<CatalogueAd> getAllUserCatalogues(int user_id);

    /**
     * Sends a request for adding a rating for the advertisement
     * @param ad_id the id of the advertisement
     * @param user_id the if og the user
     * @param rating the rating
     * @return the result of the rating process
     */
    boolean addRating(int ad_id, int user_id, double rating);

    /**
     * Returns a rating for a given advertisement
     * @param ad_id the id of the advertisement
     * @return the rating for a given advertisement
     */
    double retrieveAdRating(int ad_id);
}
