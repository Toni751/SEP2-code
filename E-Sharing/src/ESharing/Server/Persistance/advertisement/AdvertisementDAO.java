package ESharing.Server.Persistance.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.Message;

import java.util.List;

/**
 * The DAO interface for managing the advertisements database table, and the
 * tables related to it, ad unavailability, ad pictures and ad ratings
 * @version 1.0
 * @author Group1
 */
public interface AdvertisementDAO {

  /**
   * Adds a new advertisement to the database
   * @param advertisement the advertisement to be added
   * @return the id of the created advertisement, or 0 if it wasn't added to the database
   */
  int create(Advertisement advertisement);

  /**
   * Removes an advertisement at a given id from the database and returns a list
   * with the messages to be sent to the users with reservations
   * @param id the id of the advertisement to be removed
   * @return the list with messages to be sent to the users with reservations
   */
  List<Message> removeAdvertisement(int id);

  /**
   * Adds the images paths and the unavailability dates for the advertisement
   * to the ad unavailability and ad pictures tables
   * @param advertisement the given advertisement
   */
  void addImagesAndDates(Advertisement advertisement);

  /**
   * Retrieves all advertisements from the database
   * @return the list with all advertisements
   */
  List<Advertisement> getAllAdvertisements();

  /**
   * Makes a given advertisement approved in the database
   * @param id the id of the advertisement to be approved
   * @return the catalogue ad object with that advertisement
   */
  CatalogueAd approveAdvertisement(int id);

  /**
   * Retrieves the catalogue with all advertisements from the database
   * @return the list with all catalogue advertisements
   */
  List<CatalogueAd> getAdvertisementsCatalogue();

  /**
   * Retrieves from the database the advertisement with a given id
   * @param id the id of the advertisement to be retrieved
   * @return the advertisement with the given id, or null if it doesn't exist
   */
  Advertisement getAdvertisementById (int id);

  /**
   * Retrieves all the advertisements a user has posted
   * @param user_id the id of the user
   * @return the list with all the user's advertisements as catalogue ads, or an empty list if the user has no advertisements posted
   */
  List<CatalogueAd> getAdvertisementsByUser(int user_id);

  /**
   * Increments the number of reports for the advertisement at the given id
   * @param advertisementID the id of the reported advertisement
   * @return the number of reports the advertisement has
   */
  int addNewAdvertisementReport(int advertisementID);

  /**
   * Retrieves from the database the advertisements ad catalogue for the admin
   * @return the list with all ads as admin ad catalogue items
   */
  List<AdCatalogueAdmin> getAdminAdCatalogue();

  /**
   * Retrieves from the database the advertisement with the given id as an admin ad catalogue object
   * @param id the id of the advertisement to be retrieved
   * @return the advertisement as a admin ad catalogue object
   */
  AdCatalogueAdmin getAdminAdCatalogue(int id);

  /**
   * Adds a rating to the database
   * @param ad_id the id of the rated advertisement
   * @param user_id the id of the user who rated the advertisement
   * @param rating the rating
   * @return true if the rating was inserted successfully, false otherwise
   */
  boolean addRating (int ad_id, int user_id, double rating);

  /**
   * Retrieves the average rating for an advertisement with the given id
   * @param ad_id the id of the advertisement
   * @return the average rating for the ad, 0 if it has no ratings
   */
  double retrieveAdRating (int ad_id);

  /**
   * Retrieves the total number of advertisements from the database
   * @return the total number of advertisements in the database
   */
  int getAdvertisementsNumber();
}
