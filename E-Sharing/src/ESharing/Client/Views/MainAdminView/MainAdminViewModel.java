package ESharing.Client.Views.MainAdminView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;

public class MainAdminViewModel {

    private AdministratorActionsModel administratorActionsModel;

    public MainAdminViewModel() {
        this.administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
    }

    public void loadUsersListRequest() {
        AdministratorLists.getInstance().setUserList(administratorActionsModel.getAllUsers());
    }


}
