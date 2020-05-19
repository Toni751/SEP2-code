package ESharing.Server;

import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Core.StubFactory;
import ESharing.Server.Persistance.DAOFactory;

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
        DAOFactory daoFactory = new DAOFactory();
        ServerModelFactory serverModelFactory = new ServerModelFactory(daoFactory);
        StubFactory stubFactory = new StubFactory(serverModelFactory);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("Server", stubFactory);
        System.out.println("Server started..");
    }
}
