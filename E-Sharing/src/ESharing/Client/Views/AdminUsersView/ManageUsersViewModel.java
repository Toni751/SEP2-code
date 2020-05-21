package ESharing.Client.Views.AdminUsersView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.beans.PropertyChangeEvent;

/**
 * The class in a view model layer contains all functions which are used in the administrator users list view.
 * @version 1.0
 * @author Group1
 */
public class ManageUsersViewModel{
    private StringProperty warningLabelProperty;
    private StringProperty totalUsersProperty;
    private StringProperty reportedUsersProperty;
    private StringProperty searchProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty editUserDisableProperty;
    private BooleanProperty removeUserDisableProperty;
    private BooleanProperty messageUserDisableProperty;
    private BooleanProperty warningVisibleProperty;

    private AdministratorActionsModel administratorActionsModel;
    private UserActionsModel userActionsModel;
    private ObservableList<User> users;
    private User selectedUser;

    /**
     * A constructor initializes model layer for a administrator features and all fields
     */
    public ManageUsersViewModel()
    {
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();

        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        warningLabelProperty = new SimpleStringProperty();
        totalUsersProperty = new SimpleStringProperty();
        reportedUsersProperty = new SimpleStringProperty();
        searchProperty = new SimpleStringProperty();
        removeUserDisableProperty = new SimpleBooleanProperty();
        editUserDisableProperty = new SimpleBooleanProperty();
        messageUserDisableProperty = new SimpleBooleanProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        warningStyleProperty = new SimpleStringProperty();

        users = FXCollections.observableArrayList();

        administratorActionsModel.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this::newUserCreated);
        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::userRemoved);
        administratorActionsModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), this::updatedUser);
        userActionsModel.addPropertyChangeListener(Events.NEW_USER_REPORT.toString(), this::newUserReported);
    }

    private void newUserReported(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("USER reported");
        int userID = (int) propertyChangeEvent.getOldValue();
        int reports = (int) propertyChangeEvent.getNewValue();
        System.out.println(userID);
        System.out.println(reports);


        for(int i = 0 ; i < users.size() ; i++)
        {
            if(users.get(i).getUser_id() == userID) {
                User updated = users.get(i);
                updated.setReportsNumber(reports);
                users.set(i, updated);
            }
        }
    }

    private void updatedUser(PropertyChangeEvent propertyChangeEvent) {
        User updatedUser = (User) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < users.size() ; i++){
            if(users.get(i).getUser_id() == updatedUser.getUser_id()){
                users.set(i, updatedUser);
            }
        }
    }

    private void userRemoved(PropertyChangeEvent propertyChangeEvent) {
        User removedUser = (User) propertyChangeEvent.getNewValue();
        users.remove(removedUser);
        disableUserManagingProperty();
    }

    private void newUserCreated(PropertyChangeEvent propertyChangeEvent) {
        User newUser = (User) propertyChangeEvent.getNewValue();
        users.add(newUser);
        disableUserManagingProperty();
    }

    /**
     * Loads all users which are a part of the system to the users table
     * @return the collection of the all users which are part of the system
     */
    public ObservableList<User> loadAllUsers()
    {
        Platform.runLater(() ->{
            users.setAll(administratorActionsModel.getAllUsers());
            totalUsersProperty.setValue(String.valueOf(AdministratorLists.getInstance().getUserList().size()));
            reportedUsersProperty.setValue(String.valueOf(AdministratorLists.getInstance().reportedUsers()));
        });
        return users;
    }

    /**
     * Sends a request to the model layer for removing selected user
     */
    public void removeSelectedUser()
    {
        if(administratorActionsModel.removeUserAccount(AdministratorLists.getInstance().getSelectedUser())) {
            warningLabelProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            selectedUser = null;
        }
        else {
            warningLabelProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
        disableUserManagingProperty();
    }

    /**
     * Sets a disable property for the manage user components as false
     */
    public void disableUserManagingProperty()
    {
        editUserDisableProperty.setValue(true);
        removeUserDisableProperty.setValue(true);
        messageUserDisableProperty.setValue(true);
    }

    /**
     * Sets a disable property for the manage user components as true
     */
    public void enableUserManagingProperty()
    {
        editUserDisableProperty.setValue(false);
        removeUserDisableProperty.setValue(false);
        messageUserDisableProperty.setValue(false);
    }

    /**
     * Sets a default view and values
     */
    public void loadDefaultView()
    {
        users.clear();
        warningVisibleProperty.setValue(false);
        disableUserManagingProperty();
        loadAllUsers();
    }

    /**
     * Select user form the table regarding values inserted by a user in the search text field
     */
    public void searchInTable() {
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        Platform.runLater(() ->{
            for (User user : users) {
                if (user.getUsername().contains(searchProperty.get()))
                    filteredList.add(user);
            }
            users.setAll(filteredList);
            if(searchProperty.get().equals(""))
                loadAllUsers();
        });

    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a warning label
     */
    public StringProperty getWarningLabelProperty() {
        return warningLabelProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a reported user label
     */
    public StringProperty getReportedUsersProperty() {
        return reportedUsersProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a total user label
     */
    public StringProperty getTotalUsersProperty() {
        return totalUsersProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a search text field
     */
    public StringProperty getSearchProperty() {
        return searchProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable property of a remove user button
     */
    public BooleanProperty getRemoveUserDisableProperty() {
        return removeUserDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable property of a edit user button
     */
    public BooleanProperty getEditUserDisableProperty() {
        return editUserDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable property of a message user button
     */
    public BooleanProperty getMessageUserDisableProperty() {
        return messageUserDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a warning pane
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable property of a remove user button
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

}
