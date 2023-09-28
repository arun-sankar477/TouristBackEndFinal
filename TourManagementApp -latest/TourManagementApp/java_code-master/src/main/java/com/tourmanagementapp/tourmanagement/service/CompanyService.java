package com.tourmanagementapp.tourmanagement.service;


import com.tourmanagementapp.tourmanagement.controller.TrafficUpdateRequest;
import com.tourmanagementapp.tourmanagement.models.Company;

import com.tourmanagementapp.tourmanagement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public Company addCompany(Company company) {
        // Set unique branchId and addedDate here
        // (You can use UUID or any other mechanism to generate a unique branchId)
        company.setBranchId(generateUniqueBranchId());
        company.setAddedDate(LocalDate.now());
        validateCompanyDetails(company);
        double tarrif = company.getTarrif();
        validateAndSetTariffDetails(tarrif);

        // Save the company with tariff details
        return companyRepository.save(company);
    }

    private void validateCompanyDetails(Company company) {
        // Website should contain "www"
        if (!company.getWebsite().contains("www")) {
            throw new Company.CustomException("Website should contain 'www'.");
        }

        // Validate email format
        if (!company.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new Company.CustomException("Invalid email format.");
        }

        // Validate contact number (exactly 10 digits and numeric)
        if (!company.getContact().matches("\\d{10}")) {
            throw new Company.CustomException("Mobile number must be exactly 10 digits and contain only numeric characters.");
        }
    }



    private String generateUniqueBranchId() {
        // Generate a random number with a maximum of 5 digits
        int maxNumber = 99999; // Maximum 5-digit number
        int randomBranchId = new Random().nextInt(maxNumber + 1);

        // Format the branch ID with leading zeros if needed
        String formattedBranchId = String.format("%05d", randomBranchId);

        // Implement any additional logic you need for uniqueness
        // Example: Check if the generated ID already exists in the database

        return formattedBranchId;
    }

    private void validateAndSetTariffDetails(double tariff) {
        if (tariff < Company.MIN_TARIFF || tariff > Company.MAX_TARIFF) {
            throw new Company.CustomException("Tariff details must range between 50,000 - 100,000.");
        }
    }

    private void validateAndSetTariffDetails(TrafficUpdateRequest tariff) {
        double tarrif = tariff.getTariff();
        if (tarrif < Company.MIN_TARIFF || tarrif > Company.MAX_TARIFF) {
            throw new Company.CustomException("Tariff details must range between 50,000 - 100,000.");
        }
    }


    public void updateTrafficDetails(String branchId, TrafficUpdateRequest trafficUpdateRequest) {
        Company company = companyRepository.findByBranchId(branchId);

        if (company == null) {
            throw new Company.CustomException("Invalid branchId");
        }
        validateAndSetTariffDetails(trafficUpdateRequest);
        //List<TrafficDetails> trafficDetails = company.getTrafficDetails();
        if (trafficUpdateRequest != null) {
            company.setTarrif(trafficUpdateRequest.getTariff());
           

        }

        // Update the lastUpdateDate before saving the Company entity
        company.setLastUpdateDate(LocalDate.now());

        companyRepository.save(company);

    }






	public List<Company> searchCompanies(String criteria, String criteriaValue) {
		// TODO Auto-generated method stub
		
		//return null;
		switch (criteria.toLowerCase()) {
		case "branchid":
		return companyRepository.findByBranchIdContaining(criteriaValue);
		case "branchname":
		return companyRepository.findByBranchNameContaining(criteriaValue);
		case "place":
		return companyRepository.findByPlaceContaining(criteriaValue);
		default:
		throw new Company.CustomException("Invalid search criteria.");
		}
		
  

	}}
   

