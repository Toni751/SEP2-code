package ESharing.Client.Model.UserAccount;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

public interface UserSettingModel extends PropertyChangeSubject {
    void modifyAddress();
    void modifyGeneralInformation();
    void removeAccount(User loggedUser);
}
