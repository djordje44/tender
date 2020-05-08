package ch.olmero.tender.controller;

import ch.olmero.tender.dto.AddOfferDto;
import ch.olmero.tender.entity.Offer;
import ch.olmero.tender.mapper.OfferMapper;
import ch.olmero.tender.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public AddOfferDto addOffer(@RequestBody @Valid AddOfferDto addOfferDto) {

        Offer result = offerService.addOffer(
                OfferMapper.INSTANCE.addOfferDtoToOffer(addOfferDto));

        return OfferMapper.INSTANCE.offerToAddOfferDto(result);
    }

}
