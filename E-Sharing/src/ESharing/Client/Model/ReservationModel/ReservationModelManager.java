package ESharing.Client.Model.ReservationModel;

import ESharing.Client.Networking.reservation.ReservationClient;
import ESharing.Shared.TransferedObject.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.List;

public class ReservationModelManager implements ReservationModel{

    private ReservationClient reservationClient;
    private PropertyChangeSupport support;

    public ReservationModelManager() {
        support = new PropertyChangeSupport(this);
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
}
