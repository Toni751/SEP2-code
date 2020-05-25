package ESharing.Client.Views.CreateAdView;
import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Vehicles;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAdViewModel {

    private StringProperty titleProperty;
    private StringProperty priceProperty;
    private StringProperty descriptionProperty;
    private StringProperty warningProperty;
    private StringProperty typeValueProperty;
    private BooleanProperty warningVisibleProperty;
    private ObservableList<String> typeItemsProperty;
    private StringProperty warningStyleProperty;
    private ObjectProperty<Paint> mainImageRectangleFill;
    private ObjectProperty<Paint> subImage1RectangleFill;
    private ObjectProperty<Paint> subImage2RectangleFill;
    private ObjectProperty<Paint> subImage3RectangleFill;
    private ObjectProperty<Paint> subImage4RectangleFill;
    private BooleanProperty mainPicturePaneVisible;

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

    private final Image defaultImage = new Image("ESharing/Addition/Images/icons/image.png");
    public CreateAdViewModel()
    {
        titleProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        priceProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        dateProperty = new SimpleObjectProperty<>();
        typeItemsProperty = new SimpleListProperty<>();
        warningStyleProperty = new SimpleStringProperty();
        typeValueProperty = new SimpleStringProperty();
        mainPicturePaneVisible = new SimpleBooleanProperty();

        mainImageProperty = new SimpleObjectProperty<>();
        subImage1Property = new SimpleObjectProperty<>();
        subImage2Property = new SimpleObjectProperty<>();
        subImage3Property = new SimpleObjectProperty<>();
        subImage4Property = new SimpleObjectProperty<>();
        mainImageRectangleFill = new SimpleObjectProperty<>();
        subImage1RectangleFill = new SimpleObjectProperty<>();
        subImage2RectangleFill = new SimpleObjectProperty<>();
        subImage3RectangleFill = new SimpleObjectProperty<>();
        subImage4RectangleFill = new SimpleObjectProperty<>();

        typeItemsProperty = FXCollections.observableArrayList();
        imageFiles = new HashMap<>();
        selectedDates = new ArrayList<>();

        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
    }

    public void addAdvertisementRequest()
    {
        String verification = verificationModel.verifyAdvertisement(titleProperty.get(), typeValueProperty.get(), descriptionProperty.get(),priceProperty.get(), imageFiles.size());

        if(verification == null) {
            Map<String, byte[]> convertedImages = advertisementModel.convertedImages(imageFiles);
            double convertedPrice = Double.parseDouble(priceProperty.get());
            advertisementModel.addNewAdvertisement(new Advertisement(LoggedUser.getLoggedUser().getUser(), convertedImages, typeValueProperty.get(), selectedDates, convertedPrice, titleProperty.get(), descriptionProperty.get()));
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else{
            warningProperty.setValue(verification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
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
        if(id.equals(AdImages.MAIN_IMAGE.toString())) {
            mainImageRectangleFill.setValue(new ImagePattern(image));
            mainImageProperty.setValue(null);
            mainPicturePaneVisible.setValue(true);
        }
        else if(id.equals(AdImages.SUB_IMAGE1.toString())){
            subImage1RectangleFill.setValue(new ImagePattern(image));
            subImage1Property.setValue(null);
        }
        else if(id.equals(AdImages.SUB_IMAGE2.toString())) {
            subImage2RectangleFill.setValue(new ImagePattern(image));
            subImage2Property.setValue(null);
        }
        else if(id.equals(AdImages.SUB_IMAGE3.toString())) {
            subImage3RectangleFill.setValue(new ImagePattern(image));
            subImage3Property.setValue(null);
        }
        else if(id.equals(AdImages.SUB_IMAGE4.toString())) {
            subImage4RectangleFill.setValue(new ImagePattern(image));
            subImage4Property.setValue(null);
        }
    }

    public void addNewSelectedDate()
    {
        LocalDate selectedDate = dateProperty.getValue();
        if(selectedDates.contains(selectedDate))
            selectedDates.remove(selectedDate);
        else
            selectedDates.add(dateProperty.getValue());
    }

    public void setDefaultView()
    {
        warningVisibleProperty.setValue(false);
        descriptionProperty.setValue("");
        titleProperty.setValue("");
        typeValueProperty.setValue("");
        priceProperty.setValue("");
        mainPicturePaneVisible.setValue(false);

        mainImageProperty.setValue(defaultImage);
        subImage1Property.setValue(defaultImage);
        subImage2Property.setValue(defaultImage);
        subImage3Property.setValue(defaultImage);
        subImage4Property.setValue(defaultImage);

        mainImageRectangleFill.setValue(Color.valueOf("#f2f4f5"));
        subImage1RectangleFill.setValue(Color.valueOf("#f2f4f5"));
        subImage2RectangleFill.setValue(Color.valueOf("#f2f4f5"));
        subImage3RectangleFill.setValue(Color.valueOf("#f2f4f5"));
        subImage4RectangleFill.setValue(Color.valueOf("#f2f4f5"));

        selectedDates = new ArrayList<>();
        typeItemsProperty.clear();
        for(int i = 0 ; i <Vehicles.values().length ; i++) {
            typeItemsProperty.add(Vehicles.values()[i].toString());
        }

        LoggedUser.getLoggedUser().setCurrentOpenConversation(new ArrayList<>());
    }

    public List<LocalDate> getSelectedDates()
    {
        return selectedDates;
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getPriceProperty() {
        return priceProperty;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public ObjectProperty<Image> getMainImageProperty() {
        return mainImageProperty;
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

    public ObservableList<String> getTypeItemsProperty() {
        return typeItemsProperty;
    }

    public StringProperty getTypeValueProperty() {
        return typeValueProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    public ObjectProperty<Paint> getMainImageRectangleFill() {
        return mainImageRectangleFill;
    }

    public ObjectProperty<Paint> getSubImage1RectangleFill() {
        return subImage1RectangleFill;
    }

    public ObjectProperty<Paint> getSubImage2RectangleFill() {
        return subImage2RectangleFill;
    }

    public ObjectProperty<Paint> getSubImage3RectangleFill() {
        return subImage3RectangleFill;
    }

    public ObjectProperty<Paint> getSubImage4RectangleFill() {
        return subImage4RectangleFill;
    }

    public BooleanProperty getMainPicturePaneVisible() {
        return mainPicturePaneVisible;
    }
}
