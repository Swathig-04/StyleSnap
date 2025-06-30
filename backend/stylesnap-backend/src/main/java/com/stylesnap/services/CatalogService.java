package com.stylesnap.services;

import com.stylesnap.models.Catalog;
import com.stylesnap.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<Catalog> getAllProducts() {
        return catalogRepository.findAll();
    }
}
