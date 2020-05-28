package ESharing.Client.Views.MainAccountSetting;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import java.util.Optional;

/**
 * The controller class used to display the main user setting view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class MainAccountSettingController extends ViewController {

    @FXML private Pane userSettingPane;

    private ViewHandler viewHandler;
    private MainSettingViewModel mainSettingViewModel;

    /**
     * Initializes and opens the main user setting view with all components,
     * Sends a request to the view handler to load the user info setting view into the userSettingPane
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainSettingViewModel = ViewModelFactory.getViewModelFactory().getMainSettingViewModel();
        viewHandler.openUserInfoSettingView(userSettingPane);
    }

    /**
     * Displays confirmation alert,
     * Sends a request to the view model layer for removing current logged user
     */
    public void removeAccountButton() {
        Alert removeConfirmation =  GeneralFunctions.ShowConfirmationAlert("Confirm removing", "Are you sure? This operation can not be restored");
        Optional<ButtonType> result = removeConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            mainSettingViewModel.removeAccount();
            viewHandler.openWelcomeView();
        } else {
            removeConfirmation.close();
        }
    }

    /**
     * Sends a request to the view handler to open the user info setting view into the userSettingPane
     */
    public void loadAboutPane() {
        viewHandler.openUserInfoSettingView(userSettingPane);
    }

    /**
     * Sends a request to the view handler to open the user address setting view into the userSettingPane
     */
    public void loadAddressPane() {
        viewHandler.openUserAddressSettingView(userSettingPane);
    }

    /**
     * Sends a request to the view handler to open the user advertisement setting view into the userSettingPane
     */
    public void loadAdvertisementsPane() {
        viewHandler.openUserAdvertisementView(userSettingPane);
    }

    /**
     * Sends a request to the view handler to open the user reservation setting view into the userSettingPane
     */
    public void loadReservationPane(){
        viewHandler.openReservationView(userSettingPane);
    }
}
