package ESharing.Client.Views.SignUpView;

import ESharing.Client.Views.SignInView.SignInViewModel;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class SignUpViewController {

    public JFXTextField streetTextField;
    public JFXTextField streetNumberTextField;
    public JFXTextField phoneTextField;
    public JFXTextField cityTextField;
    public JFXTextField postalCodeTextField;
    public ProgressBar creatingProgressBar2;
    public ProgressBar creatingProgressBar1;
    public Circle personalCircle;
    public Circle addressCircle;
    public Pane addressPane;
    public JFXTextField passwordTextField;
    public JFXTextField usernameTextField;
    public JFXTextField confirmPasswordTextField;
    public Pane personalPane;

    private SignUpViewModel signInViewModel;
    
    public void init(SignUpViewModel signInVM)
    {
        this.signInViewModel = signInVM;
        addressPane.setVisible(false);
    }
    
    public void onSignUpButton(ActionEvent actionEvent) {
    }

    public void onRulesClick(MouseEvent mouseEvent) {
    }

    public void onGoToAddressClick(MouseEvent mouseEvent) {
    }

    public void onGoToPersonalClick(MouseEvent mouseEvent) {
    }
}
