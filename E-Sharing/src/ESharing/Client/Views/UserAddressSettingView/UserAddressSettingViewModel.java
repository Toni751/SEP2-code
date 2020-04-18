package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.UserSettingModel;

public class UserAddressSettingViewModel {

    private UserSettingModel settingModel;

    public UserAddressSettingViewModel()
    {
        settingModel = ModelFactory.getModelFactory().getUserSettingModel();
    }
}
