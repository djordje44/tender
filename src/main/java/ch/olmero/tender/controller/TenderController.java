package ch.olmero.tender.controller;

import ch.olmero.tender.dto.AcceptOfferDto;
import ch.olmero.tender.dto.AddTenderDto;
import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.mapper.TenderMapper;
import ch.olmero.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tenders")
public class TenderController {

    private final TenderService tenderService;

    @PostMapping
    public AddTenderDto addTender(@RequestBody @Valid AddTenderDto tenderDto) {

        Tender result = tenderService.addTender(
                TenderMapper.INSTANCE.addTenderDtoToTender(tenderDto));

        return TenderMapper.INSTANCE.tenderToAddTenderDto(result);
    }

    @PutMapping("/offer")
    public void acceptOffer(@RequestBody @Valid AcceptOfferDto acceptOfferDto) {

        tenderService.acceptOffer(acceptOfferDto.getTenderId(), acceptOfferDto.getOfferId());
    }

    @GetMapping("{id}/offers")
    public List<TenderOfferDto> acceptOffer(@PathVariable Integer id) {

        return tenderService.getTenderOffers(id);
    }

}
