package com.stylesnap.controllers;

import com.stylesnap.models.Catalog;
import com.stylesnap.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")



@RequestMapping("/api/catalog")
public class CatalogController {

    @Autowired
    private CatalogRepository catalogRepository;
    @GetMapping
    public List<Catalog> getCatalogItems(@RequestParam(required = false) String category) {
        List<Catalog> allProducts = catalogRepository.findAll();

        if (category == null || category.isEmpty()) {
            return allProducts;
        }

        String targetPrefix = category.equalsIgnoreCase("female") ? "her" :
                              category.equalsIgnoreCase("male") ? "him" : "";

        return allProducts.stream()
                .filter(product -> product.getImageURL().toLowerCase().startsWith(targetPrefix))
                .toList();
    }
    @GetMapping("/{id}")
    public Catalog getProductById(@PathVariable int id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found with ID: " + id));
    }
    @PostMapping
    public Catalog addProduct(@RequestBody Catalog newProduct) {
        System.out.println("🆕 New product added: " + newProduct.getProductName());

        return catalogRepository.save(newProduct);
    }
}
