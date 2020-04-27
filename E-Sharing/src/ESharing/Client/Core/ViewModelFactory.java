package ESharing.Client.Core;

import ESharing.Client.Views.MainAccountSetting.MainSettingViewModel;
import ESharing.Client.Views.MainAppView.MainAppViewModel;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import ESharing.Client.Views.UserAddressSettingView.UserAddressSettingViewModel;
import ESharing.Client.Views.UserInfoSettingView.UserInfoSettingViewModel;
import ESharing.Client.Views.WelcomeView.WelcomeViewModel;

/**
 * The class responsible for managing all view models
 * @version 1.0
 * @author Group1
 */
public class ViewModelFactory {

    private WelcomeViewModel welcomeViewModel;
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;
    private MainAppViewModel mainAppViewModel;
    private MainSettingViewModel mainSettingViewModel;
    private UserInfoSettingViewModel userInfoSettingViewModel;
    private UserAddressSettingViewModel userAddressSettingViewModel;
    private static ViewModelFactory viewModelFactory;

    /**
     * A private constructor initializes all view model
     */
    private ViewModelFactory()
    {
        welcomeViewModel = new WelcomeViewModel();
        signInViewModel = new SignInViewModel();
        signUpViewModel = new SignUpViewModel();
        mainAppViewModel = new MainAppViewModel();
        mainSettingViewModel = new MainSettingViewModel();
        userAddressSettingViewModel = new UserAddressSettingViewModel();
        userInfoSettingViewModel = new UserInfoSettingViewModel();
    }

    /**
     * Returns ViewModelFactory object if it exists, otherwise creates new object
     * @return the ViewModelFactory object
     */
    public static ViewModelFactory getViewModelFactory()
    {
        if(viewModelFactory == null)
            viewModelFactory = new ViewModelFactory();
        return viewModelFactory;
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

    /**
     * Returns initialized view model of the main user setting view
     * @return the initialized MainSettingViewModel
     */
    public MainSettingViewModel getMainSettingViewModel() {
        return mainSettingViewModel;
    }

    /**
     * Returns initialized view model of the main system view
     * @return the initialized MainAppViewModel
     */
    public MainAppViewModel getMainAppViewModel() {
        return mainAppViewModel;
    }

    /**
     * Returns initialized view model of the user address setting view
     * @return the initialized UserAddressSettingView
     */
    public UserAddressSettingViewModel getUserAddressSettingViewModel() {
        return userAddressSettingViewModel;
    }

    /**
     * Returns initialized view model of the user information setting view
     * @return the initialized UserInfoSettingView
     */
    public UserInfoSettingViewModel getUserInfoSettingViewModel() {
        return userInfoSettingViewModel;
    }
}
