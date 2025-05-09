package com.duoc.backend.medication;

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
public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @Test
    void testGetAllMedications() {
        // Preparar datos
        Medication med1 = new Medication("Paracetamol", 5000.0);
        Medication med2 = new Medication("Ibuprofeno", 7500.0);
        
        when(medicationRepository.findAll()).thenReturn(Arrays.asList(med1, med2));

        // Ejecutar
        List<Medication> result = medicationService.getAllMedications();

        // Verificar
        assertEquals(2, result.size());
        assertEquals("Ibuprofeno", result.get(1).getName());
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    void testGetMedicationById_Exists() {
        // Preparar
        Long medId = 1L;
        Medication medication = new Medication("Amoxicilina", 12000.0);
        medication.setId(medId);

        when(medicationRepository.findById(medId)).thenReturn(Optional.of(medication));

        // Ejecutar
        Medication result = medicationService.getMedicationById(medId);

        // Verificar
        assertNotNull(result);
        assertEquals(12000.0, result.getCost());
    }

    @Test
    void testGetMedicationById_NotFound() {
        // Preparar
        Long medId = 99L;
        when(medicationRepository.findById(medId)).thenReturn(Optional.empty());

        // Ejecutar
        Medication result = medicationService.getMedicationById(medId);

        // Verificar
        assertNull(result);
    }

    @Test
    void testSaveMedication() {
        // Preparar
        Medication newMed = new Medication("Ketoprofeno", 8500.0);
        when(medicationRepository.save(any(Medication.class))).thenReturn(newMed);

        // Ejecutar
        Medication saved = medicationService.saveMedication(newMed);

        // Verificar
        assertEquals("Ketoprofeno", saved.getName());
        verify(medicationRepository, times(1)).save(newMed);
    }

    @Test
    void testDeleteMedication() {
        // Preparar
        Long medId = 1L;

        // Ejecutar
        medicationService.deleteMedication(medId);

        // Verificar
        verify(medicationRepository, times(1)).deleteById(medId);
    }
}