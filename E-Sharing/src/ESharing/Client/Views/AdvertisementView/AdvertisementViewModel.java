package ESharing.Client.Views.AdvertisementView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ReservationModel.ReservationModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.beans.EventSetDescriptor;
import java.beans.PropertyChangeEvent;
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
    private DoubleProperty ratingProperty;
    private ObjectProperty<Paint> avatarCircleProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty removeVisibleProperty;
    private BooleanProperty reserveVisibleProperty;
    private BooleanProperty arrowBackProperty;
    private ObjectProperty<LocalDate> dateProperty;
    private ObjectProperty<Callback<DatePicker, DateCell>> dateCellFactoryProperty;
    private ObjectProperty<Paint> mainImageProperty;
    private ObjectProperty<Paint> sub1ImageProperty;
    private ObjectProperty<Paint> sub2ImageProperty;
    private ObjectProperty<Paint> sub3ImageProperty;
    private ObjectProperty<Paint> sub4ImageProperty;

    private List<LocalDate> selectedDates;
    private ObservableList<LocalDate> unavailableDates;

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
        ratingProperty = new SimpleDoubleProperty();
        arrowBackProperty = new SimpleBooleanProperty();
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
        unavailableDates = FXCollections.observableArrayList();

        reservationModel.addPropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), this::newReservationAppear);
        reservationModel.addPropertyChangeListener(Events.RESERVATION_REMOVED.toString(), this::reservationRemoved);
    }

    public void loadDefaultView()
    {
        unavailableDates.clear();
        unavailableDates.setAll(LoggedUser.getLoggedUser().getSelectedAdvertisement().getUnavailability());

        titleProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getTitle());
        typeProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getType());
        priceProperty.set(String.valueOf(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPrice()));
        descriptionProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getDescription());
        ownerUsernameProperty.set(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getUsername());
        avatarCircleProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getAvatar()));

        sub1ImageProperty.setValue(Color.valueOf("#f2f7f7"));
        sub2ImageProperty.setValue(Color.valueOf("#f2f7f7"));
        sub3ImageProperty.setValue(Color.valueOf("#f2f7f7"));
        sub4ImageProperty.setValue(Color.valueOf("#f2f7f7"));

        arrowBackProperty.setValue(false);

        mainImageProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.MAIN_IMAGE.toString())));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE1.toString()))
            sub1ImageProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE1.toString())));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE2.toString()))
            sub2ImageProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE2.toString())));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE3.toString()))
            sub3ImageProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE3.toString())));
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE4.toString()))
            sub4ImageProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE4.toString())));

        warningVisibleProperty.set(false);

        if(LoggedUser.getLoggedUser().getUser().isAdministrator() || LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()){
            reserveVisibleProperty.setValue(false);
            removeVisibleProperty.setValue(true);
        }
        else {
            reserveVisibleProperty.setValue(true);
            removeVisibleProperty.setValue(false);
        }

        if(LoggedUser.getLoggedUser().getUser().isAdministrator()){
            arrowBackProperty.setValue(true);
        }

        dateProperty.setValue(null);
        ratingProperty.setValue(3); //Load average rating from database
        selectedDates.clear();
    }

    public void recalculatePrice()
    {
        double newPrice = LoggedUser.getLoggedUser().getSelectedAdvertisement().getPrice() * selectedDates.size();
        priceProperty.set(String.valueOf(newPrice));
    }

    public void sendReservationRequest()
    {
        Advertisement selectedAdvertisement = LoggedUser.getLoggedUser().getSelectedAdvertisement();
        Reservation newReservation = new Reservation(selectedAdvertisement.getAdvertisementID(), selectedAdvertisement.getTitle(), selectedAdvertisement.getOwner().getUsername(), LoggedUser.getLoggedUser().getUser(), selectedAdvertisement.getPrice(), selectedDates);
        boolean requestResult = false;
        if(!selectedDates.isEmpty())
            requestResult = reservationModel.makeNewReservation(newReservation);
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

    public void sendRatingRequest() {
        System.out.println(ratingProperty.get());
        //send a request with the rating to database
        //if result is true ; disabled rating component
    }

    public void clearRatingsProperty() {
        ratingProperty.setValue(0);
    }

    public void setDefaultRatingsProperty() {
        ratingProperty.setValue(3);
        //Load default ratings average from database
    }

    public ObservableList<LocalDate> getUnavailableDates() {
        return unavailableDates;
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

    public ObjectProperty<Paint> getMainImageProperty() {
        return mainImageProperty;
    }

    public ObjectProperty<Paint> getSub1ImageProperty() {
        return sub1ImageProperty;
    }

    public ObjectProperty<Paint> getSub2ImageProperty() {
        return sub2ImageProperty;
    }

    public ObjectProperty<Paint> getSub3ImageProperty() {
        return sub3ImageProperty;
    }

    public ObjectProperty<Paint> getSub4ImageProperty() {
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

    public DoubleProperty getRatingProperty() {
        return ratingProperty;
    }

    private void reservationRemoved(PropertyChangeEvent propertyChangeEvent) {
        int[] idArray = (int[]) propertyChangeEvent.getOldValue();
        List<LocalDate> removedDays = (List<LocalDate>) propertyChangeEvent.getNewValue();

        int advertisementID = idArray[0];

        if(LoggedUser.getLoggedUser().getSelectedAdvertisement() != null && advertisementID == LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID()){
            unavailableDates.removeAll(removedDays);
        }

        System.out.println("event removed");
    }

    private void newReservationAppear(PropertyChangeEvent propertyChangeEvent) {
        Reservation reservation = (Reservation) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement()!= null && reservation.getAdvertisementID() == LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID()){
            unavailableDates.addAll(reservation.getReservationDays());
        }
    }

    public BooleanProperty getArrowBackProperty() {
        return arrowBackProperty;
    }
}
