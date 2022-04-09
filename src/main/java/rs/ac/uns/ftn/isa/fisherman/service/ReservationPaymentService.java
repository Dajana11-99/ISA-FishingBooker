package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.*;

public interface ReservationPaymentService {
    PaymentInformation setTotalPaymentAmount(Reservation reservation, User user);
    void updateUserRankAfterReservation(Client client, User user);

    void resetLoyaltyStatusAfterCancellation(Client client, User user);
}
