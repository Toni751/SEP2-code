package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Vehicles;
import ESharing.Shared.Util.Views;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * The controller class used to display the the main system view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    @FXML private JFXComboBox<String> searchComboBox;
    @FXML private Rectangle homeRectangle;
    @FXML private VBox mainVBox;
    @FXML private Label messageNotification;
    @FXML private Rectangle addAdvertisementRectangle;
    @FXML private Rectangle messageRectangle;
    @FXML private Rectangle settingRectangle;
    @FXML private Pane contentPane;
    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;

    @FXML private ScrollPane scrollPane1;
    @FXML private StackPane scrollRoot;
    /**
     * Initializes and opens the main system view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {

        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        selectSubViewToOpen();

        mainAppViewModel.resetRectanglesVisibleProperty();

        messageNotification.textProperty().bind(mainAppViewModel.getNotificationProperty());
        addAdvertisementRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getAdRectangleVisibleProperty());
        settingRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getSettingRectangleVisibleProperty());
        messageRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getMessageRectangleVisibleProperty());
        homeRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getHomeRectangleVisibleProperty());
        searchComboBox.valueProperty().bindBidirectional(mainAppViewModel.getSearchValueProperty());

        searchComboBox.setItems(mainAppViewModel.getSearchItemsProperty());
        mainAppViewModel.loadNotifications();
        //createMainView();
        crateViewGridPane();

        mainAppViewModel.addPropertyChangeListener(Events.USER_LOGOUT.toString(), this::onAdminRemoveAccount);
        mainAppViewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), this::onUpdateList);
    }

    private void onUpdateList(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("EVENT");
        Platform.runLater(() -> {
            mainVBox.getChildren().clear();
            List<CatalogueAd> advertisements = (ArrayList) propertyChangeEvent.getNewValue();

            System.out.println(advertisements);
            int rows = advertisements.size() / 3;
            int rest = advertisements.size() % 3;
            int currentAdvertisement = 0;

            for (int i = 0; i < rows; i++) {
                HBox row = new HBox(37.5);
                for (int j = 0; j < 3; j++) {
                    VBox advertisement = new VBox(5);
                    advertisement.setPrefWidth(200);
                    advertisement.setPrefHeight(180);

                    ImageView mainImage = new ImageView(advertisements.get(currentAdvertisement).getMainImage());
                    mainImage.setFitWidth(200);
                    mainImage.setFitHeight(106);
                    mainImage.preserveRatioProperty();
                    Label title = new Label(advertisements.get(currentAdvertisement).getTitle());
                    Label price = new Label(String.valueOf(advertisements.get(currentAdvertisement).getPrice()));

                    int finalCurrentAdvertisement = currentAdvertisement;
                    advertisement.getChildren().addAll(mainImage, title, price);
                    advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                        System.out.println("Clicked");
                        mainAppViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement));
                        LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
                        viewHandler.openMainAppView();
                    });

                    row.getChildren().add(advertisement);
                    currentAdvertisement++;

                }
                mainVBox.getChildren().add(row);
            }

            if (rest != 0) {
                HBox row = new HBox(37.5);
                for (int i = 0; i < rest; i++) {
                    VBox advertisement = new VBox(5);
                    advertisement.setPrefWidth(200);
                    advertisement.setPrefHeight(180);

                    ImageView mainImage = new ImageView(advertisements.get(currentAdvertisement).getMainImage());
                    mainImage.setFitWidth(200);
                    mainImage.setFitHeight(106);
                    mainImage.preserveRatioProperty();
                    Label title = new Label(advertisements.get(currentAdvertisement).getTitle());
                    Label price = new Label(String.valueOf(advertisements.get(currentAdvertisement).getPrice()));

                    advertisement.getChildren().addAll(mainImage, title, price);

                    int finalCurrentAdvertisement = currentAdvertisement;
                    advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                        System.out.println("Clicked");
                        mainAppViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement));
                        LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
                        viewHandler.openMainAppView();
                    });
                    row.getChildren().add(advertisement);
                    currentAdvertisement++;
                }
                mainVBox.getChildren().add(row);
            }
        });
    }

    public void createMainView() {
        mainVBox.getChildren().clear();
        List<CatalogueAd> advertisements = mainAppViewModel.getCatalogueAds();

        System.out.println(advertisements);
        int rows = advertisements.size() / 3;
        int rest = advertisements.size() % 3;
        int currentAdvertisement = 0;

        for (int i = 0; i < rows; i++) {
            HBox row = new HBox(37.5);
            for (int j = 0; j < 3; j++) {
                VBox advertisement = new VBox(5);
                advertisement.setPrefWidth(200);
                advertisement.setPrefHeight(180);

                ImageView mainImage = new ImageView(advertisements.get(currentAdvertisement).getMainImage());
                mainImage.setFitWidth(200);
                mainImage.setFitHeight(106);
                mainImage.preserveRatioProperty();
                Label title = new Label(advertisements.get(currentAdvertisement).getTitle());
                Label price = new Label(String.valueOf(advertisements.get(currentAdvertisement).getPrice()));

                int finalCurrentAdvertisement = currentAdvertisement;
                advertisement.getChildren().addAll(mainImage, title, price);
                advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    System.out.println("Clicked");
                    mainAppViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement));
                    LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
                    viewHandler.openMainAppView();
                });

                row.getChildren().add(advertisement);
                currentAdvertisement++;

            }
            mainVBox.getChildren().add(row);
        }

        if (rest != 0) {
            HBox row = new HBox(37.5);
            for (int i = 0; i < rest; i++) {
                VBox advertisement = new VBox(5);
                advertisement.setPrefWidth(200);
                advertisement.setPrefHeight(180);

                ImageView mainImage = new ImageView(advertisements.get(currentAdvertisement).getMainImage());
                mainImage.setFitWidth(200);
                mainImage.setFitHeight(106);
                mainImage.preserveRatioProperty();
                Label title = new Label(advertisements.get(currentAdvertisement).getTitle());
                Label price = new Label(String.valueOf(advertisements.get(currentAdvertisement).getPrice()));

                advertisement.getChildren().addAll(mainImage, title, price);

                int finalCurrentAdvertisement = currentAdvertisement;
                advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    System.out.println("Clicked");
                    mainAppViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement));
                    LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
                    viewHandler.openMainAppView();
                });
                row.getChildren().add(advertisement);
                currentAdvertisement++;
            }
            mainVBox.getChildren().add(row);
        }

    }

    private void crateViewGridPane(){

        List<CatalogueAd> advertisements = mainAppViewModel.getCatalogueAds();

        GridPane gridPane = new GridPane();
        gridPane.setPrefWidth(820);
        gridPane.setMaxWidth(820);
        gridPane.setHgap(50);
        gridPane.setVgap(50);



        int currentRow = 0;
        int currentColumn = 0;
        int currentAdvertisement = 0;

        int ads = advertisements.size();
        for(int i = 0 ;  i < ads/3; i++){
            for(int j = 0 ; j < 3 ; j++){
                Node currentNode = createAdComponent(advertisements.get(currentAdvertisement));
                gridPane.add(currentNode, currentColumn, currentRow);
                GridPane.setHalignment(currentNode, HPos.CENTER);
                GridPane.setValignment(currentNode, VPos.CENTER);
                currentColumn++;
                currentAdvertisement++;
            }

            currentColumn = 0;
            currentRow++;
        }

        if(ads%3 != 0){
            for( int i = 0 ; i < ads%3; i++){
                gridPane.add(createAdComponent(advertisements.get(currentAdvertisement)), currentColumn, currentRow);
                currentColumn++;
                currentAdvertisement++;
            }
        }
        scrollPane1.setContent(gridPane);
    }


    private AnchorPane createAdComponent(CatalogueAd catalogueAd){
        String[] colors = {"#102B3F", "#004346", "#828C51", "#2F3E46", "#52796F", "#EF7B45"};
        Random randomColor = new Random();
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
       anchorPane.setStyle("-fx-cursor: hand");

       Rectangle image = new Rectangle();
       image.setFill(new ImagePattern(catalogueAd.getMainImage()));
       image.setLayoutY(10);
       image.setWidth(200);
       image.setHeight(120);

       Label title = new Label(catalogueAd.getTitle());
       title.setStyle("-fx-text-fill: #f2f7f7; -fx-font-size: 16px");
       title.setLayoutY(150);
       title.setLayoutX(10);

       Label price = new Label(catalogueAd.getPrice() + "/day");
       price.setStyle("-fx-text-fill: #fff; -fx-font-size: 24px");
       price.setLayoutY(180);
       price.setLayoutX(10);

       anchorPane.getChildren().add(background);
       anchorPane.getChildren().add(image);
       anchorPane.getChildren().add(title);
       anchorPane.getChildren().add(price);

        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.println("Clicked");
            mainAppViewModel.selectAdvertisement(catalogueAd);
            LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
            viewHandler.openMainAppView();
        });

       return anchorPane;
    }


    /**
     * Sends a request to the view model layer to setting visible property of the setting rectangle object
     * Sends a request to the view handler to open the main user setting view
     */
    public void onSettingButton()
    {
        mainAppViewModel.setSettingRectangleSelected();
        viewHandler.openMainSettingView(contentPane);
    }

    /**
     * Sends a request to the view model layer to setting visible property of the message rectangle object
     * Sends a request to the view handler to open the chat view
     */
    public void onChatButton()
    {
        mainAppViewModel.setMessageRectangleSelected();
        viewHandler.openChatView(contentPane);
    }

    /**
     * Sends a request to the view model layer to setting visible property of the add advertisement rectangle object
     * Sends a request to the view handler to open the create advertisement view
     */
    public void onGoToAddAdvertisement() {
        mainAppViewModel.setAdRectangleSelected();
        viewHandler.openAddAdvertisementView(contentPane);
    }

    /**
     * Logs out current user and sends a request to the view handler to open the welcome view
     */
    public void onLogout() {
        mainAppViewModel.userLoggedOut();
        viewHandler.openWelcomeView();
    }

    /**
     * Sends a request to the view handler to minimizing the current stage
     */
    public void onMinimizeAction() {
        viewHandler.minimizeWindow();
    }

    /**
     * Sends a request to the view model layer to logout the user and closes the application
     */
    public void onCloseButtonAction() {
        mainAppViewModel.userLoggedOut();
        System.exit(0);
    }

    /**
     * Sends a request to the view handler to open a welcome view when an administrator removes the account of a current logged user
     * @param propertyChangeEvent the administrator removing account event
     */
    private void onAdminRemoveAccount(PropertyChangeEvent propertyChangeEvent) {
        viewHandler.openWelcomeView();
    }


    public void onHomeAction() {
        mainAppViewModel.setHomeRectangleSelected();
        viewHandler.resetMainView();
        viewHandler.openMainAppView();
    }

    public void onSearchAdvertisements() {
        mainAppViewModel.searchAdvertisements();
    }

    private void selectSubViewToOpen(){
        Views requestedView = LoggedUser.getLoggedUser().getSelectedView();
        if(requestedView == Views.ADVERTISEMENT_VIEW)
            viewHandler.openAdvertisementView(contentPane);
        else if(requestedView == Views.USER_VIEW)
            viewHandler.openUserView(contentPane);
        else if(requestedView == Views.CHAT_VIEW)
            viewHandler.openChatView(contentPane);

        LoggedUser.getLoggedUser().setSelectedView(null);
    }
}
