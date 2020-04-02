package ESharing.Client;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ClientFactory clientFactory = new ClientFactory();
        ModelFactory modelFactory = new ModelFactory(clientFactory);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);

        viewHandler.start();
    }
}
