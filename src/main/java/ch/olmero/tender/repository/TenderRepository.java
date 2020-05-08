package ch.olmero.tender.repository;

import ch.olmero.tender.entity.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Integer> {
}
