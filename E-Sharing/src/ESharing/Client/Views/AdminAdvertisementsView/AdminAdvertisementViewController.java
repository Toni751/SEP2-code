package ESharing.Client.Views.AdminAdvertisementsView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The controller class used to display the administrator advertisement view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class AdminAdvertisementViewController extends ViewController {

    @FXML private JFXButton goToSelectedAdvertisementButton;
    @FXML private JFXComboBox<String> searchComboBox;
    @FXML private Label notApprovedAdvertisements;
    @FXML private Label totalAdvertisement;
    @FXML private AnchorPane mainPane;
    @FXML private JFXButton removeAdvertisementButton;
    @FXML private JFXButton approveAdvertisementButton;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    @FXML private TableColumn<AdCatalogueAdmin, Integer> adIdColumn;
    @FXML private TableColumn<AdCatalogueAdmin, String> adTitleColumn;
    @FXML private TableColumn<AdCatalogueAdmin, String> adTypeColumn;
    @FXML private TableColumn<AdCatalogueAdmin, Integer> adReportsColumn;
    @FXML private TableView<AdCatalogueAdmin> advertisementTable;

    private ViewHandler viewHandler;
    private AdminAdvertisementsViewModel adminAdvertisementsViewModel;

    /**
     * Initializes and opens administrator advertisement view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        adminAdvertisementsViewModel = ViewModelFactory.getViewModelFactory().getAdminAdvertisementsViewModel();
        adminAdvertisementsViewModel.loadDefaultView();

        adIdColumn.setCellValueFactory(new PropertyValueFactory<>("advertisementID"));
        adTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        adTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        adReportsColumn.setCellValueFactory(new PropertyValueFactory<>("reportsNumber"));

        goToSelectedAdvertisementButton.disableProperty().bindBidirectional(adminAdvertisementsViewModel.getOpenAdDisableProperty());
        removeAdvertisementButton.disableProperty().bindBidirectional(adminAdvertisementsViewModel.getRemoveDisableProperty());
        approveAdvertisementButton.disableProperty().bindBidirectional(adminAdvertisementsViewModel.getApproveDisableProperty());

        searchComboBox.setItems(adminAdvertisementsViewModel.getComboItems());
        searchComboBox.valueProperty().bindBidirectional(adminAdvertisementsViewModel.getComboValueProperty());
        notApprovedAdvertisements.textProperty().bind(adminAdvertisementsViewModel.getNotApprovedProperty());
        totalAdvertisement.textProperty().bind(adminAdvertisementsViewModel.getTotalProperty());
        warningLabel.textProperty().bind(adminAdvertisementsViewModel.getWarningProperty());
        warningPane.visibleProperty().bindBidirectional(adminAdvertisementsViewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(adminAdvertisementsViewModel.getWarningStyleProperty());

        advertisementTable.setItems(adminAdvertisementsViewModel.getAllAdvertisement());

        advertisementTable.setOnMouseClicked(this :: selectAdvertisement);
    }

    /**
     * Sends a request to the view model layer for approving advertisement
     */
    public void approveSelectedAdvertisement() {
        adminAdvertisementsViewModel.approveAdvertisement();
    }

    /**
     * Opens view of the selected advertisement
     */
    public void goToSelectedAdvertisement() {
        viewHandler.openAdvertisementView(null);
    }

    /**
     * Sends a request to the view model layer for removing advertisement
     */
    public void onRemoveAdAction() {
        adminAdvertisementsViewModel.removeSelectedAdvertisement();
    }

    /**
     * Sends a request to the view model layer for searching advertisement
     */
    public void onSearchAdvertisements() {
        adminAdvertisementsViewModel.searchAdvertisements();
    }

    /**
     * Sends a request to the view model layer for selecting advertisement
     */
    private void selectAdvertisement(MouseEvent mouseEvent) {
        int index = advertisementTable.getSelectionModel().getSelectedIndex();
        adminAdvertisementsViewModel.selectAdvertisement(advertisementTable.getItems().get(index));
    }

}
