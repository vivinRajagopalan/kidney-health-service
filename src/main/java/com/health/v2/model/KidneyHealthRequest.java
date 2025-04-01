package com.health.v2.model;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class KidneyHealthRequest {
    @NotNull(message = "HbA1c value is required")
    @Positive(message = "HbA1c must be a positive value")
    private Double hbA1c;
    
    @NotNull(message = "Albumin value is required")
    @Positive(message = "Albumin must be a positive value")
    private Double albumin;
    
    @NotNull(message = "Urine creatinine value is required")
    @Positive(message = "Urine creatinine must be a positive value")
    private Double urineCreatinine;
    
    @NotNull(message = "Serum creatinine value is required")
    @Positive(message = "Serum creatinine must be a positive value")
    private Double serumCreatinine;
} 