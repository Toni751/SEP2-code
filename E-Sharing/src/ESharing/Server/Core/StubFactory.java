package ESharing.Server.Core;

import ESharing.Server.Networking.ServerAdvertisementHandler;
import ESharing.Server.Networking.ServerChatHandler;
import ESharing.Server.Networking.ServerHandler;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubFactory implements StubInterface {
    private RMIChatServer chatServerRMI;
    private RMIServer serverRMI;
    private RMIAdvertisementServer advertisementServerRMI;
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
            chatServerRMI = new ServerChatHandler(serverFactory.getChatModel(), serverFactory.getServerModel());
        }
        return chatServerRMI;
    }

    public RMIServer getServerRMI() throws RemoteException {
        System.out.println("Get server called in stub");
        if(serverRMI == null)
        {
            System.out.println("Server was null in stub");
            serverRMI = new ServerHandler(serverFactory.getServerModel());
        }
        return serverRMI;
    }

    @Override
    public RMIAdvertisementServer getServerAdHandler() throws RemoteException {
        if(advertisementServerRMI == null)
            advertisementServerRMI = new ServerAdvertisementHandler(serverFactory.getServerAdvertisementModel());
        return advertisementServerRMI;
    }
}
