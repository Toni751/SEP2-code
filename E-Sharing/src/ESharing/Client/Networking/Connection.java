package ESharing.Client.Networking;

import ESharing.Server.Core.StubFactory;
import ESharing.Server.Core.StubInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Connection {

    public static StubInterface stubInterface;

    public static StubInterface getStubInterface()
    {
        if(stubInterface == null) {
            while (true) {
                try {
                    stubInterface = (StubInterface) LocateRegistry.getRegistry("localhost", 1099).lookup("Server");
                    System.out.println("Client connected");
                    break;
                } catch (RemoteException | NotBoundException e) {
                    try {
                        System.out.println("Client can't connect with the server... trying again after 5 seconds");
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return stubInterface;
    }
}
