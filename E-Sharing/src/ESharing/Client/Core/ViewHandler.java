package ESharing.Client.Core;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Client.Views.ViewControllerFactory;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Views;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.time.LocalDate;
import java.util.Random;

/**
 * The class responsible for managing views
 * @version 1.0
 * @author Group1
 */
public class ViewHandler {

    private Stage currentStage;
    private Scene currentScene;
    private String css;
    private ViewController viewController;

    private double xOffset;
    private double yOffset;

    private static ViewHandler viewHandler;

    /**
     * A constructor initializes fields
     */
    private ViewHandler()
    {
        xOffset = 0;
        yOffset = 0;
        css = this.getClass().getResource("../../Addition/Styles/Styles.css").toExternalForm();
    }

    /**
     * Returns ViewHandler object if it exists, otherwise creates new object
     * @return the ViewHandler object
     */
    public static ViewHandler getViewHandler()
    {
        if(viewHandler == null)
            viewHandler = new ViewHandler();
        return viewHandler;
    }

    /**
     * Sets main stage and opens default view
     */
    public void start()
    {
        currentStage = new Stage();
        if(currentStage.getScene() == null) currentStage.initStyle(StageStyle.TRANSPARENT);
        openWelcomeView();
    }

    /**
     * Opens welcome view
     */
    public void openWelcomeView()
    {
        ViewControllerFactory.clearViews();
        viewController = ViewControllerFactory.getViewController(Views.WELCOME_VIEW);
        showView(viewController,null);
    }

    /**
     * Loads signIn view to existing pane in the welcome view
     * @param existingPane the existing pane where signIn view will be loaded
     */
    public void openSignInView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.SIGN_IN_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Loads signUp view to existing pane in the welcome view
     * @param existingPane the existing pane where signUp view will be loaded
     */
    public void openSignUpView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.SIGN_UP_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the the main user setting view
     */
    public void openMainSettingView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.MAIN_USER_SETTING_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the main application view
     */
    public void openMainAppView()
    {
        viewController = ViewControllerFactory.getViewController(Views.MAIN_APP_VIEW);
        showView(viewController, null);
    }

    /**
     * Opens the main admin view
     */
    public void openAdminMainView()
    {
        viewController = ViewControllerFactory.getViewController(Views.MAIN_ADMIN_VIEW);
        showView(viewController, null);
    }

