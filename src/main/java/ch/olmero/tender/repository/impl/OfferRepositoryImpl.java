package ch.olmero.tender.repository.impl;

import ch.olmero.tender.entity.Bidder;
import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.repository.OfferRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Offer> getBidderOffers(Integer bidderId, Integer tenderId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cq = cb.createQuery(Offer.class);
        Root<Offer> offer = cq.from(Offer.class);

        Path<Bidder> bidder = offer.get("bidder");
        List<Predicate> predicates = new ArrayList<>();
        Predicate bidderPredicate = cb.equal(bidder.get("id"), bidderId);
        predicates.add(bidderPredicate);

        if (tenderId != null) {
            Path<Tender> tender = offer.get("tender");
            Predicate tenderPredicate = cb.equal(tender.get("id"), tenderId);
            predicates.add(tenderPredicate);
        }

        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Offer> query = em.createQuery(cq);

        return query.getResultList();
    }
}
