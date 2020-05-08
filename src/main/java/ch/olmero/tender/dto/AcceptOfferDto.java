package ch.olmero.tender.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AcceptOfferDto {

    @NotNull
    private Integer tenderId;
    @NotNull
    private Integer offerId;
}
