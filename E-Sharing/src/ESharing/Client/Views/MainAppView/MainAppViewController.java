package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Shared.TransferedObject.User;

public class MainAppViewController {

    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private User loggerUser;

    public void init(ViewHandler viewHandler, MainAppViewModel mainAppViewModel, User loggedUser)
    {
        this.viewHandler = viewHandler;
        this.mainAppViewModel = mainAppViewModel;
        this.loggerUser = loggedUser;
    }

    public void onSettingButton()
    {
        viewHandler.openMainSettingView(loggerUser);
    }

}
