package ESharing.Client.Networking.reservation;

import ESharing.Shared.Networking.reservation.RMIReservationClient;
import ESharing.Shared.TransferedObject.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class ReservationClientHandler implements ReservationClient, RMIReservationClient {

    private PropertyChangeSupport support;

    public ReservationClientHandler() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public boolean makeNewReservation(Reservation reservation) {
        return false;
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) {
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        return null;
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) {
        return null;
    }

    @Override
    public void newReservationCreated(Reservation reservation) throws RemoteException {

    }

    @Override
    public void reservationRemoved(int advertisementID, int userID) throws RemoteException {

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
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
