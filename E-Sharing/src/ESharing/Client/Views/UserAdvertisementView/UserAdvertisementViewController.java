package ESharing.Client.Views.UserAdvertisementView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Views;
import com.sun.webkit.Timer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.beans.EventHandler;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.List;

/**
 * The controller class used to display the edit user info view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class UserAdvertisementViewController extends ViewController {

    @FXML private VBox mainVBox;
    @FXML private ScrollPane scrollPane;
    private ViewHandler viewHandler;
    private UserAdvertisementViewModel userAdvertisementViewModel;
    private GridPane gridPane;
    private int currentRow;
    private int currentColumn;

    /**
     * Initializes and opens the edit user info view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        userAdvertisementViewModel = ViewModelFactory.getViewModelFactory().getUserAdvertisementViewModel();
        userAdvertisementViewModel.defaultView();

        createGridPane();

        userAdvertisementViewModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::updateAdView);
    }

    /**
     * Sends a request for creation an advertisement preview
     */
    public void createGridPane(){
        List<CatalogueAd> advertisements = userAdvertisementViewModel.getOwnCatalogues();
        gridPane = new GridPane();
        gridPane.setPrefWidth(820);
        gridPane.setMaxWidth(820);
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        currentRow = 0;
        currentColumn = 0;
        int currentAdvertisement = 0;

        int ads = advertisements.size();
        for(int i = 0 ;  i < ads/3; i++){
            for(int j = 0 ; j < 3 ; j++){
                gridPane.add(viewHandler.createAdvertisementComponent(userAdvertisementViewModel.getOwnCatalogues().get(currentAdvertisement), String.valueOf(userAdvertisementViewModel.getOwnCatalogues().get(currentAdvertisement).getAdvertisementID()),currentAdvertisement), currentColumn, currentRow);
                currentColumn++;
                currentAdvertisement++;
            }

            currentColumn = 0;
            currentRow++;
        }

        if(ads%3 != 0){
            for( int i = 0 ; i < ads%3; i++){
                gridPane.add(viewHandler.createAdvertisementComponent(userAdvertisementViewModel.getOwnCatalogues().get(currentAdvertisement), String.valueOf(userAdvertisementViewModel.getOwnCatalogues().get(currentAdvertisement).getAdvertisementID()), currentAdvertisement), currentColumn, currentRow);
                currentColumn++;
                currentAdvertisement++;
            }
        }
        scrollPane.setContent(gridPane);
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void updateAdView(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(this::createGridPane);
    }
}
