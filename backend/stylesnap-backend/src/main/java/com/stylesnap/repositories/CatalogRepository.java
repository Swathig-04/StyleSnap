package com.stylesnap.repositories;

import com.stylesnap.models.Catalog; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
}
