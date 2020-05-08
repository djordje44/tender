package ch.olmero.tender.service.impl;

import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.exception.BadRequestException;
import ch.olmero.tender.repository.ConstructionSiteRepository;
import ch.olmero.tender.repository.IssuerRepository;
import ch.olmero.tender.repository.OfferRepository;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderRepository tenderRepository;
    private final IssuerRepository issuerRepository;
    private final ConstructionSiteRepository constructionSiteRepository;
    private final OfferRepository offerRepository;


    @Override
    public Tender addTender(Tender tender) {
        log.debug(format("Adding tender %s", tender.getName()));

        validateIssuer(tender.getIssuer().getId());
        validateConstructionSite(tender.getConstructionSite().getId());

        return tenderRepository.save(tender);
    }

    @Override
    @Transactional
    public void acceptOffer(Integer tenderId, Integer offerId) {
        log.debug(format("Accepting offer %d for tender %d", offerId, tenderId));

        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new NoSuchElementException(format("There is no tender with id %d", tenderId)));
        Offer offer = offerRepository.findOfferByIdAndTenderId(offerId, tenderId)
                .orElseThrow(() -> new NoSuchElementException(
                        format("There is no offer %d for tender %d", offerId, tenderId)));

        if (tender.isClosed()) {
            log.error(format("Tender %d is closed, can't accept offer.", tenderId));
            throw new BadRequestException(format("Tender %d is closed, can't accept offer.", tenderId));
        }

        tender.setClosed(true);
        offer.setAccepted(true);

        tenderRepository.save(tender);
        offerRepository.save(offer);
        offerRepository.rejectNonAcceptedOffers(tender.getId());
    }

    private void validateIssuer(Integer issuerId) {
        issuerRepository.findById(issuerId)
                .orElseThrow(() -> new NoSuchElementException(format("There is no issuer with id %d", issuerId)));
    }

    private void validateConstructionSite(Integer constructionSiteId) {
        constructionSiteRepository.findById(constructionSiteId)
                .orElseThrow(() -> new NoSuchElementException(
                        format("There is no construction site with id %d", constructionSiteId)));
    }

}
