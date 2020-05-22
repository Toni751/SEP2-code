package ESharing.Server.Model.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ServerReservationModelManager implements ServerReservationModel{

    private PropertyChangeSupport support;


    public ServerReservationModelManager(){
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
