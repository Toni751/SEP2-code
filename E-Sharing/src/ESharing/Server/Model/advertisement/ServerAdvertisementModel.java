package ESharing.Server.Model.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface for the server advertisement model
 * @version 1.0
 * @author Group1
 */
public interface ServerAdvertisementModel extends PropertyChangeSubject {

    /**
     * Adds the advertisement's pictures to the server resources and makes a
     * request to the DAO for adding the advertisement to the database
     * @param advertisement the advertisement to be added
     * @return true if the advertisement was successfully added to the database, false otherwise
     */
    boolean addAdvertisement(Advertisement advertisement);

    /**
     * Sends a request to the DAO for approving an advertisement
     * @param id the id of the advertisement to be approved
     * @return true if the advertisement was successfully made approved the database, false otherwise
     */
    boolean approveAdvertisement (int id);

    /**
     * Removes the advertisement's pictures from the server and send a request
     * to the DAO for removing the advertisement from the database
     * @param id the id of the advertisement to be removed
     * @return true if the advertisement was successfully removed, false otherwise
     */
    boolean removeAdvertisement(int id);

    /**
     * Retrieves all images from the DAO and sets the corresponding images for all advertisements
     * @return the list with all advertisements
     */
    List<Advertisement> selectAllAdvertisements();

    /**
     * Retrieves from the DAO the catalogue with all advertisements
     * @return the list with all advertisements as catalogue items
     */
    List<CatalogueAd> getAdvertisementsCatalogue();

    /**
     * Retrieves from the DAO the catalogue with all advertisements belonging to a user
     * @param id the id of the user
     * @return the list with all of the user's advertisements as catalogue items
     */
    List<CatalogueAd> getAdvertisementsCatalogueForUser(int id);

    /**
     * Retrieves from the DAO an advertisement by its id
     * @param id the id of the advertisement to be retrieved
     * @return the advertisement
     */
    Advertisement getAdvertisementById (int id);

    /**
     * Sends a request to the DAO to increment the advertisement's reports
     * @param advertisementID the id of the reported advertisement
     * @return true if the advertisement's reports were successfully updated, false otherwise
     */
    boolean addNewAdvertisementReport(int advertisementID);

    /**
     * Retrieves from the DAO the administrator's advertisements catalogue
     * @return the list with all advertisements as admin catalogue items
     */
    List<AdCatalogueAdmin> getAdminAdCatalogue();

    /**
     * Sends a request to the DAO to add a rating to a advertisement from a user
     * @param ad_id the id of the advertisement to be rated
     * @param user_id the id of the user who rated it
     * @param rating the rating
     * @return true if the rating was successfully added to the database, false otherwise
     */
    boolean addRating (int ad_id, int user_id, double rating);

    /**
     * Retrieves from the DAO the average rating of an advertisement
     * @param ad_id the id of the advertisement
     * @return the advertisement's average rating
     */
    double retrieveAdRating (int ad_id);

    /**
     * Retrieves from the DAO the total number of advertisements
     * @return the total number of advertisements
     */
    int getAdvertisementNumber();
}
