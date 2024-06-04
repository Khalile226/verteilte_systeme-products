package de.hs_kl.vis.ss24.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class BookingPosition {

    @Schema(name = "product_id", description = "Product id", example = "1")
    @JsonProperty("product_id")
    private Long productId;
    @Schema(name = "amount", description = "Units amount", example = "5")
    private Long amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
