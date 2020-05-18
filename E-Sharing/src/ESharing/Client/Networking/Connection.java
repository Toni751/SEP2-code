package ESharing.Client.Networking;

import ESharing.Server.Core.StubInterface;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Connection implements PropertyChangeSubject {

    private static StubInterface stubInterface;
    private static PropertyChangeSupport support;

    public Connection()
    {
        support = new PropertyChangeSupport(this);
    }

    public static StubInterface getStubInterface()
    {
            if (stubInterface == null) {
                try {
                    stubInterface = (StubInterface) LocateRegistry.getRegistry("localhost", 1099).lookup("Server");
                    System.out.println("Client connected");
                } catch (RemoteException | NotBoundException e) {
                    support.firePropertyChange(Events.CONNECTION_FAILED.toString(), null, null);
                }
            }
            return stubInterface;
    }

    public void closeConnection()
    {
        stubInterface = null;
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
