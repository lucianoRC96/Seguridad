package com.duoc.backend.appointment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("cobertura")
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointments() {
        // Preparar datos de prueba
        Appointment appointment1 = new Appointment();
        appointment1.setDate(LocalDate.of(2023, 12, 25));
        
        Appointment appointment2 = new Appointment();
        appointment2.setDate(LocalDate.of(2023, 12, 26));

        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment1, appointment2));

        // Ejecutar
        Iterable<Appointment> result = appointmentService.getAllAppointments();

        // Verificar
        assertEquals(2, ((List<Appointment>) result).size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentById_Exists() {
        // Preparar
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setReason("Control anual");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // Ejecutar
        Appointment result = appointmentService.getAppointmentById(appointmentId);

        // Verificar
        assertNotNull(result);
        assertEquals("Control anual", result.getReason());
    }

    @Test
    void testGetAppointmentById_NotFound() {
        // Preparar
        Long appointmentId = 99L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Ejecutar
        Appointment result = appointmentService.getAppointmentById(appointmentId);

        // Verificar
        assertNull(result);
    }

    @Test
    void testSaveAppointment() {
        // Preparar
        Appointment newAppointment = new Appointment();
        newAppointment.setVeterinarian("Dr. Pérez");
        
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(newAppointment);

        // Ejecutar
        Appointment saved = appointmentService.saveAppointment(newAppointment);

        // Verificar
        assertEquals("Dr. Pérez", saved.getVeterinarian());
        verify(appointmentRepository, times(1)).save(newAppointment);
    }

    @Test
    void testDeleteAppointment() {
        // Preparar
        Long appointmentId = 1L;

        // Ejecutar
        appointmentService.deleteAppointment(appointmentId);

        // Verificar
        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }
}