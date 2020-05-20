package ESharing.Client.Model.AdvertisementModel;

import ESharing.Shared.TransferedObject.Advertisement;
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
    List<Advertisement> selectAllAdvertisements();
}
