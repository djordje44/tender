package ch.olmero.tender.repository;

import ch.olmero.tender.entity.Issuer;
import ch.olmero.tender.entity.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Integer> {
}
