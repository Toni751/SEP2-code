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

/**
 * The class in a view model layer contains all functions which are used in the edit create ad view.
 * @version 1.0
 * @author Group1
 */
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

    /**
     * A constructor initializes model layer for a create ad features and all fields
     */
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

    /**
     * Sends a request for adding ad
     */
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

    /**
     * Adds new image
     * @param imageId
     * @param imageFile
     */
    public void addImageFile(String imageId, File imageFile)
    {
        if(imageFiles.get(imageId) == null)
            imageFiles.put(imageId, imageFile);
        else
            imageFiles.replace(imageId, imageFile);
    }

    /**
     * Sets image
     * @param id image id
     * @param image image
     */
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

    /**
     * Adds selected date to the list
     */
    public void addNewSelectedDate()
    {
        LocalDate selectedDate = dateProperty.getValue();
        if(selectedDates.contains(selectedDate))
            selectedDates.remove(selectedDate);
        else
            selectedDates.add(dateProperty.getValue());
    }

    /**
     * Sets default view
     */
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

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list with selected dates
     */
    public List<LocalDate> getSelectedDates()
    {
        return selectedDates;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a title
     */
    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of aprice
     */
    public StringProperty getPriceProperty() {
        return priceProperty;
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a description
     */
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a main image
     */
    public ObjectProperty<Image> getMainImageProperty() {
        return mainImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub image 1
     */
    public ObjectProperty<Image> getSubImage1Property() {
        return subImage1Property;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub image 2
     */
    public ObjectProperty<Image> getSubImage2Property() {
        return subImage2Property;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub image 3
     */
    public ObjectProperty<Image> getSubImage3Property() {
        return subImage3Property;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub image 4
     */
    public ObjectProperty<Image> getSubImage4Property() {
        return subImage4Property;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a date
     */
    public ObjectProperty<LocalDate> getDateProperty() {
        return dateProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public BooleanProperty warningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list with searching items
     */
    public ObservableList<String> getTypeItemsProperty() {
        return typeItemsProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a type
     */
    public StringProperty getTypeValueProperty() {
        return typeValueProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rectangle
     */
    public ObjectProperty<Paint> getMainImageRectangleFill() {
        return mainImageRectangleFill;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rectangle
     */
    public ObjectProperty<Paint> getSubImage1RectangleFill() {
        return subImage1RectangleFill;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rectangle
     */
    public ObjectProperty<Paint> getSubImage2RectangleFill() {
        return subImage2RectangleFill;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rectangle
     */
    public ObjectProperty<Paint> getSubImage3RectangleFill() {
        return subImage3RectangleFill;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rectangle
     */
    public ObjectProperty<Paint> getSubImage4RectangleFill() {
        return subImage4RectangleFill;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a picture pane
     */
    public BooleanProperty getMainPicturePaneVisible() {
        return mainPicturePaneVisible;
    }
}
