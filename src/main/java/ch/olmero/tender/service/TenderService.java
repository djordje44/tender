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
}
