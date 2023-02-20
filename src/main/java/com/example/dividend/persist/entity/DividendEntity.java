package com.example.dividend.persist.entity;

import com.example.dividend.model.Dividend;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@Setter
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = { "companyId","date" }
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class DividendEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity(Long companyId, Dividend dividend){
        this.companyId = companyId;
        this.date = dividend.getDate();
        this.dividend = dividend.getDividend();
    }
}
