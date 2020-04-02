package ESharing.Client.Core;

import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import ESharing.Client.Views.WelcomeView.WelcomeViewModel;

public class ViewModelFactory {

    private ModelFactory modelFactory;
    private WelcomeViewModel welcomeViewModel;
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;

    public ViewModelFactory(ModelFactory mf)
    {
        this.modelFactory = mf;
        welcomeViewModel = new WelcomeViewModel(modelFactory.getUserAccountModel());
        signInViewModel = new SignInViewModel(modelFactory.getUserAccountModel());
        signUpViewModel = new SignUpViewModel(modelFactory.getUserAccountModel());
    }

    public WelcomeViewModel getWelcomeViewModel() {
        return welcomeViewModel;
    }

    public SignInViewModel getSignInViewModel() {
        return signInViewModel;
    }

    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }
}
