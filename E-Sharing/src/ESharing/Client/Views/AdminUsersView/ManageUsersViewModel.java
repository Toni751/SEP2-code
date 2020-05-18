package ESharing.Client.Views.AdminUsersView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;

/**
 * The class in a view model layer contains all functions which are used in the manage users view.
 * @version 1.0
 * @author Group1
 */
public class ManageUsersViewModel{
    private StringProperty warningLabelProperty;
    private StringProperty totalUsersProperty;
    private StringProperty reportedUsersProperty;
    private StringProperty searchProperty;


    private AdministratorActionsModel administratorActionsModel;
    private UserActionsModel userActionsModel;
    private ObservableList<User> users;
    private User selectedUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public ManageUsersViewModel()
    {
        users = FXCollections.observableArrayList();
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        warningLabelProperty = new SimpleStringProperty();
        totalUsersProperty = new SimpleStringProperty();
        reportedUsersProperty = new SimpleStringProperty();
        searchProperty = new SimpleStringProperty();

        administratorActionsModel.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this::updateUserTableView);
        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::updateUserTableView);
        administratorActionsModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), this::updateUserTableView);
    }


    /**
     * Loads all users exisitng in the system to the JavaFx collection
     * @return the JavaFx collection with all users existing in the system
     */
    public ObservableList<User> loadAllUsers()
    {
        Platform.runLater(() ->{
            users.clear();
            users.setAll(AdministratorLists.getInstance().getUserList());
           for(int i = 0 ; i < users.size() ; i++)
           {
               System.out.println(users.get(i).getUsername());
           }
            totalUsersProperty.setValue(AdministratorLists.getInstance().getUserList().size() +"");
            reportedUsersProperty.setValue(AdministratorLists.getInstance().reportedUsers() + "");
        });
        return users;
    }

    /**
     * Sends a request to remove selected user from the system
     * @return the result of the action as a boolean
     */
    public boolean removeSelectedUser()
    {
        if(administratorActionsModel.removeUserAccount(AdministratorLists.getInstance().getSelectedUser())) {
            warningLabelProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            selectedUser = null;
            return true;
        }
        else {
            warningLabelProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            return false;
        }
    }


    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning label
     */
    public StringProperty getWarningLabelProperty() {
        return warningLabelProperty;
    }

    public StringProperty getReportedUsersProperty() {
        return reportedUsersProperty;
    }

    public StringProperty getTotalUsersProperty() {
        return totalUsersProperty;
    }

    private void updateUserTableView(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            loadAllUsers();
            System.out.println("Admin knows that it is necessary to update table");
        });
    }

    public StringProperty getSearchProperty() {
        return searchProperty;
    }

    public void searchInTable() {
        Platform.runLater(() -> {
            users.clear();
            for(User user : AdministratorLists.getInstance().getUserList())
            {
                if(user.getUsername().contains(searchProperty.get()))
                    users.add(user);
            }
        });
    }
}
