package ESharing.Client.Model.ReservationModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.reservation.ReservationClient;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * The class from the model layer which contains all reservation features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class ReservationModelManager implements ReservationModel{

    private ReservationClient reservationClient;
    private PropertyChangeSupport support;

    /**
     * A constructor sets fields and assigns events
     */
    public ReservationModelManager() {
        reservationClient = ClientFactory.getClientFactory().getReservationClient();
        support = new PropertyChangeSupport(this);

        reservationClient.addPropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), this::newReservationCreated);
        reservationClient.addPropertyChangeListener(Events.RESERVATION_REMOVED.toString(), this::reservationRemoved);
    }

    @Override
    public boolean makeNewReservation(Reservation reservation) {
        return reservationClient.makeNewReservation(reservation);
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) {
        return reservationClient.removeReservation(advertisementID, userID);
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        return reservationClient.getUserReservations(userID);
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) {
        return reservationClient.getReservationForAdvertisement(advertisementID);
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Starts when new reservationRemoved event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void reservationRemoved(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new reservationCreated event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newReservationCreated(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }
}
