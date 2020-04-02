package ESharing.Client.Views.SignInView;

import ESharing.Client.Model.UserAccount.UserAccountModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SignInViewModel {

    StringProperty usernameProperty;
    StringProperty passwordProperty;
    StringProperty warningProperty;

    private UserAccountModel userAccountModel;


    public SignInViewModel(UserAccountModel userModel) {
        this.userAccountModel = userModel;
        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        warningProperty= new SimpleStringProperty();
    }

    public boolean textFieldsVerification()
    {
        boolean verification = true;
        if((passwordProperty.get() == null && usernameProperty.get() == null) || (usernameProperty.get().equals("") && passwordProperty.get().equals(""))) {
            warningProperty.set("Fields are empty!");
            verification = false;
        }
        else if((passwordProperty.get() == null && usernameProperty.get().equals("")) || (usernameProperty.get() == null && passwordProperty.get().equals(""))) {
            warningProperty.set("Fields are empty!");
            verification = false;
        }
        else if(usernameProperty.get() == null || usernameProperty.get().equals("")) {
            warningProperty.set("The username field can not be empty!");
            verification = false;
        }
        else if(passwordProperty.get() == null || passwordProperty.get().equals("")) {
            warningProperty.set("The password field can not be empty!");
            verification = false;
        }
        return verification;
    }

    public void onLoginButton() {

    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

}
