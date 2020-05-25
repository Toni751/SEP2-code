package ESharing.Client.Views.UserAdvertisementView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Views;
import com.sun.webkit.Timer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.beans.EventHandler;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.List;

public class UserAdvertisementViewController extends ViewController {

    @FXML private VBox mainVBox;
    private ViewHandler viewHandler;
    private UserAdvertisementViewModel userAdvertisementViewModel;

    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        userAdvertisementViewModel = ViewModelFactory.getViewModelFactory().getUserAdvertisementViewModel();
        userAdvertisementViewModel.defaultView();

        createMainView();

        userAdvertisementViewModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::updateAdView);
    }

    private void updateAdView(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(this::createMainView);

    }

    public void createMainView() {
        mainVBox.getChildren().clear();
        List<CatalogueAd> advertisements = userAdvertisementViewModel.getOwnCatalogues();

        System.out.println(advertisements);

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


                DatePicker datePicker = new DatePicker();
                Button button = new Button("Open profile");
                Label usernameLabel = new Label("Username");
                Pane userPane = new Pane();
                userPane.getChildren().addAll(button, usernameLabel);
                PopOver popOver = new PopOver(userPane);
                int finalCurrentAdvertisement1 = currentAdvertisement;
                Callback<DatePicker, DateCell> dayCellFactory = (DatePicker datePicker1) -> new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for(int i = 0 ; i < userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().size() ; i++){
                            if(userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().get(i).getReservationDays().contains(item)){
                                setStyle("-fx-background-color: #4CDBC4;");
                                int finalI = i;
                                addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {

                                    //popOver.setTitle("User Info");
                                    User reservationUser = userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().get(finalI).getRequestedUser();
                                    usernameLabel.textProperty().setValue(reservationUser.getUsername());
                                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent1 -> {
                                        LoggedUser.getLoggedUser().setSelectedUser(reservationUser);
                                        LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
                                        viewHandler.openMainAppView();
                                        datePicker.hide();
                                    });
                                    popOver.show(datePicker);
                                    //popOver.detach();
                                });
                            }

                        }
                    }
                };
                datePicker.setMaxWidth(10);
                datePicker.setDayCellFactory(dayCellFactory);

                advertisement.getChildren().add(datePicker);


                advertisement.getChildren().addAll(mainImage, title, price);

                int finalCurrentAdvertisement = currentAdvertisement;
                advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    System.out.println("Clicked");
                    if (userAdvertisementViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement).getAdvertisementID())) {
                        LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
                        viewHandler.openMainAppView();
                    }
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


                DatePicker datePicker = new DatePicker();
                Button button = new Button("Open profile");
                Label usernameLabel = new Label("Username");
                Pane userPane = new Pane();
                userPane.getChildren().addAll(button, usernameLabel);
                PopOver popOver = new PopOver(userPane);
                int finalCurrentAdvertisement1 = currentAdvertisement;
                Callback<DatePicker, DateCell> dayCellFactory = (DatePicker datePicker1) -> new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for(int i = 0 ; i < userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().size() ; i++){
                            if(userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().get(i).getReservationDays().contains(item)){
                                setStyle("-fx-background-color: #4CDBC4;");
                                int finalI = i;
                                addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {

                                    //popOver.setTitle("User Info");
                                    User reservationUser = userAdvertisementViewModel.getOwnCatalogues().get(finalCurrentAdvertisement1).getReservations().get(finalI).getRequestedUser();
                                    usernameLabel.textProperty().setValue(reservationUser.getUsername());
                                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent1 -> {
                                        LoggedUser.getLoggedUser().setSelectedUser(reservationUser);
                                        LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
                                        viewHandler.openMainAppView();
                                        datePicker.hide();
                                    });
                                    popOver.show(datePicker);
                                    //popOver.detach();
                                });
                            }

                        }
                    }
                };
                datePicker.setMaxWidth(10);
                datePicker.setDayCellFactory(dayCellFactory);

                advertisement.getChildren().add(datePicker);


                advertisement.getChildren().addAll(mainImage, title, price);

                int finalCurrentAdvertisement = currentAdvertisement;
                advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    System.out.println("Clicked");
                    if (userAdvertisementViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement).getAdvertisementID())) {
                        LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
                        viewHandler.openMainAppView();
                    }
                });
                row.getChildren().add(advertisement);
                currentAdvertisement++;
            }
            mainVBox.getChildren().add(row);
        }
    }

    public void onOpenAdvertisement(ActionEvent actionEvent) {
    }

    public void onRemoveAdvertisement(ActionEvent actionEvent) {
    }
}
