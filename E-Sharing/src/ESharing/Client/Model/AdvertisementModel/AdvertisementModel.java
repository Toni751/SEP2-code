package ESharing.Client.Model.AdvertisementModel;

import ESharing.Shared.TransferedObject.Advertisement;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AdvertisementModel {

    boolean addNewAdvertisement(Advertisement advertisement);
    Map<String, byte[]> convertedImages(Map<String, File> images);
}
