package ESharing.Client.Networking.reservation;

import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.reservation.RMIReservationClient;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class ReservationClientHandler implements ReservationClient, RMIReservationClient {

    private PropertyChangeSupport support;
    private RMIReservationServer server;

    public ReservationClientHandler() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public boolean makeNewReservation(Reservation reservation) {
        try
        {
            return server.makeNewReservation(reservation);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) {
        try
        {
            return server.removeReservation(advertisementID, userID);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        try
        {
            return server.getUserReservations(userID);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) {
        try
        {
            return server.getReservationForAdvertisement(advertisementID);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void newReservationCreated(Reservation reservation) throws RemoteException {
        support.firePropertyChange(Events.NEW_RESERVATION_CREATED.toString(), null, reservation);
    }

    @Override
    public void reservationRemoved(int advertisementID, int userID) throws RemoteException {
        support.firePropertyChange(Events.RESERVATION_REMOVED.toString(), advertisementID, userID);
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
