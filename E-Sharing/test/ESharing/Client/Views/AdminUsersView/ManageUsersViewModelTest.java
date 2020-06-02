package ESharing.Client.Views.AdminUsersView;

import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Shared.TransferedObject.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//tests the searchInTable method from the ManageUsersViewModel class, to see if
//the filtering of the method works as intended
class ManageUsersViewModelTest
{
  ManageUsersViewModel viewModel;

  @BeforeEach
  public void setup()
  {
    viewModel = new ManageUsersViewModel();
  }

  //tests if the number of total users and reported users are retrieved correctly
  //from the administrator list when the list with all users is loaded on the screen
  @Test
  public void totalNumberOfUsersAndReportedUsersHasTheRightValue()
  {
    //arrange
    StringProperty totalUsersProperty = new SimpleStringProperty();
    StringProperty reportedUsers = new SimpleStringProperty();


    viewModel.getTotalUsersProperty().bindBidirectional(totalUsersProperty);
    viewModel.getReportedUsersProperty().bindBidirectional(reportedUsers);

    //act
    viewModel.loadAllUsers();

    //assert
    assertEquals(String.valueOf(AdministratorLists.getInstance().getUserList().size()), totalUsersProperty.getValue());
    assertEquals(String.valueOf(AdministratorLists.getInstance().reportedUsers()), reportedUsers.getValue());
  }

  //checks if when searching with an empty string, all users are displayed
  @Test
  public void filteredUsersWorksForEmptySearchProperty()
  {
    //arrange
    ObservableList<User> filteredList;
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchProperty().bindBidirectional(searchProperty);

    //act
    searchProperty.setValue("");
    viewModel.searchInTable();
    filteredList = viewModel.getUsers();

    //assert
    assertEquals(filteredList, viewModel.loadAllUsers());
  }

  //checks if when searching with letter "A", all users displayed contain letter "A" in the username
  @Test
  public void filterUsersWorksWhenSearchingWithLetterA()
  {
    //arrange
    ObservableList<User> filteredList;
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchProperty().bindBidirectional(searchProperty);

    //act
    searchProperty.setValue("A");
    viewModel.searchInTable();
    filteredList = viewModel.getUsers();
    boolean atLeastOneUsernameDoesntHaveLetterA = false;
    for (User user : filteredList)
    {
      if (!(user.getUsername().contains("A")))
        atLeastOneUsernameDoesntHaveLetterA = true;
    }
    //assert
    assertFalse(atLeastOneUsernameDoesntHaveLetterA);
  }

  //checks when searching with multiple letters, such as "ABC",
  //all the users displayed contain the given characters, "ABC" in their username
  @Test
  public void filterUserWorksWhenSearchingWithMultipleCharacters()
  {
    //arrange
    ObservableList<User> filteredList;
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchProperty().bindBidirectional(searchProperty);

    //act
    searchProperty.setValue("ABC");
    viewModel.searchInTable();
    filteredList = viewModel.getUsers();
    boolean atLeastOneUsernameDoesntHaveTheGivenCharacters = false;
    for (User user : filteredList)
    {
      if (!(user.getUsername().contains("ABC")))
        atLeastOneUsernameDoesntHaveTheGivenCharacters = true;
    }
    //assert
    assertFalse(atLeastOneUsernameDoesntHaveTheGivenCharacters);
  }
}