    /**
     * Opens the view with functionalities to edit the administrator account
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openEditAdminView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.EDIT_ADMIN_ACCOUNT);
        showView(viewController, existingPane);
    }

    /**
     * Open the manageUsers view
     */
    public void openManagesUsersView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.MANAGE_USERS_VIEW);
        showView(viewController,existingPane);
    }


    /**
     * Opens a view with user information settings
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserInfoSettingView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_INFO_SETTING_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a view with user address settings
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserAddressSettingView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_ADDRESS_SETTING_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a admin dashboard view
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openAdminDashboardView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.ADMIN_DASHBOARD_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a admin edit user view
     */
    public void openAdminEditUserView()
    {
        viewController = ViewControllerFactory.getViewController(Views.ADMIN_EDIT_USER_VIEW);
        showView(viewController, null);
    }

    /**
     * Opens the chat view
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openChatView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.CHAT_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the view with creation advertisement process
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openAddAdvertisementView(Pane existingPane) {
        viewController = ViewControllerFactory.getViewController(Views.ADD_ADVERTISEMENT_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the preview of the selected advertisement
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openAdvertisementView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.ADVERTISEMENT_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a view with all advertisements for administrator account
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openAdminAdvertisementView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.MANAGE_ADVERTISEMENT_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a preview of the selected user
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the view with all advertisements which are a part of the user account
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserAdvertisementView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_ADVERTISEMENT_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the reservation view
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openReservationView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.RESERVATION_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the views which presents all available advertisements
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openAdsOverviewView(Pane existingPane){
        viewController = ViewControllerFactory.getViewController(Views.ADS_OVERVIEW_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens another stage with a preview of the selected picture
     * @param image the selected picture
     */
    public void openPicturePreview(Image image)
    {
        Stage previewStage = new Stage();
        previewStage.setWidth(800);
        previewStage.setHeight(412);


        System.out.println(image.getWidth());
        BorderPane root = new BorderPane();
        Scene imageScene = new Scene(root);

        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(previewStage.widthProperty());
        imageView.fitHeightProperty().bind(previewStage.widthProperty());
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);


        previewStage.setScene(imageScene);
        previewStage.show();
    }

    /**
     * Creates the advertisement component which is presented to the user
     * @param catalogueAd the Catalogue object which keeps all important information about an advertisement
     * @param id the ID which will be assigned for the component
     * @param currentAd the numer of advertisement for which the component is actually created
     * @return the advertisement component as a JavaFX anchor pane object
     */
    public AnchorPane createAdvertisementComponent(CatalogueAd catalogueAd, String id, int currentAd){
        String[] colors = {"#102B3F", "#004346" , "#828C51", "#2F3E46", "#52796F", "#EF7B45"};
        Random randomColor = new Random();
        DatePicker datePicker = null;
        ImageView calendarImage = new ImageView();
        int random = randomColor.nextInt(colors.length);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(200);
        anchorPane.setPrefHeight(230);
        anchorPane.setMaxWidth(200);
        anchorPane.setMaxHeight(230);

        Pane background = new Pane();
        background.setPrefHeight(230);
        background.setPrefWidth(200);
        background.setStyle("-fx-background-color: "+ colors[random] +"; -fx-background-radius: 10");

        Rectangle image = new Rectangle();
        image.setFill(new ImagePattern(catalogueAd.getMainImage()));
        image.setLayoutY(10);
        image.setWidth(200);
        image.setHeight(120);
        image.setStyle("-fx-cursor: hand");

        Label title = new Label(catalogueAd.getTitle());
        title.setStyle("-fx-text-fill: #f2f7f7; -fx-font-size: 16px");
        title.setLayoutY(150);
        title.setLayoutX(10);

        Label price = new Label(catalogueAd.getPrice() + "/day");
        price.setStyle("-fx-text-fill: #fff; -fx-font-size: 24px");
        price.setLayoutY(180);
        price.setLayoutX(10);

        if(currentAd != -1){

            datePicker = new DatePicker();
            Button button = new Button("Open profile");
            button.setStyle("-fx-background-color: #f2f7f7; -fx-text-fill: #4cdbc4;-fx-font-size: 14px");
            Label usernameLabel = new Label("Username");
            usernameLabel.setStyle("-fx-font-size: 16; ");
            VBox userPane = new VBox();
            userPane.getChildren().addAll(usernameLabel, button);
            PopOver popOver = new PopOver(userPane);
            DatePicker finalDatePicker = datePicker;
            Callback<DatePicker, DateCell> dayCellFactory = (DatePicker datePicker1) -> new DateCell(){
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    for(int i = 0; i < ViewModelFactory.getViewModelFactory().getUserAdvertisementViewModel().getOwnCatalogues().get(currentAd).getReservations().size() ; i++){

                        if(ViewModelFactory.getViewModelFactory().getUserAdvertisementViewModel().getOwnCatalogues().get(currentAd).getReservations().get(i).getReservationDays().contains(item)){
                            setStyle("-fx-background-color: #4CDBC4;");
                            int finalI = i;
                            System.out.println(currentAd);
                            addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                                User reservationUser = ViewModelFactory.getViewModelFactory().getUserAdvertisementViewModel().getOwnCatalogues().get(currentAd).getReservations().get(finalI).getRequestedUser();
                                usernameLabel.textProperty().setValue(reservationUser.getUsername());
                                button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent1 -> {
                                    LoggedUser.getLoggedUser().setSelectedUser(reservationUser);
                                    LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
                                    viewHandler.openMainAppView();
                                    finalDatePicker.hide();
                                });
                                popOver.show(finalDatePicker);
                            });
                        }

                    }
                }
            };

            datePicker.setDayCellFactory(dayCellFactory);
            datePicker.setLayoutY(180);
            datePicker.setLayoutX(30);
            datePicker.setVisible(false);
            calendarImage.setImage(new Image("ESharing/Addition/Images/icons/calendar-white-icon.png"));
            calendarImage.setLayoutY(180);
            calendarImage.setLayoutX(130);
            calendarImage.setFitHeight(50);
            calendarImage.setFitWidth(50);
            DatePicker finalDatePicker1 = datePicker;
            calendarImage.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
               finalDatePicker1.show();
            });
        }

        anchorPane.getChildren().add(background);
        anchorPane.getChildren().add(image);
        anchorPane.getChildren().add(title);
        anchorPane.getChildren().add(price);
        if(currentAd != -1) {
            anchorPane.getChildren().add(datePicker);
            anchorPane.getChildren().add(calendarImage);
        }
            

        image.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            ViewModelFactory.getViewModelFactory().getAdsOverviewViewModel().selectAdvertisement(catalogueAd);
            LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
            viewHandler.openMainAppView();
        });
        anchorPane.setId(id);
        return anchorPane;

    }

    /**
     * Minimizes the javaFx stage
     */
    public void minimizeWindow()
    {
        currentStage.setIconified(true);
    }

    /**
     * Resets views which are stored in the memory
     */
    public void resetMainView() {
        ViewControllerFactory.clearViews();
    }

    /**
     * Shows views in a current scene or already loaded pane, using the ViewController class
     * @param controller the ViewController class which inheritances all view controllers
     * @param existingPane the already loaded pane where a new view can be loaded
     */
    private void showView(ViewController controller, Pane existingPane)
    {
        Platform.runLater(() -> {
            if(existingPane == null) {
                moveWindowEvents(controller.getRoot());
                if (currentScene == null)  currentScene = new Scene(controller.getRoot());
                currentScene.setRoot(controller.getRoot());
                currentScene.setFill(Color.TRANSPARENT);
                currentScene.getStylesheets().add(css);
                if (currentStage == null) currentStage = new Stage();
                currentStage.setScene(currentScene);
                currentStage.show();

            }
            else {
                existingPane.getChildren().clear();
                existingPane.getChildren().setAll(controller.getRoot());
            }
        });
    }

    /**
     * Adds options to click and drag the main stage
     * @param root the parent which will be owner of click and drag actions
     */
    private void moveWindowEvents(Parent root)
    {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentStage.setX(event.getScreenX() - xOffset);
                currentStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
