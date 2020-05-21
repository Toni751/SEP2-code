package ESharing.Client.Model.ReservationModel;

import java.time.LocalDate;
import java.util.List;

public class ReservationModelManager implements ReservationModel{
    @Override
    public boolean sendReservationRequest(int advertisementID, List<LocalDate> selectedDates) {
        return false;
    }
}
