package de.hs_kl.vis.ss24.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class Product {

    @JsonProperty("product_id")
    @Schema(name = "product_id", description = "Product id", example = "1")
    private Long productId;
    @Schema(name = "name",description = "Product Name", example = "Car")
    private String name;
    @Schema(name = "price",description = "Product price", example = "7.99")
    private Double price;
    @Schema(name = "weight",description = "Product weight", example = "10.2")
    private Double weight;
    @Schema(name = "length",description = "Product length", example = "5.4")
    private Double length;
    @Schema(name = "width",description = "Product width", example = "5.4")
    private Double width;
    @Schema(name = "amount",description = "Amount available units", example = "70")
    private Long amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
