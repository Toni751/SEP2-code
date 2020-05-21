package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class AdCatalogueAdmin implements Serializable
{
  private int id;
  private String title;
  private String type;
  private int reports;

  public AdCatalogueAdmin(int id, String title, String type, int reports)
  {
    this.id = id;
    this.title = title;
    this.type = type;
    this.reports = reports;
  }

  public int getAdvertisementID()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }

  public String getType()
  {
    return type;
  }

  public int getReportsNumber()
  {
    return reports;
  }
}
