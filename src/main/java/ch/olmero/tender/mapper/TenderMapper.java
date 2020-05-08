package ch.olmero.tender.mapper;

import ch.olmero.tender.dto.AddTenderDto;
import ch.olmero.tender.dto.IssuerTenderDto;
import ch.olmero.tender.entity.Tender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenderMapper {

    TenderMapper INSTANCE = Mappers.getMapper(TenderMapper.class);

    @Mappings({
            @Mapping(target = "issuer.id", source = "issuerId"),
            @Mapping(target = "constructionSite.id", source = "constructionSiteId")
    })
    Tender addTenderDtoToTender(AddTenderDto addTenderDto);

    @Mappings({
            @Mapping(target = "issuerId", source = "issuer.id"),
            @Mapping(target = "constructionSiteId", source = "constructionSite.id")
    })
    AddTenderDto tenderToAddTenderDto(Tender tender);

    @Mappings({
            @Mapping(target = "constructionSiteId", source = "constructionSite.id")
    })
    IssuerTenderDto tenderToIssuerTenderDto(Tender tender);

}
