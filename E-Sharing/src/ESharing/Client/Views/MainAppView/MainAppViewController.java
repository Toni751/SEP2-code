package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;

public class MainAppViewController extends ViewController {

    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private User loggerUser;

    public void init(User loggedUser)
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.loggerUser = loggedUser;
    }

    public void onSettingButton()
    {
        viewHandler.openMainSettingView(loggerUser);
    }

}
