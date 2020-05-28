package ESharing.Client.Model.AdvertisementModel;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * The interface used to connect a view model layer with a model layer for all advertisement features.
 * @version 1.0
 * @author Group1
 */
public interface AdvertisementModel extends PropertyChangeSubject
{
    /**
     * Sends a request to create new advertisement to the networking layer
     * @param advertisement the advertisement object which was created by a user
     * @return the result of the adding process
     */
    boolean addNewAdvertisement(Advertisement advertisement);

    /**
     * Converts given images into a byte arrays
     * @param images the map with images
     * @return the map object with converted images
     */
    Map<String, byte[]> convertedImages(Map<String, File> images);

    /**
     * Sends a request to remove the given advertisement from the system
     * @param id the id of the given advertisement
     * @return the result of the removing process
     */
    boolean removeAdvertisement(int id);

    /**
     * Returns the list with all advertisements object existing in the system
     * @return the list of all advertisements existing in the system
     */
    List<Advertisement> selectAllAdvertisements();

    /**
     * Sends a request to approve current advertisement
     * @param id the id of the given advertisement
     * @return the result of the approving process
     */
    boolean approveAdvertisement(int id);

    /**
     * Sends a request to report current advertisement
     * @param advertisementID the id of the given advertisement
     * @return the result of the reporting process
     */
    boolean reportAdvertisement(int advertisementID);

    /**
     * Returns a list with all advertisement in shorter version
     * @return list with all advertisement in shorter version
     */
    List<CatalogueAd> getAllCatalogues();

    /**
     * Returns the advertisement object regarding the given id
     * @param advertisementID the if of the given advertisement
     * @return the advertisement object regarding the given id
     */
    Advertisement getAdvertisement(int advertisementID);

    /**
     * Gets all advertisement in shorter version for a given user
     * @param user_id the id of the given user
     * @return all advertisement in shorter version for a given user
     */
    List<CatalogueAd> getAllCataloguesForUser(int user_id);

    /**
     * Returns all advertisements existing in the system in form related with administrator. As a AdCatalogueAdmin objects.
     * @return all advertisements existing in the system in form related with administrator. As a AdCatalogueAdmin objects.
     */
    List<AdCatalogueAdmin> getAllAdminCatalogues();
    boolean addRating (int ad_id, int user_id, double rating);
    double retrieveAdRating (int ad_id);
    int getAdvertisementNumber();
}
