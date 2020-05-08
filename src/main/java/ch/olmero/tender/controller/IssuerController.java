package ch.olmero.tender.controller;

import ch.olmero.tender.dto.IssuerTenderDto;
import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.mapper.OfferMapper;
import ch.olmero.tender.mapper.TenderMapper;
import ch.olmero.tender.service.OfferService;
import ch.olmero.tender.service.TenderService;
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
@RequestMapping("issuers")
public class IssuerController {

    private final TenderService tenderService;

    @GetMapping("{id}/tenders")
    public List<IssuerTenderDto> getIssuerTenders(@PathVariable Integer id) {

        return tenderService.getIssuerTenders(id).stream()
                .map(TenderMapper.INSTANCE::tenderToIssuerTenderDto)
                .collect(toList());
    }

}
