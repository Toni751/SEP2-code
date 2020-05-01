package ESharing.Client.Views.AdminDashboardView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AdminDashboardViewModel {

    private XYChart.Series usersSeries;
    private StringProperty userNumberProperty;
    private DoubleProperty data1Property;
    private DoubleProperty data2Property;
    private DoubleProperty data3Property;
    private AdministratorActionsModel model;
    private int todayUsers;
    private String todayString;
    private double zeroReports;

    public AdminDashboardViewModel()
    {
        model = ModelFactory.getModelFactory().getAdministratorActionsModel();
        userNumberProperty = new SimpleStringProperty();
        data1Property = new SimpleDoubleProperty();
        data2Property = new SimpleDoubleProperty();
        data3Property = new SimpleDoubleProperty();
        usersSeries = new XYChart.Series();
        model.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this::newUserCreated);
        model.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::removedUser);
    }

    public void loadLastWeekCreatedUsers()
    {
        userNumberProperty.set(AdministratorLists.getInstance().getUserList().size() + "");

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

            System.out.println(todayMinus1Day);

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
            data1Property.setValue(zeroReports);
            data2Property.setValue(moreThan5Reports);
            data3Property.setValue(moreThan10Reports);
            System.out.println("pie chart");
    }


    public XYChart.Series getUsersSeries() {
        return usersSeries;
    }

    public StringProperty getUserNumberProperty() {
        return userNumberProperty;
    }

    public DoubleProperty getData1Property() {
        return data1Property;
    }

    public DoubleProperty getData2Property() {
        return data2Property;
    }

    public DoubleProperty getData3Property() {
        return data3Property;
    }

    private void newUserCreated(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            userNumberProperty.set(AdministratorLists.getInstance().getUserList().size() + "");
            usersSeries.getData().remove(6);
            usersSeries.getData().add(new XYChart.Data(todayString, todayUsers++));
            data1Property.setValue(zeroReports++);
            System.out.println("Refreshing line and pie chart");
        });
    }

    private void removedUser(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            userNumberProperty.set(AdministratorLists.getInstance().getUserList().size() + "");
        });
    }
}
