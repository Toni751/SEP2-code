package ESharing.Client.Model.AdvertisementModel;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AdvertisementModel extends PropertyChangeSubject
{
    boolean addNewAdvertisement(Advertisement advertisement);
    Map<String, byte[]> convertedImages(Map<String, File> images);
    boolean removeAdvertisement(int id);
    List<Advertisement> selectAllAdvertisements();

    boolean approveAdvertisement(int id);

    boolean reportAdvertisement(int advertisementID);

    List<CatalogueAd> getAllCatalogues();

    Advertisement getAdvertisement(int advertisementID);

    List<CatalogueAd> getAllCataloguesForUser(int user_id);

    List<AdCatalogueAdmin> getAllAdminCatalogues();
    boolean addRating (int ad_id, int user_id, double rating);
    double retrieveAdRating (int ad_id);
    int getAdvertisementNumber();
}
