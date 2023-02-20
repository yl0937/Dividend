package com.example.dividend.web;

import com.example.dividend.model.Company;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        var result = this.companyService.getCompanyNamesByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> searchCompany(final Pageable pageable){
        Page<CompanyEntity> companyEntities = this.companyService.getAllCompany(pageable);

        return ResponseEntity.ok(companyEntities);
    }

    @PostMapping("")
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker().trim();
        if(ObjectUtils.isEmpty(ticker))
        {
            throw  new RuntimeException("ticker is empty");
        }

        Company company = this.companyService.save(ticker);
        this.companyService.addAutocompleteKeyword(company.getName());
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}

