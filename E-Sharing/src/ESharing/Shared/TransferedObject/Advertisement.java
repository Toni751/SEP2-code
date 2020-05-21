package ESharing.Shared.TransferedObject;

import ESharing.Shared.Util.AdImages;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advertisement implements Serializable {
    private int advertisementID;
    private User owner;
    private Map<String, byte[]> photos;
    private Map<String, String> serverPaths;
    private String type;
    private List<LocalDate> unavailability;
    private double price;
    private String title;
    private String description;
    private LocalDate creationDate;
    private boolean approved;
    private int reportsNumber;

    public Advertisement(User owner, Map<String, byte[]> photos, String type, List<LocalDate> unavailability, double price, String title, String description) {
        this.owner = owner;
        this.photos = photos;
        this.type = type;
        this.unavailability = unavailability;
        this.price = price;
        this.title = title;
        this.description = description;
        this.approved = false;

        serverPaths = new HashMap<>();

    }

    public int getAdvertisementID() {
        return advertisementID;
    }

    public User getOwner() {
        return owner;
    }

    public Map<String, Image> getPhotos() {
        Map<String, Image> images = new HashMap<>();
        if(photos.containsKey(AdImages.MAIN_IMAGE.toString()))
            images.put(AdImages.MAIN_IMAGE.toString(), new Image(new ByteArrayInputStream(photos.get(AdImages.MAIN_IMAGE.toString()))));
        if(photos.containsKey(AdImages.SUB_IMAGE1.toString()))
            images.put(AdImages.SUB_IMAGE1.toString(), new Image(new ByteArrayInputStream(photos.get(AdImages.SUB_IMAGE1.toString()))));
        if(photos.containsKey(AdImages.SUB_IMAGE2.toString()))
            images.put(AdImages.SUB_IMAGE2.toString(), new Image(new ByteArrayInputStream(photos.get(AdImages.SUB_IMAGE2.toString()))));
        if(photos.containsKey(AdImages.SUB_IMAGE3.toString()))
            images.put(AdImages.SUB_IMAGE3.toString(), new Image(new ByteArrayInputStream(photos.get(AdImages.SUB_IMAGE3.toString()))));
        if(photos.containsKey(AdImages.SUB_IMAGE4.toString()))
            images.put(AdImages.SUB_IMAGE4.toString(), new Image(new ByteArrayInputStream(photos.get(AdImages.SUB_IMAGE4.toString()))));
        return images;
    }

    public Image getPhoto(String id)
    {
        return new Image(new ByteArrayInputStream(photos.get(id)));
    }

    public void setServerPath(Map<String, String> paths)
    {
        serverPaths.put(AdImages.MAIN_IMAGE.toString(), paths.get(AdImages.MAIN_IMAGE.toString()));
        if(paths.containsKey(AdImages.SUB_IMAGE1.toString()))
            serverPaths.put(AdImages.SUB_IMAGE1.toString(), paths.get(AdImages.SUB_IMAGE1.toString()));
        if(paths.containsKey(AdImages.SUB_IMAGE2.toString()))
            serverPaths.put(AdImages.SUB_IMAGE2.toString(), paths.get(AdImages.SUB_IMAGE2.toString()));
        if(paths.containsKey(AdImages.SUB_IMAGE3.toString()))
            serverPaths.put(AdImages.SUB_IMAGE3.toString(), paths.get(AdImages.SUB_IMAGE3.toString()));
        if(paths.containsKey(AdImages.SUB_IMAGE4.toString()))
            serverPaths.put(AdImages.SUB_IMAGE4.toString(), paths.get(AdImages.SUB_IMAGE4.toString()));
    }

    public Map<String, String> getServerPaths()
    {
        return serverPaths;
    }

    public String getServerPath(String id)
    {
        return serverPaths.get(id);
    }

    public String getType() {
        return type;
    }

    public void addNewReport()
    {
        reportsNumber++;
    }

    public List<LocalDate> getUnavailability() {
        return unavailability;
    }

    public Map<String, byte[]> getBytePhotos()
    {
        return photos;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setAdvertisementID(int advertisementID) {
        this.advertisementID = advertisementID;
    }


    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void addUnavailableDate(LocalDate date)
    {
        unavailability.add(date);
    }

    public void setAdApproved()
    {
        approved = true;
    }

    public boolean ifAdApproved()
    {
        return approved;
    }

    public void setPhotos(Map<String, byte[]> photos) {
        this.photos = photos;
    }

    public int getReportsNumber() {
        return reportsNumber;
    }

    public void setReports(int reports) {
        this.reportsNumber = reports;
    }
}
