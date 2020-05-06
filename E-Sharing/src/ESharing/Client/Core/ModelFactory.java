package ESharing.Client.Core;

import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.AppModel.AppOverviewModelManager;
import ESharing.Client.Model.AdministratorModel.AdministratorActionModelManager;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.UserActions.UserActionsModelManager;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Client.Model.VerificationModel.VerificationModelManager;

/**
 * The class responsible for managing models
 * @version 1.0
 * @author Group1
 */
public class ModelFactory {
    private UserActionsModel userActionsModel;
    private AppOverviewModel appOverviewModel;
    private AdministratorActionsModel administratorActionsModel;
    private VerificationModel verificationModel;

    private static ModelFactory modelFactory;

    /**
     * A constructor initializes fields and sets class responsible for initialize client
     */
    private ModelFactory()
    {
        userActionsModel = new UserActionsModelManager();
        appOverviewModel = new AppOverviewModelManager();
        administratorActionsModel = new AdministratorActionModelManager();
        verificationModel = new VerificationModelManager();
    }

    /**
     * Returns ModelFactory object if it exists, otherwise creates new object
     * @return the ModelFactory object
     */
    public static ModelFactory getModelFactory() {
        if(modelFactory == null)
            modelFactory = new ModelFactory();
        return modelFactory;
    }

    /**
     * Returns initialized model for all features belongs to user service
     * @return initialized model for all features belongs to user service
     */
    public UserActionsModel getUserActionsModel() {
        return userActionsModel;
    }

    /**
     * Returns initialized model for all features belongs to system overview
     * @return initialized model for all features belongs to system overview
     */
    public AppOverviewModel getAppOverviewModel() {
        return appOverviewModel;
    }

    /**
     * Returns initialized model for all features belongs to administrator
     * @return initialized model for all features belongs to administrator
     */
    public AdministratorActionsModel getAdministratorActionsModel() {
        return administratorActionsModel;
    }

    /**
     * Returns initialized model for all verification processes
     * @return initialized model for all verification processes
     */
    public VerificationModel getVerificationModel() {
        return verificationModel;
    }
}
