package ch.olmero.tender.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddTenderDto {

    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer issuerId;
    @NotNull
    private Integer constructionSiteId;
}
