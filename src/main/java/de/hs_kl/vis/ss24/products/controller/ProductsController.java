package de.hs_kl.vis.ss24.products.controller;

import de.hs_kl.vis.ss24.products.controller.dto.Product;
import de.hs_kl.vis.ss24.products.persistence.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@Tag(name = "Products API")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Operation(summary = "Get all products", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @GetMapping
    private ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productsService.getProducts();
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error - Please Contact the owner");
        }
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @GetMapping("/{id}")
    private  ResponseEntity<?> getProductById(@PathVariable("id") @Parameter(name = "id", description = "Product id", example = "1") Long id) {
        try {
            Product product = productsService.getProductById(id);
            return ResponseEntity.status(200).body(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Update a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @PutMapping("/{id}/{price}")
    private ResponseEntity<?> updatePrice(@PathVariable("id") @Parameter(name = "id", description = "Product id", example = "1") Long id, @PathVariable @Parameter(name = "price", description = "Product price", example = "34.2") Double price) {
        try {
            Thread.sleep(3000);
            Product product = productsService.updateProductPrice(id, price);
            return ResponseEntity.status(200).body(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a product by id", description = "Returns a success indicator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteProduct(@PathVariable("id") @Parameter(name = "id", description = "Product id", example = "1") Long id) {
        try {
            productsService.removeProduct(id);
            return ResponseEntity.status(200).body("Product removed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
