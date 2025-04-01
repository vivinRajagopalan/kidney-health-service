package com.health.v2.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KidneyHealthAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(KidneyHealthAnalyzer.class);
    private static final String MODEL_PATH = "models/kidney-risk/kidney-risk.pt";
    private boolean modelAvailable;

    public KidneyHealthAnalyzer() {
        Resource modelResource = new ClassPathResource(MODEL_PATH);
        modelAvailable = modelResource.exists();
        if (modelAvailable) {
            logger.info("Found kidney risk model at {}", MODEL_PATH);
        } else {
            logger.warn("Could not find kidney risk model at {}. Will use fallback analysis.", MODEL_PATH);
        }
    }

    public double analyzeRisk(double hbA1c, double albumin, double urineCreatinine, double serumCreatinine) {
        if (!modelAvailable) {
            return calculateFallbackRiskScore(hbA1c, albumin, urineCreatinine, serumCreatinine);
        }

        // TODO: Implement actual model prediction using DJL or PyTorch Java bindings
        // For now, return fallback prediction
        return calculateFallbackRiskScore(hbA1c, albumin, urineCreatinine, serumCreatinine);
    }

    private double calculateFallbackRiskScore(double hbA1c, double albumin, double urineCreatinine, double serumCreatinine) {
        // Simple rule-based risk calculation
        double riskScore = 0.0;
        
        // HbA1c risk factor (normal range 4.0-5.6%)
        if (hbA1c > 6.5) {
            riskScore += 0.3;
        } else if (hbA1c > 5.7) {
            riskScore += 0.15;
        }
        
        // Albumin risk factor (normal range 3.4-5.4 g/dL)
        if (albumin < 3.4) {
            riskScore += 0.25;
        }
        
        // Urine creatinine risk factor
        if (urineCreatinine > 300) {
            riskScore += 0.2;
        }
        
        // Serum creatinine risk factor (normal range 0.7-1.3 mg/dL)
        if (serumCreatinine > 1.3) {
            riskScore += 0.25;
        }
        
        return Math.min(1.0, riskScore);
    }
} 