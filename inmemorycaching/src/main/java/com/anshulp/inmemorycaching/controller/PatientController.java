package com.anshulp.inmemorycaching.controller;

import com.anshulp.inmemorycaching.service.CacheInspectionService;
import com.anshulp.inmemorycaching.service.PatientService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final CacheInspectionService cacheInspectionService;

    public PatientController(PatientService patientService, CacheInspectionService cacheInspectionService) {
        this.patientService = patientService;
        this.cacheInspectionService = cacheInspectionService;
    }

    @GetMapping("/get/{id}")
    public String getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public String createPatient(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "age") int age
    ) {
        return patientService.createPatient(email, age);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        return patientService.deletePatient(id);
    }

    @PutMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, String email, int age) {
        return patientService.updatePatient(id, email, age);
    }

    @GetMapping("/cache-inspect")
    public void inspectCache() {
        cacheInspectionService.printCacheContents("patients");
    }
}

