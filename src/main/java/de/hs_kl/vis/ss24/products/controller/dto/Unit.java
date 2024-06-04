package de.hs_kl.vis.ss24.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Unit {

    @JsonProperty("unit_id")
    private Long unitId;
    private Long productId;
    private String serialNumber;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
