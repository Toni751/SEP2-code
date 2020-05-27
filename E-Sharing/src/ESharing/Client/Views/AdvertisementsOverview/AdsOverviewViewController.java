package ESharing.Client.Views.AdvertisementsOverview;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.MainAppView.MainAppViewModel;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class AdsOverviewViewController extends ViewController {

    @FXML private JFXComboBox<String> searchComboBox;
    @FXML private ScrollPane scrollPane1;
    @FXML private Pane contentPane;
    private GridPane gridPane;
    private int currentRow;
    private int currentColumn;
    private ViewHandler viewHandler;
    private AdsOverviewViewModel adsOverviewViewModel;

    public void init(){
        this.viewHandler = ViewHandler.getViewHandler();
        this.adsOverviewViewModel = ViewModelFactory.getViewModelFactory().getAdsOverviewViewModel();
        adsOverviewViewModel.setDefaultView();

        crateViewGridPane(null);
        searchComboBox.valueProperty().bindBidirectional(adsOverviewViewModel.getSearchValueProperty());
        searchComboBox.setItems(adsOverviewViewModel.getSearchItemsProperty());

        adsOverviewViewModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::onAdRemoved);
        adsOverviewViewModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), this::onNewAd);
        adsOverviewViewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), this::onUpdateList);
    }

    private void onUpdateList(PropertyChangeEvent propertyChangeEvent) {
        List<CatalogueAd> updatedList = (List<CatalogueAd>) propertyChangeEvent.getNewValue();
        crateViewGridPane(updatedList);
        System.out.println(updatedList.get(0).getTitle());
    }

    private void onNewAd(PropertyChangeEvent propertyChangeEvent) {
        CatalogueAd catalogueAd = (CatalogueAd) propertyChangeEvent.getNewValue();
        AnchorPane anchorPane = viewHandler.createAdvertisementComponent(catalogueAd, String.valueOf(catalogueAd.getAdvertisementID()), -1);
        Platform.runLater(() -> gridPane.add(anchorPane,currentColumn,currentRow));

    }

    private void onAdRemoved(PropertyChangeEvent propertyChangeEvent) {
        int advertisementID = (int) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < gridPane.getChildren().size() ; i++){
            if(gridPane.getChildren().get(i).getId().equals(String.valueOf(advertisementID))) {
                int finalI = i;
                Platform.runLater(() ->gridPane.getChildren().remove(gridPane.getChildren().get(finalI)));
            }
        }
    }

    private void crateViewGridPane(List<CatalogueAd> updatedList){

        List<CatalogueAd> advertisements = null;
        if(updatedList == null) {
            System.out.println("not update");
            advertisements = adsOverviewViewModel.getCatalogueAds();
        }
        else {
            System.out.println("UPDATE LISt");
            advertisements = updatedList;
        }
        gridPane = new GridPane();
        gridPane.setPrefWidth(820);
        gridPane.setMaxWidth(820);
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        currentRow = 0;
        currentColumn = 0;
        int currentAdvertisement = 0;

        int ads = advertisements.size();
        for(int i = 0 ;  i < ads/3; i++){
            for(int j = 0 ; j < 3 ; j++){
                //gridPane.add(viewHandler.createAdvertisementComponent(adsOverviewViewModel.getCatalogueAds().get(currentAdvertisement), String.valueOf(adsOverviewViewModel.getCatalogueAds().get(currentAdvertisement).getAdvertisementID()), -1), currentColumn, currentRow);
                gridPane.add(viewHandler.createAdvertisementComponent(advertisements.get(currentAdvertisement), String.valueOf(advertisements.get(currentAdvertisement).getAdvertisementID()), -1), currentColumn, currentRow);
                currentColumn++;
                currentAdvertisement++;
            }

            currentColumn = 0;
            currentRow++;
        }

        if(ads%3 != 0){
            for( int i = 0 ; i < ads%3; i++){
                gridPane.add(viewHandler.createAdvertisementComponent(advertisements.get(currentAdvertisement), String.valueOf(advertisements.get(currentAdvertisement).getAdvertisementID()),-1), currentColumn, currentRow);
                currentColumn++;
                currentAdvertisement++;
            }
        }
        scrollPane1.setContent(gridPane);
    }

    public void onSearchAdvertisements() {
        System.out.println("comboclicked");
        adsOverviewViewModel.searchAdvertisements();
    }

}
