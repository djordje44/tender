package ch.olmero.tender.service.impl;

import ch.olmero.tender.entity.Bidder;
import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.repository.BidderRepository;
import ch.olmero.tender.repository.OfferRepository;
import ch.olmero.tender.repository.OfferRepositoryCustom;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    OfferService offerService;

    @Mock
    OfferRepository offerRepository;
    @Mock
    OfferRepositoryCustom offerRepositoryCustom;
    @Mock
    TenderRepository tenderRepository;
    @Mock
    BidderRepository bidderRepository;

    @BeforeEach
    void setUp() {
        offerService = new OfferServiceImpl(offerRepository, offerRepositoryCustom, tenderRepository, bidderRepository);
    }

    @Test
    void addOffer_InvalidTenderException() {
        Offer offer = generateOffer();
        when(tenderRepository.findById(offer.getBidder().getId())).thenReturn(Optional.empty());

        NoSuchElementException result = assertThrows(NoSuchElementException.class, () -> offerService.addOffer(offer));

        assertEquals(format("There is no tender with id %d", offer.getTender().getId()), result.getMessage());
        verify(bidderRepository, never()).findById(any());
        verify(offerRepository, never()).save(any());
    }

    @Test
    void addOffer_InvalidBidderException() {
        Offer offer = generateOffer();
        when(tenderRepository.findById(offer.getTender().getId())).thenReturn(Optional.of(new Tender()));
        when(bidderRepository.findById(offer.getBidder().getId())).thenReturn(Optional.empty());

        NoSuchElementException result = assertThrows(NoSuchElementException.class, () -> offerService.addOffer(offer));

        assertEquals(format("There is no bidder with id %d", offer.getBidder().getId()), result.getMessage());
        verify(offerRepository, never()).save(any());
    }

    @Test
    void addOffer_Success() {
        Offer offer = generateOffer();
        when(tenderRepository.findById(offer.getTender().getId())).thenReturn(Optional.of(new Tender()));
        when(bidderRepository.findById(offer.getBidder().getId())).thenReturn(Optional.of(new Bidder()));
        when(offerRepository.save(offer)).thenReturn(offer);

        Offer result = offerService.addOffer(offer);

        assertEquals(offer, result);
    }

    @Test
    void getBidderOffers_Success() {
        Integer bidderId = 1;
        Integer tenderId = 1;
        List<Offer> offers = mock(List.class);
        when(offerRepositoryCustom.getBidderOffers(bidderId, tenderId)).thenReturn(offers);

        List<Offer> result = offerService.getBidderOffers(bidderId, tenderId);

        assertEquals(offers, result);
    }

    private Offer generateOffer() {
        Offer offer = new Offer();

        Bidder bidder = new Bidder();
        bidder.setId(1);
        offer.setBidder(bidder);

        Tender tender = new Tender();
        tender.setId(1);
        offer.setTender(tender);

        return offer;
    }

}