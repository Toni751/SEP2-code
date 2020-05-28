package ESharing.Client.Views.ReservationView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ReservationModel.ReservationModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * The class in a view model layer contains all functions which are used in the reservation view.
 * @version 1.0
 * @author Group1
 */
public class ReservationViewModel {

    private BooleanProperty removeProperty;
    private BooleanProperty openProperty;
    private BooleanProperty warningVisibleProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;

    private ReservationModel reservationModel;
    private AdvertisementModel advertisementModel;
    private ObservableList<Reservation> reservations;

    private Reservation selectedReservation;
    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a reservation features and all fields
     */
    public ReservationViewModel() {
        this.reservationModel = ModelFactory.getModelFactory().getReservationModel();
        this.advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        support = new PropertyChangeSupport(this);
        reservations = FXCollections.observableArrayList();

        removeProperty = new SimpleBooleanProperty();
        openProperty = new SimpleBooleanProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();


        reservationModel.addPropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), this::newReservationCreated);
        reservationModel.addPropertyChangeListener(Events.RESERVATION_REMOVED.toString(), this::reservationRemoved);
        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
    }

    /**
     * Sets defaultview
     */
    public void defaultView(){

        reservations.clear();
        loadAllReservations();

        removeProperty.setValue(true);
        openProperty.setValue(true);
        warningVisibleProperty.setValue(false);
    }

    /**
     * enables button property
     */
    public void enableManagingActions(){
        removeProperty.setValue(false);
        openProperty.setValue(false);
    }

    /**
     * disables button property
     */
    public void disableManagingActions(){
        removeProperty.setValue(true);
        openProperty.setValue(true);
    }

    /**
     * Loads all reservations
     */
    public void loadAllReservations() {
        reservations.setAll(reservationModel.getUserReservations(LoggedUser.getLoggedUser().getUser().getUser_id()));
    }

    /**
     * Sends a request for removing reservation and sets the warning pane property
     */
    public void removeReservation(){
        if(reservationModel.removeReservation(selectedReservation.getAdvertisementID(), LoggedUser.getLoggedUser().getUser().getUser_id()))
        {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    /**
     * Loads reservations
     * @return the result of loading
     */
    public boolean loadReservation(){
        Advertisement advertisement = advertisementModel.getAdvertisement(selectedReservation.getAdvertisementID());
        if(advertisement != null)
        {
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            return true;
        }
        return false;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list of all reservations
     */
    public ObservableList<Reservation> getAllReservations() {
        return reservations;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a open button
     */
    public BooleanProperty getOpenProperty() {
        return openProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a remove button
     */
    public BooleanProperty getRemoveProperty() {
        return removeProperty;
    }

    /**
     * Sets selected reservation
     * @param selectedReservation the given reservation
     */
    public void setSelectedReservation(Reservation selectedReservation) {
        LoggedUser.getLoggedUser().selectAdvertisement(advertisementModel.getAdvertisement(selectedReservation.getAdvertisementID()));
        this.selectedReservation = selectedReservation;
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
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void reservationRemoved(PropertyChangeEvent propertyChangeEvent) {
        int[] idArray = (int[]) propertyChangeEvent.getOldValue();
        int advertisementID = idArray[0];
        int userID = idArray[1];

        if(userID == LoggedUser.getLoggedUser().getUser().getUser_id()){
            for(int i = 0 ; i < reservations.size(); i++)
            {
                if(reservations.get(i).getAdvertisementID() == advertisementID)
                    reservations.remove(reservations.get(i));
            }
        }
        disableManagingActions();
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void newReservationCreated(PropertyChangeEvent propertyChangeEvent) {
        Reservation reservation = (Reservation) propertyChangeEvent.getNewValue();
        if(reservation.getRequestedUser().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
            defaultView();
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int id = (int) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < reservations.size(); i++){
            if(reservations.get(i).getAdvertisementID() == id)
                reservations.remove(reservations.get(i));
        }
    }
}
