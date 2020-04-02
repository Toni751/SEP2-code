package ESharing.Client;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.RMISecurityManager;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) {
        System.setProperty("java.security.policy", "security.policy");

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }

        ClientFactory clientFactory = new ClientFactory();
        ModelFactory modelFactory = new ModelFactory(clientFactory);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);

        viewHandler.start();
    }
}
