package ESharing.Shared.TransferedObject;

import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A class for representing a reservation object
 * @version 1.0
 * @author Group1
 */
public class Reservation implements Serializable {
    private int advertisementID;
    private String advertisementTitle;
    private User requestedUser;
    private String ownerUsername;
    private double price;
    private List<LocalDate> reservationDays;

    public Reservation(int advertisementID, String advertisementTitle, String ownerUsername, User requestedUser, double price, List<LocalDate> reservationDays) {
        this.advertisementID = advertisementID;
        this.requestedUser = requestedUser;
        this.reservationDays = reservationDays;
        this.advertisementTitle = advertisementTitle;
        this.ownerUsername = ownerUsername;
        this.price = price;
    }

    public int getAdvertisementID() {
        return advertisementID;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return price * reservationDays.size();
    }

    public String getAdvertisementTitle() {
        return advertisementTitle;
    }

    public List<LocalDate> getReservationDays() {
        return reservationDays;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public DatePicker getReservationsComponent(){
        DatePicker datePicker = new DatePicker();
        Callback<DatePicker, DateCell> dayCellFactory = (DatePicker) -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(true);
                if(reservationDays.contains(item)){
                    setStyle("-fx-background-color: #4CDBC4;");
                }
            }
        };
        datePicker.setMaxWidth(10);
        datePicker.setDayCellFactory(dayCellFactory);
        return datePicker;
    }
}
