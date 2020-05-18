package ESharing.Shared.TransferedObject;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Advertisement {
    private int advertisementID;
    private User owner;
    private List<byte[]> photos;
    private String type;
    private List<LocalDate> unavailability;
    private double price;
    private String title;
    private String description;
    private LocalDate creationDate;
    private boolean approved;

    public Advertisement(User owner, List<byte[]> photos, String type, List<LocalDate> unavailability, double price, String title, String description) {
        this.owner = owner;
        this.photos = photos;
        this.type = type;
        this.unavailability = unavailability;
        this.price = price;
        this.title = title;
        this.description = description;
        this.approved = false;
    }

    public int getAdvertisementID() {
        return advertisementID;
    }

    public User getOwner() {
        return owner;
    }

    public List<Image> getPhotos() {
        List<Image> images = new ArrayList<>();
        for(int i = 0 ; i < photos.size() ; i++)
            images.add(new Image(new ByteArrayInputStream(photos.get(i))));
        return images;
    }

    public String getType() {
        return type;
    }

    public List<LocalDate> getUnavailability() {
        return unavailability;
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
}
