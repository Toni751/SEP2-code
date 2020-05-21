package ESharing.Client.Model.ReservationModel;

import java.time.LocalDate;
import java.util.List;

public interface ReservationModel {

    boolean sendReservationRequest(int advertisementID, List<LocalDate> selectedDates);
}
