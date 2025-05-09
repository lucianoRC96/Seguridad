package com.duoc.backend.medication;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicationRepository extends CrudRepository<Medication, Long> {
     // Búsqueda exacta ignorando mayúsculas/minúsculas
     List<Medication> findByNameIgnoreCase(String name);
    
     // Búsqueda por rango de precio
     List<Medication> findByCostBetween(Double minPrice, Double maxPrice);
     
     // Búsqueda de medicamentos con precio mayor que
     List<Medication> findByCostGreaterThan(Double price);
     
     // Búsqueda por nombre que contenga cadena (like)
     List<Medication> findByNameContaining(String nameFragment);
}