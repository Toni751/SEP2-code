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

/**
 * The class in a view model layer contains all functions which are used in the advertisement view.
 *
 * @author Group1
 * @version 1.0
 */
public class AdvertisementViewModel {

    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private StringProperty priceProperty;
    private StringProperty typeProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private StringProperty ownerUsernameProperty;
    private StringProperty ownerPhoneProperty;
    private StringProperty ownerDateProperty;
    private DoubleProperty ratingProperty;
    private ObjectProperty<Paint> avatarCircleProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty removeVisibleProperty;
    private BooleanProperty reserveVisibleProperty;
    private BooleanProperty ownerButtonDisableProperty;
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
    private double rating;

    private ReservationModel reservationModel;
    private AdvertisementModel advertisementModel;

    /**
     * A constructor initializes model layer for a administrator features and all fields
     */
    public AdvertisementViewModel()
    {
        reservationModel = ModelFactory.getModelFactory().getReservationModel();
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        titleProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        priceProperty = new SimpleStringProperty();
        dateProperty = new SimpleObjectProperty<>();
        typeProperty = new SimpleStringProperty();
        ownerPhoneProperty = new SimpleStringProperty();
        ownerDateProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        ratingProperty = new SimpleDoubleProperty();
        ownerButtonDisableProperty = new SimpleBooleanProperty();
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

    /**
     * Sets a default view and values
     */
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
        ownerPhoneProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getPhoneNumber());
        ownerDateProperty.setValue(LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getCreation_date());

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
            ownerButtonDisableProperty.setValue(true);
        }
        else {
            reserveVisibleProperty.setValue(true);
            removeVisibleProperty.setValue(false);
            ownerButtonDisableProperty.setValue(false);
        }

        if(LoggedUser.getLoggedUser().getUser().isAdministrator()){
            arrowBackProperty.setValue(true);
        }

        dateProperty.setValue(null);
        rating = advertisementModel.retrieveAdRating(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID());
        ratingProperty.setValue(rating);

        selectedDates.clear();
    }

    /**
     * Recalculates price
     */
    public void recalculatePrice()
    {
        double newPrice = LoggedUser.getLoggedUser().getSelectedAdvertisement().getPrice() * selectedDates.size();
        priceProperty.set(String.valueOf(newPrice));
    }

    /**
     * Sends a request for creating reservation
     */
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

    /**
     * Sends a request for removing reservation
     */
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

    /**
     * Adds selected dates to the list
     */
    public void addSelectedDate()
    {
        LocalDate selectedDate = dateProperty.getValue();
        if(selectedDates.contains(selectedDate))
            selectedDates.remove(selectedDate);
        else
            selectedDates.add(dateProperty.getValue());
    }

    /**
     * Sends a request for reporting
     */
    public void reportAdvertisement() {
        if (advertisementModel.reportAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID())) {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            warningVisibleProperty.setValue(true);
        }
    }

    /**
     * Sends a request for rating
     */
    public void sendRatingRequest() {
      advertisementModel.addRating(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID(), LoggedUser.getLoggedUser().getUser().getUser_id(), ratingProperty.get());
    }

    /**
     * Clears rating
     */
    public void clearRatingsProperty() {
        ratingProperty.setValue(0);
    }

    /**
     * Sets default ratings
     */
    public void setDefaultRatingsProperty() {
        ratingProperty.setValue(rating);
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list with unavailable dates
     */
    public ObservableList<LocalDate> getUnavailableDates() {
        return unavailableDates;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list with selected dates
     */
    public List<LocalDate> getSelectedDates() {
        return selectedDates;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a title value
     */
    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a description value
     */
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a price value
     */
    public StringProperty getPriceProperty() {
        return priceProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a type value
     */
    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a date value
     */
    public ObjectProperty<LocalDate> getDateProperty() {
        return dateProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a main picture
     */
    public ObjectProperty<Paint> getMainImageProperty() {
        return mainImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub picture 1
     */
    public ObjectProperty<Paint> getSub1ImageProperty() {
        return sub1ImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub picture 2
     */
    public ObjectProperty<Paint> getSub2ImageProperty() {
        return sub2ImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub picture 3
     */
    public ObjectProperty<Paint> getSub3ImageProperty() {
        return sub3ImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a sub picture 4
     */
    public ObjectProperty<Paint> getSub4ImageProperty() {
        return sub4ImageProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning pane
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning pane
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning pane
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a date picker item
     */
    public ObjectProperty<Callback<DatePicker, DateCell>> getCellFactoryProperty(){
        return dateCellFactoryProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a username value
     */
    public StringProperty getOwnerUsernameProperty() {
        return ownerUsernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a avatar value
     */
    public ObjectProperty<Paint> getAvatarCircleProperty() {
        return avatarCircleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a remove button
     */
    public BooleanProperty getRemoveVisibleProperty() {
        return removeVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a reserve button
     */
    public BooleanProperty getReserveVisibleProperty() {
        return reserveVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a rating
     */
    public DoubleProperty getRatingProperty() {
        return ratingProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a arrow back
     */
    public BooleanProperty getArrowBackProperty() {
        return arrowBackProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a phone
     */
    public StringProperty getOwnerPhoneProperty() {
        return ownerPhoneProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a owner
     */
    public StringProperty getOwnerDate() {
        return ownerDateProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a owner button
     */
    public BooleanProperty getOwnerButtonDisable() {
        return ownerButtonDisableProperty;
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void reservationRemoved(PropertyChangeEvent propertyChangeEvent) {
        int[] idArray = (int[]) propertyChangeEvent.getOldValue();
        List<LocalDate> removedDays = (List<LocalDate>) propertyChangeEvent.getNewValue();

        int advertisementID = idArray[0];

        if(LoggedUser.getLoggedUser().getSelectedAdvertisement() != null && advertisementID == LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID()){
            unavailableDates.removeAll(removedDays);
        }

        System.out.println("event removed");
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void newReservationAppear(PropertyChangeEvent propertyChangeEvent) {
        Reservation reservation = (Reservation) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement()!= null && reservation.getAdvertisementID() == LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID()){
            unavailableDates.addAll(reservation.getReservationDays());
        }
    }
}
