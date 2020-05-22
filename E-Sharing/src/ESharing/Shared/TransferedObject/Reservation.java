package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Reservation implements Serializable {
    private int advertisementID;
    private int userID;
    private double totalPrice;
    private List<LocalDate> reservationDays;

    public Reservation(int advertisementID, int userID,List<LocalDate> reservationDays) {
        this.advertisementID = advertisementID;
        this.userID = userID;
        this.reservationDays = reservationDays;
    }

    public int getAdvertisementID() {
        return advertisementID;
    }

    public int getUserID() {
        return userID;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<LocalDate> getReservationDays() {
        return reservationDays;
    }
}
