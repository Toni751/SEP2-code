package ESharing.Client.Model.AdvertisementModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.advertisement.ClientAdvertisement;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertisementModelManager implements AdvertisementModel{

    private ClientAdvertisement clientAdvertisement;

    public AdvertisementModelManager() {
        this.clientAdvertisement = ClientFactory.getClientFactory().getClientAdvertisement();
    }

    @Override
    public boolean addNewAdvertisement(Advertisement advertisement) {
        return clientAdvertisement.addAdvertisement(advertisement);
    }

    @Override
    public Map<String, byte[]> convertedImages(Map<String, File> images) {
        Map<String, byte[]> convertedImages = new HashMap<>();
        convertedImages.put(AdImages.MAIN_IMAGE.toString(), convertImageFile(images.get(AdImages.MAIN_IMAGE.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE1.toString()))
            convertedImages.put(AdImages.SUB_IMAGE1.toString(), convertImageFile(images.get(AdImages.SUB_IMAGE1.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE2.toString()))
            convertedImages.put(AdImages.SUB_IMAGE2.toString(), convertImageFile(images.get(AdImages.SUB_IMAGE2.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE3.toString()))
            convertedImages.put(AdImages.SUB_IMAGE3.toString(), convertImageFile(images.get(AdImages.SUB_IMAGE3.toString())));
        if(images.containsKey(AdImages.SUB_IMAGE4.toString()))
            convertedImages.put(AdImages.SUB_IMAGE4.toString(), convertImageFile(images.get(AdImages.SUB_IMAGE4.toString())));
        return convertedImages;
    }

    @Override
    public List<Advertisement> selectAllAdvertisements() {
        return clientAdvertisement.selectAllAdvertisement();
    }

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
