package com.tourmanagementapp.tourmanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Company")
public class Company {

	 @Id
    private int id;
 
  private String branchId;

   public String branchName;
    public String companyplace;
  
    @Pattern(regexp = ".*www.*", message = "Website should contain 'www'")
    public String website;

   
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Pattern(regexp = "\\d+", message = "Mobile number must contain only digits")
    private String contact;

    
    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b", message = "Invalid email format")
    public String email;

   public Double tarrif;
   // private List<TariffDetails> tariffs;
    public String place;



    private LocalDate addedDate;

   
    private LocalDate lastUpdateDate;

    public static final double MIN_TARIFF = 50000.00;
    public static final double MAX_TARIFF = 100000.00;


    public static class CustomException extends RuntimeException {
        public CustomException(String message) {
            super(message);
        }
    }
	
	

}
