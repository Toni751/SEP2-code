package ESharing.Client.Core;

import ESharing.Client.Views.MainAccountSetting.MainSettingViewModel;
import ESharing.Client.Views.MainAppView.MainAppViewModel;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import ESharing.Client.Views.WelcomeView.WelcomeViewModel;
import com.sun.tools.javac.Main;

/**
 * The class responsible for managing all view models
 * @version 1.0
 * @author Group1
 */
public class ViewModelFactory {

    private ModelFactory modelFactory;
    private WelcomeViewModel welcomeViewModel;
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;

    private MainAppViewModel mainAppViewModel;

    private MainSettingViewModel mainSettingViewModel;

    /**
     * One-argument constructor initializes view models and sets class managing models
     * @param mf the class managing models
     */
    public ViewModelFactory(ModelFactory mf)
    {
        this.modelFactory = mf;
        welcomeViewModel = new WelcomeViewModel(modelFactory.getUserAccountModel());
        signInViewModel = new SignInViewModel(modelFactory.getUserAccountModel());
        signUpViewModel = new SignUpViewModel(modelFactory.getUserAccountModel());

        mainAppViewModel = new MainAppViewModel(modelFactory.getAppOverviewModel());

        mainSettingViewModel = new MainSettingViewModel(modelFactory.getUserSettingModel());
    }

    /**
     * Returns initialized view model of the welcome view
     * @return the initialized welcomeViewModel
     */
    public WelcomeViewModel getWelcomeViewModel() {
        return welcomeViewModel;
    }

    /**
     * Returns initialized view model of the signIn view
     * @return the initialized signInViewModel
     */
    public SignInViewModel getSignInViewModel() {
        return signInViewModel;
    }

    /**
     * Returns initialized view model of the signUp view
     * @return the initialized signUpViewModel
     */
    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }

    public MainSettingViewModel getMainSettingViewModel() {
        return mainSettingViewModel;
    }

    public MainAppViewModel getMainAppViewModel() {
        return mainAppViewModel;
    }
}
