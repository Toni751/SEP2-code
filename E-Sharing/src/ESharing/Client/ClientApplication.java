package ESharing.Client;

import ESharing.Client.Core.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import java.rmi.RMISecurityManager;

/**
 * The java class starts the javaFX application and sets the security for the RMI
 * @version 1.0
 * @author Group1
 */
public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) {
        System.setProperty("java.security.policy", "security.policy");

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new RMISecurityManager());
        ViewHandler viewHandler = ViewHandler.getViewHandler();
        viewHandler.start();
    }
}
