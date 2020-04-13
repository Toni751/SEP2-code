package ESharing.Server;

import ESharing.Server.Model.ServerModelManager;
import ESharing.Server.Networking.ServerHandler;
import ESharing.Server.Persistance.AddressDAOImpl;
import ESharing.Server.Persistance.UserDAOImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class for starting the server
 * @version 1.0
 * @author Group 1
 */
public class RunServer
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        ServerHandler server = new ServerHandler(new ServerModelManager(UserDAOImpl.getInstance(
            AddressDAOImpl.getInstance())));
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("Server", server);
        System.out.println("Server started..");
    }
}
