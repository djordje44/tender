package ch.olmero.tender.controller;

import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.mapper.OfferMapper;
import ch.olmero.tender.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("bidders")
public class BidderController {

    private final OfferService offerService;

    @GetMapping("{id}/offers")
    public List<TenderOfferDto> getBidderOffers(@PathVariable Integer id,
                                            @RequestParam(name = "tenderId", required = false) Integer tenderId) {

        return offerService.getBidderOffers(id, tenderId).stream()
                .map(OfferMapper.INSTANCE::offerToTenderOfferDto)
                .collect(toList());
    }

}
