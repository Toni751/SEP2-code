package ESharing.Client.Views.AdminDashboardView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The class in a view model layer contains all functions which are used in the administrator dashboard view.
 * @version 1.0
 * @author Group1
 */
public class AdminDashboardViewModel {

    private XYChart.Series usersSeries;
    private SimpleObjectProperty<ObservableList<PieChart.Data>> pieChartDataProperty;
    private StringProperty userNumberProperty;

    private PieChart.Data pieData1;
    private PieChart.Data pieData2;
    private PieChart.Data pieData3;

    private SimpleObjectProperty<Side> pieChartLegendProperty;
    private SimpleBooleanProperty pieChartLegendVisibleProperty;
    private SimpleBooleanProperty xAxisRangingProperty;
    private SimpleBooleanProperty yAxisRangingProperty;
    private SimpleStringProperty advertisementsNumberProperty;
    private AdministratorActionsModel model;
    private AdvertisementModel advertisementModel;
    private int todayUsers;
    private String todayString;
    private double zeroReports;

    /**
     * A constructor initializes the model layer for a administrator features and all fields
     */
    public AdminDashboardViewModel()
    {
        model = ModelFactory.getModelFactory().getAdministratorActionsModel();
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        pieData1 = new PieChart.Data("0 Reports", 0);
        pieData2 = new PieChart.Data(">10 Reports", 0);
        pieData3 = new PieChart.Data(">5 Reports", 0);

        userNumberProperty = new SimpleStringProperty();
        pieChartLegendProperty = new SimpleObjectProperty<>();
        pieChartLegendVisibleProperty = new SimpleBooleanProperty();
        advertisementsNumberProperty = new SimpleStringProperty();
        xAxisRangingProperty = new SimpleBooleanProperty();
        yAxisRangingProperty = new SimpleBooleanProperty();
        pieChartDataProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList(pieData1, pieData2, pieData3));
        usersSeries = new XYChart.Series();

        model.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this::reloadDashboard);
        model.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::reloadDashboard);
        advertisementModel.addPropertyChangeListener(Events.NEW_AD_REQUEST.toString(), this::updateNumber);
        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::updateNumber);
    }

    /**
     * Selects all days from a previous week,
     * checks how many users created accounts in each day and loads all data as area chart series
     */
    public void loadLastWeekCreatedUsers()
    {
            todayUsers = 0;
            int usersMinus1Day = 0;
            int usersMinus2Day = 0;
            int usersMinus3Day = 0;
            int usersMinus4Day = 0;
            int usersMinus5Day = 0;
            int usersMinus6Day = 0;

            LocalDate today = LocalDate.now();
            todayString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus1Day = today.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus2Day = today.minusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus3Day = today.minusDays(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus4Day = today.minusDays(4).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus5Day = today.minusDays(5).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String todayMinus6Day = today.minusDays(6).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            for(User user : AdministratorLists.getInstance().getUserList())
            {
                if(todayString.equals(user.getCreation_date()))
                    todayUsers++;
                if(todayMinus1Day.equals(user.getCreation_date()))
                    usersMinus1Day++;
                if(todayMinus2Day.equals(user.getCreation_date()))
                    usersMinus2Day++;
                if(todayMinus3Day.equals(user.getCreation_date()))
                    usersMinus3Day++;
                if(todayMinus4Day.equals(user.getCreation_date()))
                    usersMinus4Day++;
                if(todayMinus5Day.equals(user.getCreation_date()))
                    usersMinus5Day++;
                if(todayMinus6Day.equals(user.getCreation_date()))
                    usersMinus6Day++;
            }
            usersSeries.getData().add(new XYChart.Data(todayMinus6Day, usersMinus6Day));
            usersSeries.getData().add(new XYChart.Data(todayMinus5Day, usersMinus5Day));
            usersSeries.getData().add(new XYChart.Data(todayMinus4Day, usersMinus4Day));
            usersSeries.getData().add(new XYChart.Data(todayMinus3Day, usersMinus3Day));
            usersSeries.getData().add(new XYChart.Data(todayMinus2Day, usersMinus2Day));
            usersSeries.getData().add(new XYChart.Data(todayMinus1Day, usersMinus1Day));
            usersSeries.getData().add(new XYChart.Data(todayString, todayUsers));
    }

    /**
     * Loads three types of users existing in the system:
     *                          Users: 0 Reports,
     *                          Users: More than 5 reports
     *                          Users: More than 10 reports
     * Sets the loaded data as a pie chart data
     */
    public void loadPieChartValues()
    {
            zeroReports = 0;
            double moreThan5Reports = 0;
            double moreThan10Reports = 0;

            for(User user : AdministratorLists.getInstance().getUserList())
            {
                if(user.getReportsNumber() == 0)
                    zeroReports++;
                if(user.getReportsNumber() > 5)
                    moreThan5Reports++;
                if(user.getReportsNumber() > 10)
                    moreThan10Reports++;
            }

            pieData1.pieValueProperty().setValue(zeroReports);
            pieData2.pieValueProperty().setValue(moreThan5Reports);
            pieData3.pieValueProperty().setValue(moreThan10Reports);
    }

    /**
     * Sets default version of the view
     */
    public void defaultView() {
        pieChartLegendProperty.set(Side.RIGHT);
        pieChartLegendVisibleProperty.set(true);
        xAxisRangingProperty.set(true);
        yAxisRangingProperty.set(true);
        userNumberProperty.set(AdministratorLists.getInstance().getUserList().size() + "");
        advertisementsNumberProperty.setValue(String.valueOf(advertisementModel.getAdvertisementNumber()));

        loadLastWeekCreatedUsers();
        loadPieChartValues();
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the area chart data
     */
    public XYChart.Series getUsersSeries() {
        return usersSeries;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the number of the system users
     */
    public StringProperty getUserNumberProperty() {
        return userNumberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the position of the pie chart legend
     */
    public SimpleObjectProperty<Side> getPieChartLegendProperty() {
        return pieChartLegendProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the pie char legend
     */
    public SimpleBooleanProperty getPieChartLegendVisibleProperty() {
        return pieChartLegendVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the ranging value of the X axis for the area chart
     */
    public SimpleBooleanProperty getXAxisRanging() {
        return xAxisRangingProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the ranging value of the Y axis for the area chart
     */
    public SimpleBooleanProperty getYAxisRanging() {
        return yAxisRangingProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the pie chart data
     */
    public SimpleObjectProperty<ObservableList<PieChart.Data>> getPieChartDate() {
        return pieChartDataProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the ad number property
     */
    public StringProperty getAdvertisementsNumberProperty() {
        return advertisementsNumberProperty;
    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void updateNumber(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(()-> advertisementsNumberProperty.setValue(String.valueOf(advertisementModel.getAdvertisementNumber())));

    }

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void reloadDashboard(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(this::defaultView);
    }
}
