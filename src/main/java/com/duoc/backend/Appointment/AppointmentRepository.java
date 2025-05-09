package com.duoc.backend.appointment;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
     List<Appointment> findByDate(LocalDate date);
}
