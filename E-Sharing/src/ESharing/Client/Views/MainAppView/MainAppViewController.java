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
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Initializes and opens the main system view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {

        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();

        if(LoggedUser.getLoggedUser().getSelectedUser() != null)
            viewHandler.openChatView(contentPane);

        mainAppViewModel.resetRectanglesVisibleProperty();

        messageNotification.textProperty().bind(mainAppViewModel.getNotificationProperty());
        addAdvertisementRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getAdRectangleVisibleProperty());
        settingRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getSettingRectangleVisibleProperty());
        messageRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getMessageRectangleVisibleProperty());
        homeRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getHomeRectangleVisibleProperty());
        searchComboBox.valueProperty().bindBidirectional(mainAppViewModel.getSearchValueProperty());

        searchComboBox.setItems(mainAppViewModel.getSearchItemsProperty());

        mainAppViewModel.loadNotifications();
        createMainView();

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
                        viewHandler.openAdvertisementView();
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
                        viewHandler.openAdvertisementView();
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
                    viewHandler.openAdvertisementView();
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
                    viewHandler.openAdvertisementView();
                });
                row.getChildren().add(advertisement);
                currentAdvertisement++;
            }
            mainVBox.getChildren().add(row);
        }

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


    //TEST!!!!!
//    public void onOpenAdvertisement() {
//        AdvertisementModel advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
//        Map<String, File> images = new HashMap<>();
//        File mainImage = new File("E-Sharing/src/ESharing/Addition/Images/icons/ad-icon.png");
//        images.put(AdImages.MAIN_IMAGE.toString(), mainImage);
//
//
//        Map<String, byte[]> imagesConverted = advertisementModel.convertedImages(images);
//
//        User owner = new User("TestUser", "TestPassword", "TestPhone", new Address("TestStreet", "TestCity"));
//
//        ArrayList<LocalDate> dates = new ArrayList<>();
//        dates.add(LocalDate.now().plusDays(1));
//        dates.add(LocalDate.now().plusDays(2));
//        dates.add(LocalDate.now().plusDays(3));
//
//
//
//        Advertisement advertisement = new Advertisement(LoggedUser.getLoggedUser().getUser(), imagesConverted, Vehicles.Bike.toString(), dates, 30, "Test title", "Test description");
//        advertisement.setCreationDate(LocalDate.now());
//        LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
//
//        viewHandler.openAdvertisementView();
//    }

    public void onHomeAction() {
        mainAppViewModel.setHomeRectangleSelected();
        viewHandler.resetMainView();
        viewHandler.openMainAppView();
    }

    public void onSearchAdvertisements() {
        mainAppViewModel.searchAdvertisements();
    }
}
