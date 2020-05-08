package ch.olmero.tender.mapper;

import ch.olmero.tender.dto.AddOfferDto;
import ch.olmero.tender.dto.TenderOfferDto;
import ch.olmero.tender.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferMapper {

    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    @Mappings({
            @Mapping(target = "tender.id", source = "tenderId"),
            @Mapping(target = "bidder.id", source = "bidderId")
    })
    Offer addOfferDtoToOffer(AddOfferDto addOfferDto);

    @Mappings({
            @Mapping(target = "tenderId", source = "tender.id"),
            @Mapping(target = "bidderId", source = "bidder.id")
    })
    AddOfferDto offerToAddOfferDto(Offer offer);


    @Mappings({
            @Mapping(target = "tenderId", source = "tender.id"),
            @Mapping(target = "bidderId", source = "bidder.id")
    })
    TenderOfferDto offerToTenderOfferDto(Offer offer);

}
