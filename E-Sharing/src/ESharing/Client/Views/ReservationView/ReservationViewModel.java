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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;

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
    }

    public void defaultView(){

        reservations.clear();
        loadAllReservations();

        removeProperty.setValue(true);
        openProperty.setValue(true);
        warningVisibleProperty.setValue(false);
    }

    public void enableManagingActions(){
        removeProperty.setValue(false);
        openProperty.setValue(false);
    }

    public void disableManagingActions(){
        removeProperty.setValue(true);
        openProperty.setValue(true);
    }

    public void loadAllReservations() {
        reservations.setAll(reservationModel.getUserReservations(LoggedUser.getLoggedUser().getUser().getUser_id()));
    }

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

    public boolean loadReservation(){
        Advertisement advertisement = advertisementModel.getAdvertisement(selectedReservation.getAdvertisementID());
        if(advertisement != null)
        {
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            return true;
        }
        return false;
    }


    public ObservableList<Reservation> getAllReservations() {
        return reservations;
    }

    public BooleanProperty getOpenProperty() {
        return openProperty;
    }

    public BooleanProperty getRemoveProperty() {
        return removeProperty;
    }

    public void setSelectedReservation(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

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

    private void newReservationCreated(PropertyChangeEvent propertyChangeEvent) {
        Reservation reservation = (Reservation) propertyChangeEvent.getNewValue();
        if(reservation.getRequestedUser().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
            defaultView();
    }
}
