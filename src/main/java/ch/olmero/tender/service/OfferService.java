package ch.olmero.tender.service;

import ch.olmero.tender.entity.Offer;

import java.util.List;

public interface OfferService {

    /**
     * Save given offer.
     *
     * @param offer Offer to be saved.
     * @return offer
     */
    Offer addOffer(Offer offer);

    /**
     * Get offers for bidder.
     *
     * @param bidderId Id of bidder.
     * @param tenderId Id of tender.
     * @return list of offers.
     */
    List<Offer> getBidderOffers(Integer bidderId, Integer tenderId);
}
