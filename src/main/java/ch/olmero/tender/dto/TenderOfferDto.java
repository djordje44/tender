package ch.olmero.tender.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TenderOfferDto {

    private Integer id;
    private Integer tenderId;
    private Integer bidderId;
    private BigDecimal price;
    private Boolean accepted;
}