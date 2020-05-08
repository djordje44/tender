package ch.olmero.tender.service.impl;

import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.repository.ConstructionSiteRepository;
import ch.olmero.tender.repository.IssuerRepository;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderRepository tenderRepository;
    private final IssuerRepository issuerRepository;
    private final ConstructionSiteRepository constructionSiteRepository;


    @Override
    public Tender addTender(Tender tender) {
        log.debug(format("Adding tender %s", tender.getName()));

        validateIssuer(tender.getIssuer().getId());
        validateConstructionSite(tender.getConstructionSite().getId());

        return tenderRepository.save(tender);
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
