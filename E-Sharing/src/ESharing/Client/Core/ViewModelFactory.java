package ESharing.Client.Core;

import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import ESharing.Client.Views.WelcomeView.WelcomeViewModel;

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
}
