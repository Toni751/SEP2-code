package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class AdCatalogueAdmin implements Serializable
{
  private int id;
  private String title;
  private String type;
  private int reports;
  private boolean approved;

  public AdCatalogueAdmin(int id, String title, String type, int reports, boolean approved)
  {
    this.id = id;
    this.title = title;
    this.type = type;
    this.reports = reports;
    this.approved = approved;
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

  public boolean isApproved() {
    return approved;
  }

  public void makeApproved()
  {
    approved = true;
  }

  public void setReports(int reports) {
    this.reports = reports;
  }
}
