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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The class to be download by the client, holding all the stub for remote method invocation
 * @version 1.0
 * @author Group1
 */
public class StubFactory implements StubInterface {
    private RMIChatServer chatServerRMI;
    private RMIUserServer userServerRMI;
    private RMIAdvertisementServer advertisementServerRMI;
    private RMIReservationServer reservationServer;
    private ServerModelFactory serverFactory;
    private Lock lock = new ReentrantLock();

    /**
     * One argument constructor initializing the server model factory and exporting the object
     * @param serverFactory the server model factory
     * @throws RemoteException if connection fails and the object cannot be exported
     */
    public StubFactory(ServerModelFactory serverFactory) throws RemoteException
    {
        this.serverFactory = serverFactory;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public RMIChatServer getServerChatHandler() throws RemoteException {
        System.out.println("Get server chat called in stub");
        if (chatServerRMI == null)
        {
            synchronized (lock)
            {
                if(chatServerRMI == null)
                {
                    System.out.println("Server chat was null in stub");
                    chatServerRMI = new ServerChatHandler(serverFactory.getChatModel(), serverFactory.getServerUserModel(), serverFactory.getServerAdvertisementModel());
                }
            }
        }
        return chatServerRMI;
    }

    @Override
    public RMIUserServer getUserServerRMI() throws RemoteException {
        System.out.println("Get server called in stub");
        if (userServerRMI == null)
        {
            synchronized (lock)
            {
                if(userServerRMI == null)
                {
                    System.out.println("Server was null in stub");
                    userServerRMI = new ServerUserHandler(serverFactory.getServerUserModel());
                }
            }
        }
        return userServerRMI;
    }

    @Override
    public RMIAdvertisementServer getServerAdHandler() throws RemoteException {
        System.out.println("Get server called in stub");
        if (advertisementServerRMI == null)
        {
            synchronized (lock)
            {
                if(advertisementServerRMI == null)
                {
                    System.out.println("Server was null in stub");
                    advertisementServerRMI = new ServerAdvertisementHandler(serverFactory.getServerAdvertisementModel());
                }
            }
        }
        return advertisementServerRMI;
    }

    @Override
    public RMIReservationServer getServerReservation() throws RemoteException {
        System.out.println("Get server called in stub");
        if (reservationServer == null)
        {
            synchronized (lock)
            {
                if(reservationServer == null)
                {
                    System.out.println("Server was null in stub");
                    reservationServer = new ServerReservationHandler(serverFactory.getServerReservationModel());
                }
            }
        }
        return reservationServer;
    }
}
