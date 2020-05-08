package ch.olmero.tender.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddOfferDto {

    private Integer id;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @NotNull
    private Integer tenderId;
    @NotNull
    private Integer bidderId;
}
