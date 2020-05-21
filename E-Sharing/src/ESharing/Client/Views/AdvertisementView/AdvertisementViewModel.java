package ESharing.Client.Views.AdvertisementView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ReservationModel.ReservationModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementViewModel {

    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private StringProperty priceProperty;
    private StringProperty typeProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private StringProperty ownerUsernameProperty;
    private ObjectProperty<Paint> avatarCircleProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty removeVisibleProperty;
    private BooleanProperty reserveVisibleProperty;
    private ObjectProperty<LocalDate> dateProperty;
    private ObjectProperty<Callback<DatePicker, DateCell>> dateCellFactoryProperty;
    private ObjectProperty<Image> mainImageProperty;
    private ObjectProperty<Image> sub1ImageProperty;
    private ObjectProperty<Image> sub2ImageProperty;
    private ObjectProperty<Image> sub3ImageProperty;
    private ObjectProperty<Image> sub4ImageProperty;

    private List<LocalDate> selectedDates;

    private ReservationModel reservationModel;
    private AdvertisementModel advertisementModel;

    public AdvertisementViewModel()
    {
        reservationModel = ModelFactory.getModelFactory().getReservationModel();
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        titleProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        priceProperty = new SimpleStringProperty();
        dateProperty = new SimpleObjectProperty<>();
        typeProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        ownerUsernameProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        removeVisibleProperty = new SimpleBooleanProperty();
        reserveVisibleProperty = new SimpleBooleanProperty();
        dateCellFactoryProperty = new SimpleObjectProperty<>();
        avatarCircleProperty = new SimpleObjectProperty<>();
        mainImageProperty = new SimpleObjectProperty<>();
        sub1ImageProperty = new SimpleObjectProperty<>();
        sub2ImageProperty = new SimpleObjectProperty<>();
        sub3ImageProperty = new SimpleObjectProperty<>();
        sub4ImageProperty = new SimpleObjectProperty<>();

        selectedDates = new ArrayList<>();
    }

    public void loadDefaultView()
    {
        titleProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getTitle());
        typeProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getType());
        priceProperty.set(String.valueOf(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPrice()));
        descriptionProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getDescription());
        ownerUsernameProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getUsername());
        avatarCircleProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getAvatar()));

        mainImageProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.MAIN_IMAGE.toString()));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE1.toString()))
            sub1ImageProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE1.toString()));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE2.toString()))
            sub2ImageProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE2.toString()));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE3.toString()))
            sub3ImageProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE3.toString()));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE4.toString()))
            sub4ImageProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE4.toString()));

        warningVisibleProperty.set(false);

        if(LoggedUser.getLoggedUser().getUser().isAdministrator() || LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()){
            reserveVisibleProperty.setValue(false);
            removeVisibleProperty.setValue(true);
        }
        else {
            reserveVisibleProperty.setValue(true);
            removeVisibleProperty.setValue(false);
        }

        dateProperty.setValue(null);
        selectedDates.clear();
    }

    public void recalculatePrice()
    {
        double newPrice = LoggedUser.getLoggedUser().getSelectedAdvertisement().getPrice() * selectedDates.size();
        priceProperty.set(String.valueOf(newPrice));
    }

    public void sendReservationRequest()
    {
        boolean requestResult = false;
        if(!selectedDates.isEmpty())
            requestResult = reservationModel.sendReservationRequest(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID(), selectedDates);
        if(requestResult){
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else{
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    public String sendAdvertisementRemoveRequest()
    {
        boolean requestResult = advertisementModel.removeAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID());
        if(!requestResult) {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
            warningVisibleProperty.setValue(true);
        }
       if(LoggedUser.getLoggedUser().getUser().isAdministrator())
           return "ADMIN";
       else
           return  "USER";
    }

    public void addSelectedDate()
    {
        LocalDate selectedDate = dateProperty.getValue();
        if(selectedDates.contains(selectedDate))
            selectedDates.remove(selectedDate);
        else
            selectedDates.add(dateProperty.getValue());
    }

    public void reportAdvertisement() {
        if (advertisementModel.reportAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID())) {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            warningVisibleProperty.setValue(true);
        }
    }

    public List<LocalDate> getSelectedDates() {
        return selectedDates;
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public StringProperty getPriceProperty() {
        return priceProperty;
    }

    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return dateProperty;
    }

    public ObjectProperty<Image> getMainImageProperty() {
        return mainImageProperty;
    }

    public ObjectProperty<Image> getSub1ImageProperty() {
        return sub1ImageProperty;
    }

    public ObjectProperty<Image> getSub2ImageProperty() {
        return sub2ImageProperty;
    }

    public ObjectProperty<Image> getSub3ImageProperty() {
        return sub3ImageProperty;
    }

    public ObjectProperty<Image> getSub4ImageProperty() {
        return sub4ImageProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    public ObjectProperty<Callback<DatePicker, DateCell>> getCellFactoryProperty(){
        return dateCellFactoryProperty;
    }

    public StringProperty getOwnerUsernameProperty() {
        return ownerUsernameProperty;
    }

    public ObjectProperty<Paint> getAvatarCircleProperty() {
        return avatarCircleProperty;
    }

    public BooleanProperty getRemoveVisibleProperty() {
        return removeVisibleProperty;
    }

    public BooleanProperty getReserveVisibleProperty() {
        return reserveVisibleProperty;
    }

}
