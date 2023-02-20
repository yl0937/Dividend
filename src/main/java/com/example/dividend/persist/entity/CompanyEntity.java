package com.example.dividend.persist.entity;

import com.example.dividend.model.Company;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "COMPANY")
@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

    public CompanyEntity(Company company){
        this.ticker = company.getTicker();
        this.name = company.getName();
    }
}
