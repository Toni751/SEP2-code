package ESharing.Client.Views.AdminDashboardView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

/**
 * The controller class used to display the administrator dashboard view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class AdminDashboardViewController extends ViewController {

    @FXML private AreaChart areaChart;
    @FXML private Label userNumberLabel;
    @FXML private PieChart pieChart;

    private AdminDashboardViewModel viewModel;

    /**
     * Initializes and opens administrator dashboard view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getAdminDashboardViewModel();
        viewModel.defaultView();

        pieChart.legendSideProperty().bindBidirectional(viewModel.getPieChartLegendProperty());
        pieChart.legendVisibleProperty().bindBidirectional(viewModel.getPieChartLegendVisibleProperty());
        areaChart.getXAxis().autoRangingProperty().bindBidirectional(viewModel.getXAxisRanging());
        areaChart.getYAxis().autoRangingProperty().bindBidirectional(viewModel.getYAxisRanging());
        pieChart.dataProperty().bind(viewModel.getPieChartDate());
        userNumberLabel.textProperty().bind(viewModel.getUserNumberProperty());
        ObservableList<XYChart.Series> userAccounts = FXCollections.observableArrayList(viewModel.getUsersSeries());
        areaChart.setData(userAccounts);
    }
}
