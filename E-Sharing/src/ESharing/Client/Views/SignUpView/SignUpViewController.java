package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class SignUpViewController extends ViewController {

    @FXML private ImageView arrowBack;
    @FXML private JFXTextField streetTextField;
    @FXML private JFXTextField streetNumberTextField;
    @FXML private JFXTextField phoneTextField;
    @FXML private JFXTextField cityTextField;
    @FXML private JFXTextField postalCodeTextField;
    @FXML private ProgressBar creatingProgressBar2;
    @FXML private ProgressBar creatingProgressBar1;
    @FXML private Circle personalCircle;
    @FXML private Circle addressCircle;
    @FXML private Circle signUpCircle;
    @FXML private JFXPasswordField passwordTextField;
    @FXML private JFXTextField usernameTextField;
    @FXML private JFXPasswordField confirmPasswordTextField;
    @FXML private Pane personalPane;
    @FXML private Pane warningPane;
    @FXML private Pane rulesPane;
    @FXML private Pane addressPane;
    @FXML private Label warningLabel;
    @FXML private Label rulesTitle;
    @FXML private Label signUpTitle;
    @FXML private Button signUpButton;

    private SignUpViewModel signUpViewModel;
    private ViewHandler viewHandler;

    /**
     * Initializes and opens signUn view with all components adds on focus and out focus events to the text fields
     */
    public void init()
    {
        this.signUpViewModel = ViewModelFactory.getViewModelFactory().getSignUpViewModel();
        this.viewHandler = ViewHandler.getViewHandler();

        usernameTextField.textProperty().bindBidirectional(signUpViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signUpViewModel.getPasswordProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(signUpViewModel.getConfirmPasswordProperty());
        phoneTextField.textProperty().bindBidirectional(signUpViewModel.getPhoneProperty());
        warningLabel.textProperty().bind(signUpViewModel.getWarningLabel());

        streetNumberTextField.textProperty().bindBidirectional(signUpViewModel.getNumberProperty());
        streetTextField.textProperty().bindBidirectional(signUpViewModel.getStreetProperty());
        cityTextField.textProperty().bindBidirectional(signUpViewModel.getCityProperty());
        postalCodeTextField.textProperty().bindBidirectional(signUpViewModel.getPostalCodeProperty());

        addOnOutFocusEvent(usernameTextField, creatingProgressBar1);
        addOnOutFocusEvent(passwordTextField, creatingProgressBar1);
        addOnOutFocusEvent(confirmPasswordTextField, creatingProgressBar1);
        addOnOutFocusEvent(phoneTextField, creatingProgressBar1);

        addOnOutFocusEvent(streetTextField, creatingProgressBar2);
        addOnOutFocusEvent(streetNumberTextField, creatingProgressBar2);
        addOnOutFocusEvent(cityTextField, creatingProgressBar2);
        addOnOutFocusEvent(postalCodeTextField, creatingProgressBar2);

        onOpenSetting();
        defaultView();
        clearFields();
    }

    /**
     * Starts a verification process of the address fields and sends request to create new user in the system
     */
    public void onSignUpButton() {
        if(signUpViewModel.signUpAddressRequest()) {
            addressCircle.setRadius(16);
            addressCircle.setStyle("-fx-fill: #4cdbc4");

            signUpCircle.setRadius(20);
            signUpCircle.setStyle("-fx-fill: orange");


            warningPane.setStyle("-fx-background-color: #DB5461");
            warningLabel.setStyle("-fx-text-fill: white");
            GeneralFunctions.fadeNode("fadeIn", warningPane, 500);
            warningPane.setVisible(true);

            if(signUpViewModel.createNewUser())
            {
                warningPane.setStyle("-fx-background-color: #4cdbc4");
                warningLabel.setStyle("-fx-text-fill: black");
                arrowBack.setDisable(true);
                signUpButton.setDisable(true);
            }
        }
        else {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("fadeIn", warningPane, 500);
        }
    }

    /**
     * Displays rules and conditions of the system
     */
    public void onRulesClick() {
        GeneralFunctions.fadeNode("FadeOut", addressPane, 500);
        addressPane.setVisible(false);
        signUpTitle.setVisible(false);
        rulesPane.toFront();
        GeneralFunctions.fadeNode("FadeIn", rulesPane, 500);
        rulesPane.setVisible(true);
        rulesTitle.setVisible(true);
    }

    /**
     * Starts a verification process of the personal fields and changes current pane to the pane with the address fields
     */
    public void onGoToAddressClick() {
        if(signUpViewModel.signUpPersonalRequest()) {
            GeneralFunctions.fadeNode("FadeOut", personalPane, 500);
            personalPane.setVisible(false);
            GeneralFunctions.fadeNode("FadeIn", addressPane, 500);
            addressPane.setDisable(false);
            addressPane.setVisible(true);

            personalCircle.setRadius(16);
            personalCircle.setStyle("-fx-fill: #4cdbc4");
            addressCircle.setRadius(20);
            addressCircle.setStyle("-fx-fill: orange");
            if(warningPane.visibleProperty().get())
                warningPane.setVisible(false);
        }
        else {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("fadeIn", warningPane, 500);
        }
    }

    /**
     * Closes the rules view and goes back to the address view
     */
    public void onBackToSignUp() {
        GeneralFunctions.fadeNode("FadeOut", rulesPane, 500);
        rulesTitle.setVisible(false);
        rulesPane.setVisible(false);
        rulesPane.toBack();
        GeneralFunctions.fadeNode("FadeIn", addressPane, 500);
        addressPane.setVisible(true);
        signUpTitle.setVisible(true);
    }

    /**
     * Changes pane with address fields to the pane with the personal fields
     */
    public void onGoToPersonalClick() {
        GeneralFunctions.fadeNode("fadeOut", addressPane, 500);
        if(warningPane.visibleProperty().get()) warningPane.setVisible(false);
        addressPane.setVisible(false);
        addressPane.setDisable(true);
        addressCircle.setRadius(16);
        addressCircle.setStyle("-fx-fill: #4cdbc4");

        GeneralFunctions.fadeNode("fadeIn", personalPane, 500);
        personalPane.setVisible(true);
        personalCircle.setStyle("-fx-fill: orange");
        personalCircle.setRadius(20);
    }

    /**
     * Hides warning notification after each keyPressed action in the text fields
     */
    public void onKeyPressed() {
        if(warningPane.visibleProperty().get())
        {
            warningPane.setVisible(false);
        }
    }

    /**
     * Sets all view configurations during the initialization process
     */
    private void onOpenSetting()
    {
        addressPane.setVisible(false);
        warningPane.setVisible(false);
        personalCircle.setStyle("-fx-fill: orange");
        personalCircle.setRadius(20);
    }

    /**
     * Creates an focus out event on the given JavaFx component
     * @param node the given JavaFx component
     * @param progressBar the progress bar component used to display changes after focus out event
     */
    private void addOnOutFocusEvent(Node node, ProgressBar progressBar)
    {
        node.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) GeneralFunctions.setFormProgressBar(progressBar, node, 0.25);
        });
    }

    /**
     * Calls method in the view model that clears all text fields
     */
    public void clearFields()
    {
        signUpViewModel.resetView();
    }

    /**
     * Sets default view and style for javaFX components
     */
    private void defaultView()
    {
        warningPane.setVisible(false);

        personalCircle.setRadius(16);
        personalCircle.setStyle("-fx-fill: #4cdbc4");

        addressCircle.setRadius(16);
        addressCircle.setStyle("-fx-fill: #4cdbc4");

        signUpCircle.setRadius(16);
        signUpCircle.setStyle("-fx-fill: #4cdbc4");

        personalPane.setVisible(true);
        addressPane.setVisible(false);
        GeneralFunctions.fadeNode("fadeIn", personalPane, 500);
        rulesPane.toBack();
        arrowBack.setDisable(false);
        signUpButton.setDisable(false);

        creatingProgressBar1.setProgress(0);
        creatingProgressBar2.setProgress(0);
    }
}
