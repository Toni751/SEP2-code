package ESharing.Client.Views.MainAccountSetting;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Shared.TransferedObject.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MainAccountSettingController {

    private ViewHandler viewHandler;
    private MainSettingViewModel mainSettingViewModel;
    private User loggedUser;


    public void init(User loggedUser)
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainSettingViewModel = ViewModelFactory.getViewModelFactory().getMainSettingViewModel();
        this.loggedUser = loggedUser;
    }


    public void removeAccountButton() {
        Alert removeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        removeConfirmation.setTitle("Confirm removing");
        removeConfirmation.setHeaderText(null);
        removeConfirmation.setContentText("Are you sure? This operation can not be restored");
        Optional<ButtonType> result = removeConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            mainSettingViewModel.removeAccount(loggedUser);
            viewHandler.openWelcomeView();
        } else {
            removeConfirmation.close();
        }
    }

    public void loadAboutPane() {
    }

    public void loadAddressPane() {
    }
}
