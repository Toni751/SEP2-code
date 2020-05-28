package ESharing.Server.Core;

import ESharing.Server.Networking.ServerAdvertisementHandler;
import ESharing.Server.Networking.ServerChatHandler;
import ESharing.Server.Networking.ServerUserHandler;
import ESharing.Server.Networking.ServerReservationHandler;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.Networking.user.RMIUserServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubFactory implements StubInterface {
    private RMIChatServer chatServerRMI;
    private RMIUserServer serverRMI;
    private RMIAdvertisementServer advertisementServerRMI;
    private RMIReservationServer reservationServer;
    private ServerModelFactory serverFactory;

    public StubFactory(ServerModelFactory serverFactory) throws RemoteException
    {
        this.serverFactory = serverFactory;
        UnicastRemoteObject.exportObject(this, 0);
    }


    public RMIChatServer getServerChatHandler() throws RemoteException {
        System.out.println("Get server chat called in stub");
        if(chatServerRMI == null)
        {
            System.out.println("Server chat was null in stub");
            chatServerRMI = new ServerChatHandler(serverFactory.getChatModel(), serverFactory.getServerModel(), serverFactory.getServerAdvertisementModel());
        }
        return chatServerRMI;
    }

    public RMIUserServer getServerRMI() throws RemoteException {
        System.out.println("Get server called in stub");
        if(serverRMI == null)
        {
            System.out.println("Server was null in stub");
            serverRMI = new ServerUserHandler(serverFactory.getServerModel());
        }
        return serverRMI;
    }

    @Override
    public RMIAdvertisementServer getServerAdHandler() throws RemoteException {
        System.out.println("Get server called in stub");
        if(advertisementServerRMI == null)
        {
            System.out.println("Server was null in stub");
            advertisementServerRMI = new ServerAdvertisementHandler(serverFactory.getServerAdvertisementModel());
        }
        return advertisementServerRMI;
    }

    @Override
    public RMIReservationServer getServerReservation() throws RemoteException {
        System.out.println("Get server called in stub");
        if(reservationServer == null)
        {
            System.out.println("Server was null in stub");
            reservationServer = new ServerReservationHandler(serverFactory.getServerReservationModel());
        }
        return reservationServer;
    }
}
