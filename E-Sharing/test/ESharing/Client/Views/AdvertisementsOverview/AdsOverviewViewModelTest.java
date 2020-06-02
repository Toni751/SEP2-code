package ESharing.Client.Views.AdvertisementsOverview;

import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Vehicles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//test the searchAdvertisement method from the AdsOverviewViewModel class to see if
//the method filters advertisements as intended, by their vehicle type attribute
class AdsOverviewViewModelTest
{
  AdsOverviewViewModel viewModel;

  @BeforeEach
  public void setup()
  {
    viewModel = new AdsOverviewViewModel();
  }

  //checks if the search method fires an event with all the advertisements as
  //the new value, when the search property is set to the default, "All" value
  @Test
  public void searchFunctionFiresEventWithAllVehiclesWhenSearchingForAll()
  {
    //arrange
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchValueProperty().bindBidirectional(searchProperty);
    searchProperty.setValue(Vehicles.All.toString());
    viewModel.getCatalogueAds(); //initializes the catalogue ads with the dummy adds

    // act & assert
    PropertyChangeListener listener = evt -> {
      List<CatalogueAd> catalogueAds = (List<CatalogueAd>) evt.getNewValue();
      assertSame(catalogueAds, viewModel.getCatalogueAds());

    };
    viewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), listener);
    viewModel.searchAdvertisements();
  }

  //checks if the search method fires an event with all the advertisements having
  //the vehicle type equal to "car" as the new value (and only these advertisements),
  //when the search property is set to the "Car" value
  @Test
  public void searchFunctionFiresEventWithAllCarsWhenSearchingForCars()
  {
    //arrange
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchValueProperty().bindBidirectional(searchProperty);
    searchProperty.setValue(Vehicles.Car.toString());
    viewModel.getCatalogueAds(); //initializes the catalogue ads with the dummy adds

    //act & assert
    PropertyChangeListener listener = evt -> {
      List<CatalogueAd> catalogueAds = (List<CatalogueAd>) evt.getNewValue();
      boolean atLeastOneElementIsNotACar = false;
      for (CatalogueAd catalogueAd : catalogueAds)
      {
        if(!(catalogueAd.getVehicleType().equals(Vehicles.Car.toString())))
          atLeastOneElementIsNotACar = true;
      }
      assertFalse(atLeastOneElementIsNotACar);

    };
    viewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), listener);
    viewModel.searchAdvertisements();
  }

  //checks if the search method fires an event with all the advertisements having
  //the vehicle type equal to "scooter" as the new value (and only these advertisements),
  //when the search property is set to the "Scooter" value
  @Test
  public void searchFunctionFiresEventWithAllScootersWhenSearchingForScooters()
  {
    //arrange
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchValueProperty().bindBidirectional(searchProperty);
    searchProperty.setValue(Vehicles.Scooter.toString());
    viewModel.getCatalogueAds(); //initializes the catalogue ads with the dummy adds

    //act & assert
    PropertyChangeListener listener = evt -> {
      List<CatalogueAd> catalogueAds = (List<CatalogueAd>) evt.getNewValue();
      boolean atLeastOneElementIsNotAScooter = false;
      for (CatalogueAd catalogueAd : catalogueAds)
      {
        if(!(catalogueAd.getVehicleType().equals(Vehicles.Scooter.toString())))
          atLeastOneElementIsNotAScooter = true;
      }
      assertFalse(atLeastOneElementIsNotAScooter);

    };
    viewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), listener);
    viewModel.searchAdvertisements();
  }

  //checks if the search method fires an event with all the advertisements having
  //the vehicle type equal to "bike" as the new value (and only these advertisements),
  //when the search property is set to the "Bike" value
  @Test
  public void searchFunctionFiresEventWithAllBikesWhenSearchingForBikes()
  {
    //arrange
    StringProperty searchProperty = new SimpleStringProperty();
    viewModel.getSearchValueProperty().bindBidirectional(searchProperty);
    searchProperty.setValue(Vehicles.Bike.toString());
    viewModel.getCatalogueAds(); //initializes the catalogue ads with the dummy adds

    //act & assert
    PropertyChangeListener listener = evt -> {
      List<CatalogueAd> catalogueAds = (List<CatalogueAd>) evt.getNewValue();
      boolean atLeastOneElementIsNotABike = false;
      for (CatalogueAd catalogueAd : catalogueAds)
      {
        if(!(catalogueAd.getVehicleType().equals(Vehicles.Bike.toString())))
          atLeastOneElementIsNotABike = true;
      }
      assertFalse(atLeastOneElementIsNotABike);

    };
    viewModel.addPropertyChangeListener(Events.UPDATE_AD_LIST.toString(), listener);
    viewModel.searchAdvertisements();
  }
}