package ESharing.Server.Model.reservation;

import ESharing.Server.Persistance.reservation.ReservationDAO;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The server model class for managing reservations
 * @version 1.0
 * @author Group1
 */
public class ServerReservationModelManager implements ServerReservationModel{

    private PropertyChangeSupport support;
    private ReservationDAO reservationDAO;
    private Lock lock;

    /**
     * One-argument constructor which initializes the reservationDAO with the given object
     * @param reservationDAO the new value to be set for the reservationDAO
     */
    public ServerReservationModelManager(ReservationDAO reservationDAO){
        this.reservationDAO = reservationDAO;

        lock=new ReentrantLock();
        support = new PropertyChangeSupport(this);
    }

    @Override
    public boolean makeNewReservation(Reservation reservation) {
        boolean result;
        synchronized (lock) {
            result = reservationDAO.makeNewReservation(reservation);
        }
        if(result) {
            support.firePropertyChange(Events.NEW_RESERVATION_CREATED.toString(), null, reservation);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean removeReservation(int advertisementID, int userID) {
        List<LocalDate> removedReservationDays = reservationDAO.getUserReservation(advertisementID, userID);
        if(removedReservationDays != null & reservationDAO.removeReservation(advertisementID, userID)) {
            int[] idArray = {advertisementID, userID};
            support.firePropertyChange(Events.RESERVATION_REMOVED.toString(), idArray, removedReservationDays);
            return true;
        }
        return false;
    }

    @Override
    public synchronized List<Reservation> getUserReservations(int userID) {
        return reservationDAO.getUserReservations(userID);
    }

    @Override
    public synchronized List<Reservation> getReservationForAdvertisement(int advertisementID) {
        return reservationDAO.getReservationForAdvertisement(advertisementID);
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
