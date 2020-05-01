package ESharing.Client.Views.AdminDashboardView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

public class AdminDashboardViewController extends ViewController {

    @FXML private AreaChart areaChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label userNumberLabel;
    @FXML private PieChart pieChart;

    private PieChart.Data data1;
    private PieChart.Data data2;
    private PieChart.Data data3;

    private AdminDashboardViewModel viewModel;

    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getAdminDashboardViewModel();

        pieChart.setLegendVisible(true);
        xAxis.setGapStartAndEnd(false);
        areaChart.getXAxis().setAutoRanging(true);
        areaChart.getYAxis().setAutoRanging(true);
        pieChart.setLegendSide(Side.RIGHT);

        data1 = new PieChart.Data("0 Reports", 0);
        data2 = new PieChart.Data(">10 Reports", 0);
        data3 = new PieChart.Data(">5 Reports", 0);

        userNumberLabel.textProperty().bind(viewModel.getUserNumberProperty());
        data1.pieValueProperty().bindBidirectional(viewModel.getData1Property());
        data2.pieValueProperty().bindBidirectional(viewModel.getData2Property());
        data3.pieValueProperty().bindBidirectional(viewModel.getData3Property());



        ObservableList<PieChart.Data> userReportsData = FXCollections.observableArrayList(data1, data2, data3);
        ObservableList<XYChart.Series> userAccounts = FXCollections.observableArrayList(viewModel.getUsersSeries());

        pieChart.setData(userReportsData);
        areaChart.setData(userAccounts);

        updateCharts();
    }


    public void updateCharts()
    {
        viewModel.loadLastWeekCreatedUsers();
        viewModel.loadPieChartValues();
    }
}
