package ch.olmero.tender.service;

import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.entity.Tender;

import java.util.List;

public interface TenderService {

    /**
     * Save given tender.
     *
     * @param tender Tender to be saved.
     * @return tender
     */
    Tender addTender(Tender tender);

    /**
     * Accept offer for tender.
     *
     * @param tenderId Id of tender.
     * @param offerId Id of offer to be accepted.
     */
    void acceptOffer(Integer tenderId, Integer offerId);

    /**
     * Get all offers for tender.
     *
     * @param tenderId Id of tender.
     * @return list of offers.
     */
    List<TenderOfferDto> getTenderOffers(Integer tenderId);
}
