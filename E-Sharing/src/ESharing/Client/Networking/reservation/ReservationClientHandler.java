package ESharing.Client.Networking.reservation;

import ESharing.Client.Networking.Connection;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.reservation.RMIReservationClient;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

public class ReservationClientHandler implements ReservationClient, RMIReservationClient {

    private PropertyChangeSupport support;
    private RMIReservationServer server;

    public ReservationClientHandler() {
        support = new PropertyChangeSupport(this);

        try
        {
            UnicastRemoteObject.exportObject(this, 0);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }



    public void initializeConnection()
    {
        try {
            server = Connection.getStubInterface().getServerReservation();
            System.out.println("Connected from Client Chat Handler");
//        server.unRegisterUserAsAListener();
        } catch (RemoteException e) {
            //support.firePropertyChange(Events.CONNECTION_FAILED.toString(), null, null);
        }
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
    public void registerForCallback() {
        try {
            server.registerCallback(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newReservationCreated(Reservation reservation){
        support.firePropertyChange(Events.NEW_RESERVATION_CREATED.toString(), null, reservation);
    }

    @Override
    public void reservationRemoved(int[] idArray, List<LocalDate> removedDays){
        support.firePropertyChange(Events.RESERVATION_REMOVED.toString(), idArray, removedDays);
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
