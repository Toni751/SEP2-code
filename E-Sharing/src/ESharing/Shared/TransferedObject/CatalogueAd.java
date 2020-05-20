package ESharing.Shared.TransferedObject;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

public class CatalogueAd implements Serializable
{
  private String title;
  private double price;
  private byte[] mainImage;
  private int advertisementID;

  public CatalogueAd(String title, double price, byte[] mainImage, int advertisementID)
  {
    this.title = title;
    this.price = price;
    this.mainImage = mainImage;
    this.advertisementID = advertisementID;
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

  public Image getMainImage()
  {
    Image main = new Image(new ByteArrayInputStream(this.mainImage));
    return main;
  }
}
