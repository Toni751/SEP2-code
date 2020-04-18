package ESharing.Client.Model.UserAccount;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

public interface UserSettingModel extends PropertyChangeSubject {
    void modifyAddress(User updateUser);
    boolean modifyGeneralInformation(User updatedUser);
    void removeAccount(User loggedUser);
}
