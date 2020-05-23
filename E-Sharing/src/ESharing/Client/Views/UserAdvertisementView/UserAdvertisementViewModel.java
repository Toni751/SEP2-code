package ESharing.Client.Views.UserAdvertisementView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ReservationModel.ReservationModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.List;

public class UserAdvertisementViewModel implements PropertyChangeSubject {


    private ObservableList<CatalogueAd> ownCatalogues;
    private ObservableList<User> reservationUsers;
    private PropertyChangeSupport support;

    private AdvertisementModel advertisementModel;
    private UserActionsModel userActionsModel;
    private ReservationModel reservationModel;

    public UserAdvertisementViewModel(){
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        reservationModel = ModelFactory.getModelFactory().getReservationModel();

        ownCatalogues = FXCollections.observableArrayList();
        reservationUsers = FXCollections.observableArrayList();
        support = new PropertyChangeSupport(this);

        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
        reservationModel.addPropertyChangeListener(Events.NEW_RESERVATION_CREATED.toString(), this::newReservationCreated);
        reservationModel.addPropertyChangeListener(Events.RESERVATION_REMOVED.toString(), this::reservationRemoved);
    }

    private void reservationRemoved(PropertyChangeEvent propertyChangeEvent) {
        int[] idArray = (int[]) propertyChangeEvent.getOldValue();
        List<LocalDate> daysToRemove = (List<LocalDate>) propertyChangeEvent.getNewValue();

        int advertisementID = idArray[0];
        int requestedID = idArray[1];

        for(int i = 0 ; i < ownCatalogues.size() ; i++) {
            if (ownCatalogues.get(i).getAdvertisementID() == advertisementID) {
                CatalogueAd catalogueAdReplace = ownCatalogues.get(i);
                for(int j = 0 ; j < ownCatalogues.get(i).getReservations().size() ; j++)
                {
                    if(ownCatalogues.get(i).getReservations().get(j).getRequestedUser().getUser_id() == requestedID){
                        CatalogueAd updated = ownCatalogues.get(i);
                        updated.getReservations().get(j).getReservationDays().removeAll(daysToRemove);
                        ownCatalogues.set(i, updated);
                    }
                }
                ownCatalogues.set(i, catalogueAdReplace);
                System.out.println("New reservations event");
            }
        }
    }

    private void newReservationCreated(PropertyChangeEvent propertyChangeEvent) {
        Reservation reservation = (Reservation) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < ownCatalogues.size() ; i++){
            if(ownCatalogues.get(i).getAdvertisementID() == reservation.getAdvertisementID()){
                CatalogueAd catalogueAdReplace = ownCatalogues.get(i);
                catalogueAdReplace.getReservations().add(reservation);
                ownCatalogues.set(i, catalogueAdReplace);
            }
        }
    }

    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int removedId = (int) propertyChangeEvent.getNewValue();

        for(int i = 0 ; i < ownCatalogues.size() ; i++)
        {
            if(ownCatalogues.get(i).getAdvertisementID() == removedId) {
                ownCatalogues.remove(ownCatalogues.get(i));
                support.firePropertyChange(Events.AD_REMOVED.toString(), null, ownCatalogues);
            }
        }
    }

    public void defaultView()
    {
        ownCatalogues.clear();
        ownCatalogues.addAll(advertisementModel.getAllCataloguesForUser(LoggedUser.getLoggedUser().getUser().getUser_id()));

    }

    public ObservableList<CatalogueAd> getOwnCatalogues() {
        return ownCatalogues;
    }

    public boolean selectAdvertisement(int id)
    {
        Advertisement advertisement = advertisementModel.getAdvertisement(id);
        if(advertisement  != null){
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            return true;
        }
        return false;
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if ("".equals(eventName) || eventName == null)
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
