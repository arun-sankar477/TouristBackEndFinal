package com.tourmanagementapp.tourmanagement.controller;

import com.tourmanagementapp.tourmanagement.models.Company;
import com.tourmanagementapp.tourmanagement.models.Company.CustomException;
import com.tourmanagementapp.tourmanagement.repository.CompanyRepository;
import com.tourmanagementapp.tourmanagement.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

//@CrossOrigin(origins="http://localhost:3000")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("branch/add-places")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        try {
            Company createdCompany = companyService.addCompany(company);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
        } catch (Company.CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


   @PutMapping("branch/update-tariff/{branchId}")
    public ResponseEntity<String> updateTariffDetails(@PathVariable String branchId,
                                                      @RequestBody TrafficUpdateRequest trafficUpdateRequest) {
        try {
            companyService.updateTrafficDetails(branchId,trafficUpdateRequest);
            return ResponseEntity.ok("Tariff details updated successfully.");
        } catch (Company.CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    
    
    @GetMapping("/admin/{criteria}/{criteriaValue}")
    public ResponseEntity<List<Company>> searchCompanies(@PathVariable String criteria, @PathVariable String criteriaValue) {
    try {
    List<Company> companies = companyService.searchCompanies(criteria, criteriaValue);
    return ResponseEntity.ok(companies);
    } catch (Company.CustomException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    }
    
 
    
//    
}
