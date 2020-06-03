package ESharing.Server.Networking;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.reservation.ServerReservationModel;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.reservation.RMIReservationClient;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The server networking handler class for reservations, used for RMI
 * @version 1.0
 * @author Group1
 */
public class ServerReservationHandler implements RMIReservationServer {

    private ServerReservationModel serverModel;
    private PropertyChangeListener listenToAddedReservation;
    private PropertyChangeListener listenToDeletedReservation;

    /**
     * One argument constructor which initializes the server reservation model, and exports the object
     * @param serverModel the value to be set for the server reservation model
     */
    public ServerReservationHandler(ServerReservationModel serverModel)
    {
        try
        {
            UnicastRemoteObject.exportObject(this, 0);
            this.serverModel = serverModel;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean makeNewReservation(Reservation reservation){
        return serverModel.makeNewReservation(reservation);
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID){
        return serverModel.removeReservation(advertisementID, userID);
    }

    @Override
    public List<Reservation> getUserReservations(int userID){
        return serverModel.getUserReservations(userID);
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID){
        return serverModel.getReservationForAdvertisement(advertisementID);
    }

    @Override
    public void registerCallback(RMIReservationClient reservationClient) {
        listenToAddedReservation = evt -> {
            try {
                reservationClient.newReservationCreated((Reservation) evt.getNewValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };

        listenToDeletedReservation = evt -> {
            try {
                reservationClient.reservationRemoved((int[])evt.getOldValue(), (List<LocalDate>) evt.getNewValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };

        serverModel.addPropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), listenToAddedReservation);
        serverModel.addPropertyChangeListener(Events.RESERVATION_REMOVED.toString(), listenToDeletedReservation);
    }

    @Override
    public void unRegisterCallback() {
        serverModel.removePropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), listenToAddedReservation);
        serverModel.removePropertyChangeListener(Events.RESERVATION_REMOVED.toString(), listenToDeletedReservation);
    }
}
