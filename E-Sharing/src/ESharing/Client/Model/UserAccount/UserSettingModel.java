package ESharing.Client.Model.UserAccount;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

public interface UserSettingModel extends PropertyChangeSubject {
    boolean modifyUserInformation(User updatedUser);
    void removeAccount();
}
