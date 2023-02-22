package com.example.dividend.service;

import com.example.dividend.exception.impl.NoCompanyException;
import com.example.dividend.model.Company;
import com.example.dividend.model.Dividend;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.model.constant.CacheKey;
import com.example.dividend.persist.Repository.CompanyRepository;
import com.example.dividend.persist.Repository.DividendRepository;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
public class FinanceService {

    private final DividendRepository dividendRepository;
    private final CompanyRepository companyRepository;

    public ScrapedResult getDividendByCompanyName(String companyName){

        log.info("search Company - > " ,companyName);
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(()->new NoCompanyException());
        List<DividendEntity> dividendEntities= this.dividendRepository.findAllByCompanyId(company.getId());
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(),e.getDividend())).collect(Collectors.toList());

        return new ScrapedResult(new Company(company.getTicker(),company.getName()),dividends);

    }
}
