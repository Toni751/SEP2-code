package ESharing.Server.Networking;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.reservation.ServerReservationModel;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.TransferedObject.Reservation;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerReservationHandler implements RMIReservationServer {

    private ServerReservationModel serverModel;
    private PropertyChangeListener listenToNewReservation;
    private PropertyChangeListener listenToAddedReservation;
    private PropertyChangeListener listenToDeletedReservation;

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
    public boolean makeNewReservation(Reservation reservation) throws RemoteException {
        return serverModel.makeNewReservation(reservation);
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) throws RemoteException {
        return serverModel.removeReservation(advertisementID, userID);
    }

    @Override
    public List<Reservation> getUserReservations(int userID) throws RemoteException {
        return serverModel.getUserReservations(userID);
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) throws RemoteException {
        return serverModel.getReservationForAdvertisement(advertisementID);
    }
}
