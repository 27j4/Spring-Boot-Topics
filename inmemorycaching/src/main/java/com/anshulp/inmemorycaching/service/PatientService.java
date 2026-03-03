package com.anshulp.inmemorycaching.service;

import com.anshulp.inmemorycaching.entity.Patient;
import com.anshulp.inmemorycaching.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Cacheable(value = "patients", key = "#id")
    public String getPatientById(Long id) {

        log.info("Fetching patient with ID: {}", id);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return patientRepository.findById(id)
                .map(patient -> "Patient Name: " + patient.getEmail() + ", Age: " + patient.getAge())
                .orElse("Patient not found");
    }

    public String createPatient(String email, int age) {
        if (patientRepository.findByEmail(email).isPresent()) {
            return "Patient with email " + email + " already exists.";
        }
        Patient patient = new Patient();
        patient.setEmail(email);
        patient.setAge(age);
        patientRepository.save(patient);
        return patient.toString();
    }

    @CacheEvict(value = "patients", key = "#id")
    public String deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return "Patient with ID " + id + " deleted successfully.";
        } else {
            return "Patient with ID " + id + " not found.";
        }
    }

    @CachePut(value = "patients", key = "#id")
    public String updatePatient(Long id, String email, int age) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setEmail(email);
                    patient.setAge(age);
                    patientRepository.save(patient);
                    return "Patient updated: " + patient.toString();
                })
                .orElse("Patient with ID " + id + " not found.");
    }
}
