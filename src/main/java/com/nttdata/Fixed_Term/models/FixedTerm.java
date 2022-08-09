package com.nttdata.Fixed_Term.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "FixedTerm")
public class FixedTerm {
    @Id
    @NotNull
    private String accountNumber;
    @NotNull
    private Customer customer;
    @NotNull
    private float commission;
    @NotNull
    private int movement_limit;
    private LocalDateTime registration_date;
    private int movementNumber;
    private LocalDate movementDate;
}
