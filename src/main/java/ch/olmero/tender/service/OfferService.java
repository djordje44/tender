package ch.olmero.tender.service;

import ch.olmero.tender.entity.Offer;

public interface OfferService {

    /**
     * Save given offer.
     *
     * @param offer Offer to be saved.
     * @return offer
     */
    Offer addOffer(Offer offer);
}
