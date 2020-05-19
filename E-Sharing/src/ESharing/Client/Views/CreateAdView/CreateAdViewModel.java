package ESharing.Client.Views.CreateAdView;
import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAdViewModel {

    private StringProperty titleProperty;
    private StringProperty typeProperty;
    private StringProperty priceProperty;
    private StringProperty descriptionProperty;
    private StringProperty warningProperty;
    private BooleanProperty warningVisibleProperty;

    private ObjectProperty<Image> mainImageProperty;
    private ObjectProperty<Image> subImage1Property;
    private ObjectProperty<Image> subImage2Property;
    private ObjectProperty<Image> subImage3Property;
    private ObjectProperty<Image> subImage4Property;

    private ObjectProperty<LocalDate> dateProperty;

    private VerificationModel verificationModel;
    private AdvertisementModel advertisementModel;

    private List<LocalDate> selectedDates;
    private Map<String, File> imageFiles;

    private final Image defaultImage = new Image("ESharing/Addition/Images/icons/upload.png");
    public CreateAdViewModel()
    {
        titleProperty = new SimpleStringProperty();
        typeProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        priceProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();

        mainImageProperty = new SimpleObjectProperty<>();
        subImage1Property = new SimpleObjectProperty<>();
        subImage2Property = new SimpleObjectProperty<>();
        subImage3Property = new SimpleObjectProperty<>();
        subImage4Property = new SimpleObjectProperty<>();
        dateProperty = new SimpleObjectProperty<>();

        imageFiles = new HashMap<>();
        selectedDates = new ArrayList<>();

        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
    }

    public ObjectProperty<Image> getMainImageProperty() {
        return mainImageProperty;
    }

    public void addAdvertisementRequest()
    {
        String verification = verificationModel.verifyAdvertisement(titleProperty.get(), typeProperty.get(), descriptionProperty.get(),priceProperty.get(), imageFiles.size());

        if(verification == null) {
            Map<String, byte[]> convertedImages = advertisementModel.convertedImages(imageFiles);
            double convertedPrice = Double.parseDouble(priceProperty.get());
            advertisementModel.addNewAdvertisement(new Advertisement(LoggedUser.getLoggedUser().getUser(), convertedImages, typeProperty.get(), selectedDates, convertedPrice, titleProperty.get(), descriptionProperty.get()));
        }
        else{
            warningProperty.setValue(verification);
            warningVisibleProperty.setValue(true);
        }
    }

    public void addImageFile(String imageId, File imageFile)
    {
        if(imageFiles.get(imageId) == null)
            imageFiles.put(imageId, imageFile);
        else
            imageFiles.replace(imageId, imageFile);
    }

    public void setImage(String id, Image image)
    {
        if(id.equals(AdImages.MAIN_IMAGE.toString()))
            mainImageProperty.setValue(image);
        else if(id.equals(AdImages.SUB_IMAGE1.toString()))
            subImage1Property.setValue(image);
        else if(id.equals(AdImages.SUB_IMAGE2.toString()))
            subImage2Property.setValue(image);
        else if(id.equals(AdImages.SUB_IMAGE3.toString()))
            subImage3Property.setValue(image);
        else if(id.equals(AdImages.SUB_IMAGE4.toString()))
            subImage4Property.setValue(image);
    }

    public void addNewSelectedDate()
    {
        LocalDate selectedDate = dateProperty.getValue();
        if(selectedDates.contains(selectedDate))
            selectedDates.remove(selectedDate);
        else
            selectedDates.add(dateProperty.getValue());
    }

    public List<LocalDate> getSelectedDates()
    {
        return selectedDates;
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    public StringProperty getPriceProperty() {
        return priceProperty;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public ObjectProperty<Image> getSubImage1Property() {
        return subImage1Property;
    }

    public ObjectProperty<Image> getSubImage2Property() {
        return subImage2Property;
    }

    public ObjectProperty<Image> getSubImage3Property() {
        return subImage3Property;
    }

    public ObjectProperty<Image> getSubImage4Property() {
        return subImage4Property;
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return dateProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public BooleanProperty warningVisibleProperty() {
        return warningVisibleProperty;
    }

    public void setDefaultView()
    {
        warningVisibleProperty.setValue(false);
        descriptionProperty.setValue("");
        titleProperty.setValue("");
        typeProperty.setValue("");
        priceProperty.setValue("");
        selectedDates = new ArrayList<>();

        mainImageProperty.setValue(defaultImage);
        subImage1Property.setValue(defaultImage);
        subImage2Property.setValue(defaultImage);
        subImage3Property.setValue(defaultImage);
        subImage4Property.setValue(defaultImage);
    }
}
