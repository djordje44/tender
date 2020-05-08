package ch.olmero.tender.controller;

import ch.olmero.tender.dto.AddTenderDto;
import ch.olmero.tender.entity.Tender;
import ch.olmero.tender.mapper.TenderMapper;
import ch.olmero.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
