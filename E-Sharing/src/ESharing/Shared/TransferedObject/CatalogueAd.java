package ESharing.Shared.TransferedObject;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

/**
 * A class for advertisement objects stored as catalogue items for the user
 * @version 1.0
 * @author Group1
 */
public class CatalogueAd implements Serializable
{
  private String title;
  private double price;
  private byte[] mainImage;
  private String mainImageServerPath;
  private String vehicleType;
  private int advertisementID;
  private List<Reservation> reservations;

  public CatalogueAd(int advertisementID, String title, String mainImageServerPath, double price, String vehicleType)
  {
    this.advertisementID = advertisementID;
    this.title = title;
    this.price = price;
    this.mainImageServerPath = mainImageServerPath;
    this.vehicleType = vehicleType;
  }

  public int getAdvertisementID()
  {
    return advertisementID;
  }

  public void setAdvertisementID(int advertisementID)
  {
    this.advertisementID = advertisementID;
  }

  public String getTitle()
  {
    return title;
  }

  public double getPrice()
  {
    return price;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public Image getMainImage()
  {
    Image main = new Image(new ByteArrayInputStream(this.mainImage));
    return main;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public String getMainImageServerPath() {
    return mainImageServerPath;
  }

  public void setMainImage(byte[] mainImage) {
    this.mainImage = mainImage;
  }
}
