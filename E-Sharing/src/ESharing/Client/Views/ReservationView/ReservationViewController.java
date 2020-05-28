package ESharing.Client.Views.ReservationView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Views;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The controller class used to display the reservation view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class ReservationViewController extends ViewController {

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> titleColumn;
    @FXML private TableColumn<Reservation, Double> totalPrice;
    @FXML private TableColumn<Reservation, String> ownerColumn;
    @FXML private TableColumn<Reservation, JFXDatePicker> dateColumn;
    @FXML private JFXButton openButton;
    @FXML private JFXButton removeButton;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;

    private ReservationViewModel reservationViewModel;
    private ViewHandler viewHandler;

    /**
     * Initializes and opens the reservation view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        reservationViewModel = ViewModelFactory.getViewModelFactory().getReservationViewModel();
        reservationViewModel.defaultView();

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("advertisementTitle"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerUsername"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationsComponent"));

        openButton.disableProperty().bindBidirectional(reservationViewModel.getOpenProperty());
        removeButton.disableProperty().bindBidirectional(reservationViewModel.getRemoveProperty());
        warningLabel.textProperty().bind(reservationViewModel.getWarningProperty());
        warningPane.visibleProperty().bindBidirectional(reservationViewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bind(reservationViewModel.getWarningStyleProperty());

        reservationTable.setItems(reservationViewModel.getAllReservations());
        reservationTable.setOnMouseClicked(this::onReservationSelected);
    }

    /**
     * Opens advertisements view
     */
    public void onOpenAdvertisement() {
        if(reservationViewModel.loadReservation()) {
            LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
            viewHandler.openMainAppView();
        }

    }

    /**
     * Sends a request for removing
     */
    public void onRemoveAction() {
        reservationViewModel.removeReservation();
    }

    /**
     * Sends a request for selecting advertisement
     * @param mouseEvent mouse clicked event
     */
    private void onReservationSelected(MouseEvent mouseEvent) {
        int index = reservationTable.getSelectionModel().getSelectedIndex();
        Reservation selectedReservation = reservationTable.getItems().get(index);
        reservationViewModel.setSelectedReservation(selectedReservation);
        reservationViewModel.enableManagingActions();
    }
}
