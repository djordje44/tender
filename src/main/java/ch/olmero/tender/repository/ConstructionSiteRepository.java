package ch.olmero.tender.repository;

import ch.olmero.tender.entity.ConstructionSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, Integer> {
}
