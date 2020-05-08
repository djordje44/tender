package ch.olmero.tender.repository;

import ch.olmero.tender.entity.Offer;

import java.util.List;

public interface OfferRepositoryCustom {

    /**
     * Get list of offers for bidder and offer
     *
     * @param bidderId Bidder ID.
     * @param tenderId  Tender ID.
     * @return list of offers.
     */
    List<Offer> getBidderOffers(Integer bidderId, Integer tenderId);

}
