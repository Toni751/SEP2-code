package ESharing.Client.Views.UserAdvertisementView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
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

                int finalCurrentAdvertisement = currentAdvertisement;
                advertisement.getChildren().addAll(mainImage, title, price);
                advertisement.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    System.out.println("Clicked");
                    if (userAdvertisementViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement).getAdvertisementID()))
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
                    if (userAdvertisementViewModel.selectAdvertisement(advertisements.get(finalCurrentAdvertisement).getAdvertisementID()))
                        viewHandler.openAdvertisementView();
                });
                row.getChildren().add(advertisement);
                currentAdvertisement++;
            }
            mainVBox.getChildren().add(row);
        }
    }
}
