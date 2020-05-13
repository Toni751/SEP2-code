package ESharing.Server.Core;

import ESharing.Server.Networking.ServerChatHandler;
import ESharing.Server.Networking.ServerHandler;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubFactory implements StubInterface {
    private RMIChatServer chatServerRMI;
    private RMIServer serverRMI;

    public StubFactory() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }


    public RMIChatServer getServerChatHandler() throws RemoteException {
        if(chatServerRMI == null)
            chatServerRMI = new ServerChatHandler(ServerModelFactory.getInstance().getChatModel());
        return chatServerRMI;
    }

    public RMIServer getServerRMI() throws RemoteException {
        if(serverRMI == null)
            serverRMI = new ServerHandler(ServerModelFactory.getInstance().getServerModel());
        return serverRMI;
    }
}
