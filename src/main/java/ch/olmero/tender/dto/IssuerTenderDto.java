package ch.olmero.tender.dto;

import lombok.Data;

@Data
public class IssuerTenderDto {

    private Integer id;
    private Integer constructionSiteId;
    private String name;
    private String description;
    private Boolean closed;
}
