package com.duoc.backend.care;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CareRepository extends CrudRepository<Care, Long> {
     // Método para buscar por nombre (like)
     List<Care> findByNameContaining(String name);
    
     // Método nuevo para buscar por costo menor que
     List<Care> findByCostLessThan(Double cost);
     
     // Si necesitas incluir el límite (menor o igual)
     List<Care> findByCostLessThanEqual(Double cost);
}
