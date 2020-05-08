package ch.olmero.tender.service.impl;

import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.repository.BidderRepository;
import ch.olmero.tender.repository.OfferRepository;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final BidderRepository bidderRepository;

    @Override
    public Offer addOffer(Offer offer) {
        log.debug(format("Adding offer for tender %d", offer.getTender().getId()));

        validateTender(offer.getTender().getId());
        validateBidder(offer.getBidder().getId());

        return offerRepository.save(offer);
    }

    private void validateTender(Integer tenderId) {
        tenderRepository.findById(tenderId)
                .orElseThrow(() -> new NoSuchElementException(format("There is no tender with id %d", tenderId)));
    }

    private void validateBidder(Integer bidderId) {
        bidderRepository.findById(bidderId)
                .orElseThrow(() -> new NoSuchElementException(format("There is no bidder with id %d", bidderId)));
    }
}
