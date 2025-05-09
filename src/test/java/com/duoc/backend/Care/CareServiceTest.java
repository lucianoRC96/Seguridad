package com.duoc.backend.Care;

import com.duoc.backend.care.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("cobertura")
public class CareServiceTest {

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private CareService careService;

    @Test
    void testGetAllCares_EmptyList() {
        // Simular repositorio vacío
        when(careRepository.findAll()).thenReturn(Arrays.asList());

        // Ejecutar
        List<Care> result = careService.getAllCares();

        // Verificar
        assertTrue(result.isEmpty());
        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCares_WithItems() {
        // Preparar datos
        Care care1 = new Care("Baño médico", 15000.0);
        Care care2 = new Care("Corte de uñas", 5000.0);
        
        when(careRepository.findAll()).thenReturn(Arrays.asList(care1, care2));

        // Ejecutar
        List<Care> result = careService.getAllCares();

        // Verificar
        assertEquals(2, result.size());
        assertEquals("Baño médico", result.get(0).getName());
        assertEquals(5000.0, result.get(1).getCost());
        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetCareById_Exists() {
        // Preparar
        Long careId = 1L;
        Care care = new Care("Desparasitación", 12000.0);
        care.setId(careId);

        when(careRepository.findById(careId)).thenReturn(Optional.of(care));

        // Ejecutar
        Care result = careService.getCareById(careId);

        // Verificar
        assertNotNull(result);
        assertEquals(12000.0, result.getCost());
        assertEquals(careId, result.getId());
        verify(careRepository, times(1)).findById(careId);
    }

    @Test
    void testGetCareById_NotFound() {
        // Preparar
        Long careId = 99L;
        when(careRepository.findById(careId)).thenReturn(Optional.empty());

        // Ejecutar
        Care result = careService.getCareById(careId);

        // Verificar
        assertNull(result);
        verify(careRepository, times(1)).findById(careId);
    }

    @Test
    void testSaveCare() {
        // Preparar
        Care newCare = new Care("Vacunación", 25000.0);
        when(careRepository.save(any(Care.class))).thenReturn(newCare);

        // Ejecutar
        Care saved = careService.saveCare(newCare);

        // Verificar
        assertNotNull(saved);
        assertEquals("Vacunación", saved.getName());
        assertEquals(25000.0, saved.getCost());
        verify(careRepository, times(1)).save(newCare);
    }

    @Test
    void testDeleteCare() {
        // Preparar
        Long careId = 1L;

        // Ejecutar
        careService.deleteCare(careId);

        // Verificar
        verify(careRepository, times(1)).deleteById(careId);
    }

    // Prueba adicional para verificar el constructor (si es necesario)
    @Test
    void testCareConstructor() {
        Care care = new Care("Limpieza dental", 18000.0);
        assertNotNull(care);
        assertEquals("Limpieza dental", care.getName());
        assertEquals(18000.0, care.getCost());
    }
}