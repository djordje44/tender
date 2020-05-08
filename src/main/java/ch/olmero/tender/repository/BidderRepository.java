package ch.olmero.tender.repository;

import ch.olmero.tender.entity.Bidder;
import ch.olmero.tender.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidderRepository extends JpaRepository<Bidder, Integer> {
}
