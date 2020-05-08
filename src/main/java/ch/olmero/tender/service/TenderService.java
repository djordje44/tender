package ch.olmero.tender.service;

import ch.olmero.tender.entity.Tender;

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
     * @return tender
     */
    void acceptOffer(Integer tenderId, Integer offerId);
}
