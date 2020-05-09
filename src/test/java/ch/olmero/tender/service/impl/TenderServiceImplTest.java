package ch.olmero.tender.service.impl;

import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.entity.ConstructionSite;
import ch.olmero.tender.entity.Issuer;
import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.exception.BadRequestException;
import ch.olmero.tender.mapper.OfferMapper;
import ch.olmero.tender.repository.ConstructionSiteRepository;
import ch.olmero.tender.repository.IssuerRepository;
import ch.olmero.tender.repository.OfferRepository;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.TenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenderServiceImplTest {

    TenderService tenderService;

    @Mock
    TenderRepository tenderRepository;
    @Mock
    IssuerRepository issuerRepository;
    @Mock
    ConstructionSiteRepository constructionSiteRepository;
    @Mock
    OfferRepository offerRepository;

    @BeforeEach
    void setUp() {
        tenderService = new TenderServiceImpl(tenderRepository, issuerRepository,
                constructionSiteRepository, offerRepository);
    }

    @Test
    void addTender_InvalidIssuerException() {
        Tender tender = generateTender();
        when(issuerRepository.findById(tender.getIssuer().getId())).thenReturn(Optional.empty());

        NoSuchElementException result =
                assertThrows(NoSuchElementException.class, () -> tenderService.addTender(tender));

        assertEquals(format("There is no issuer with id %d", tender.getIssuer().getId()), result.getMessage());
        verify(constructionSiteRepository, never()).findById(any());
        verify(tenderRepository, never()).save(any());
    }

    @Test
    void addTender_InvalidConstructionSiteException() {
        Tender tender = generateTender();
        when(issuerRepository.findById(tender.getIssuer().getId())).thenReturn(Optional.of(new Issuer()));
        when(constructionSiteRepository.findById(tender.getConstructionSite().getId())).thenReturn(Optional.empty());

        NoSuchElementException result =
                assertThrows(NoSuchElementException.class, () -> tenderService.addTender(tender));

        assertEquals(format("There is no construction site with id %d", tender.getConstructionSite().getId()),
                result.getMessage());
        verify(tenderRepository, never()).save(any());
    }

    @Test
    void addTender_Success() {
        Tender tender = generateTender();
        when(issuerRepository.findById(tender.getIssuer().getId()))
                .thenReturn(Optional.of(new Issuer()));
        when(constructionSiteRepository.findById(tender.getConstructionSite().getId()))
                .thenReturn(Optional.of(new ConstructionSite()));
        when(tenderRepository.save(tender)).thenReturn(tender);

        Tender result = tenderService.addTender(tender);

        assertEquals(tender, result);
    }

    @Test
    void acceptOffer_InvalidTenderException() {
        Integer tenderId = 1;
        Integer offerId = 2;
        when(tenderRepository.findById(tenderId)).thenReturn(Optional.empty());

        NoSuchElementException result =
                assertThrows(NoSuchElementException.class, () -> tenderService.acceptOffer(tenderId, offerId));

        assertEquals(format("There is no tender with id %d", tenderId), result.getMessage());
        verify(offerRepository, never()).findOfferByIdAndTenderId(any(), any());
        verify(tenderRepository, never()).save(any());
        verify(offerRepository, never()).save(any());
        verify(offerRepository, never()).rejectNonAcceptedOffers(any());
    }

    @Test
    void acceptOffer_InvalidOfferException() {
        Integer tenderId = 1;
        Integer offerId = 2;
        when(tenderRepository.findById(tenderId)).thenReturn(Optional.of(new Tender()));
        when(offerRepository.findOfferByIdAndTenderId(offerId, tenderId)).thenReturn(Optional.empty());

        NoSuchElementException result =
                assertThrows(NoSuchElementException.class, () -> tenderService.acceptOffer(tenderId, offerId));

        assertEquals(format("There is no offer %d for tender %d", offerId, tenderId), result.getMessage());
        verify(tenderRepository, never()).save(any());
        verify(offerRepository, never()).save(any());
        verify(offerRepository, never()).rejectNonAcceptedOffers(any());
    }

    @Test
    void acceptOffer_TenderClosedException() {
        Integer tenderId = 1;
        Integer offerId = 2;
        Tender tender = mock(Tender.class);
        when(tender.isClosed()).thenReturn(true);
        when(tenderRepository.findById(tenderId)).thenReturn(Optional.of(tender));
        when(offerRepository.findOfferByIdAndTenderId(offerId, tenderId)).thenReturn(Optional.of(new Offer()));

        BadRequestException result =
                assertThrows(BadRequestException.class, () -> tenderService.acceptOffer(tenderId, offerId));

        assertEquals(format("Tender %d is closed, can't accept offer.", tenderId), result.getMessage());
        verify(tenderRepository, never()).save(any());
        verify(offerRepository, never()).save(any());
        verify(offerRepository, never()).rejectNonAcceptedOffers(any());
    }

    @Test
    void acceptOffer_Success() {
        Integer tenderId = 1;
        Integer offerId = 2;
        Tender tender = mock(Tender.class);
        tender.setClosed(false);
        Offer offer = mock(Offer.class);
        when(tenderRepository.findById(tenderId)).thenReturn(Optional.of(tender));
        when(offerRepository.findOfferByIdAndTenderId(offerId, tenderId)).thenReturn(Optional.of(offer));

        tenderService.acceptOffer(tenderId, offerId);

        verify(tender).setClosed(true);
        verify(offer).setAccepted(true);
        verify(tenderRepository, times(1)).save(tender);
        verify(offerRepository, times(1)).save(offer);
        verify(offerRepository, times(1)).rejectNonAcceptedOffers(tender.getId());
    }

    @Test
    void getTenderOffers_Success() {
        Integer tenderId = 1;
        List<Offer> offers = mock(List.class);
        List<TenderOfferDto> tenderOfferDtos = offers.stream()
                .map(OfferMapper.INSTANCE::offerToTenderOfferDto)
                .collect(toList());
        when(offerRepository.findOffersByTenderId(tenderId)).thenReturn(offers);

        List<TenderOfferDto> result = tenderService.getTenderOffers(tenderId);

        assertEquals(tenderOfferDtos, result);
    }

    @Test
    void getIssuerTenders_Success() {
        Integer issuerId = 1;
        List<Tender> tenders = mock(List.class);
        when(tenderRepository.findAllByIssuerId(issuerId)).thenReturn(tenders);

        List<Tender> result = tenderService.getIssuerTenders(issuerId);

        assertEquals(tenders, result);
    }

    private Tender generateTender() {
        Tender tender = new Tender();

        Issuer issuer = new Issuer();
        issuer.setId(1);
        tender.setIssuer(issuer);

        ConstructionSite constructionSite = new ConstructionSite();
        constructionSite.setId(2);
        tender.setConstructionSite(constructionSite);

        return tender;
    }
}