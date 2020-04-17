package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AppModel.AppOverviewModel;

public class MainAppViewModel{

    private AppOverviewModel model;

    public MainAppViewModel()
    {
        this.model = ModelFactory.getModelFactory().getAppOverviewModel();
    }


}
