package ch.olmero.tender.repository;

import ch.olmero.tender.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    Optional<Offer> findOfferByIdAndTenderId(Integer id, Integer tenderId);

    @Modifying
    @Query("UPDATE Offer o SET o.accepted = false WHERE o.tender.id = :tenderId AND o.accepted IS NULL")
    void rejectNonAcceptedOffers(Integer tenderId);
}
