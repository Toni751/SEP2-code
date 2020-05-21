package ESharing.Client.Views.AdminAdvertisementsView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.Vehicles;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminAdvertisementsViewModel {

    private StringProperty warningStyleProperty;
    private StringProperty totalProperty;
    private StringProperty notApprovedProperty;
    private StringProperty warningProperty;
    private StringProperty comboValueProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty openAdDisableProperty;
    private BooleanProperty removeAdDisableProperty;
    private BooleanProperty approveAdDisableProperty;

    private ObservableList<String> comboItems;
    private ObservableList<Advertisement> advertisementList;

    private AdvertisementModel advertisementModel;


    public AdminAdvertisementsViewModel()
    {
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        warningProperty = new SimpleStringProperty();
        notApprovedProperty = new SimpleStringProperty();
        totalProperty = new SimpleStringProperty();
        comboValueProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        openAdDisableProperty = new SimpleBooleanProperty();
        removeAdDisableProperty = new SimpleBooleanProperty();
        approveAdDisableProperty = new SimpleBooleanProperty();

        comboItems = FXCollections.observableArrayList();
        advertisementList = FXCollections.observableArrayList();
    }

    public void loadDefaultView() {
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        advertisementList.clear();
        if(advertisementModel.selectAllAdvertisements() != null)
            advertisementList.setAll(advertisementModel.selectAllAdvertisements());

        removeAdDisableProperty.setValue(true);
        openAdDisableProperty.setValue(true);
        approveAdDisableProperty.set(true);

        totalProperty.set(String.valueOf(advertisementList.size()));

        int notApproved = 0;
        for(Advertisement advertisement : advertisementList){
            if(!advertisement.ifAdApproved())
                notApproved++;
        }
        notApprovedProperty.set(String.valueOf(notApproved));

        comboItems.clear();
        comboValueProperty.set("All");
        comboItems.add("Not Approved");
        comboItems.add("Approved");
        for(int i = 0; i < Vehicles.values().length ; i++) {
            comboItems.add(Vehicles.values()[i].toString());
        }
    }

    public void enableUserManagingProperty() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().ifAdApproved())
            approveAdDisableProperty.set(true);
        else
            approveAdDisableProperty.set(false);
        openAdDisableProperty.setValue(false);

        removeAdDisableProperty.setValue(false);
    }

    public void disableUserManagingProperty() {
        removeAdDisableProperty.setValue(true);
        openAdDisableProperty.setValue(true);
        approveAdDisableProperty.set(true);
    }

    public void searchAdvertisements() {
        advertisementList.clear();
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        disableUserManagingProperty();

        if(comboValueProperty.get().equals("Not Approved")){
            for(Advertisement advertisement : advertisementModel.selectAllAdvertisements()) {
                if(!advertisement.ifAdApproved())
                    advertisementList.add(advertisement);
            }
        }
        else if(comboValueProperty.get().equals("Approved"))
            for(Advertisement advertisement : advertisementModel.selectAllAdvertisements()) {
                if(advertisement.ifAdApproved())
                    advertisementList.add(advertisement);
                System.out.println(advertisement.ifAdApproved());
            }
        else{
            for(Advertisement advertisement : advertisementModel.selectAllAdvertisements()) {
                for (int i = 0; i < Vehicles.values().length; i++) {
                    if (comboValueProperty.get().equals(Vehicles.values()[i].toString()))
                        advertisementList.add(advertisement);
                }
            }
        }

    }

    public void approveAdvertisement() {
        if(advertisementModel.approveAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement())){
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.set(true);


    }

    public void removeSelectedAdvertisement() {
        if(advertisementModel.removeAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement())){
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.set(true);
    }

    public BooleanProperty getOpenAdDisableProperty() {
        return openAdDisableProperty;
    }

    public BooleanProperty getRemoveDisableProperty() {
        return removeAdDisableProperty;
    }

    public BooleanProperty getApproveDisableProperty() {
        return approveAdDisableProperty;
    }

    public ObservableList<String> getComboItems() {
        return comboItems;
    }

    public StringProperty getNotApprovedProperty() {
        return notApprovedProperty;
    }

    public StringProperty getTotalProperty() {
        return totalProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    public ObservableList<Advertisement> getAllAdvertisement() {
        return  advertisementList;
    }

    public StringProperty getComboValueProperty() {
        return comboValueProperty;
    }
}
