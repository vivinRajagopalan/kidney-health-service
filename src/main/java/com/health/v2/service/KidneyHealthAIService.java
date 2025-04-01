package com.health.v2.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.health.v2.model.KidneyHealthRequest;

@Service
public class KidneyHealthAIService {
    private static final Logger logger = LoggerFactory.getLogger(KidneyHealthAIService.class);
    private static final String MODEL_PATH = "models/kidney-health/kidney-health.pt";
    private boolean modelAvailable;

    public KidneyHealthAIService() {
        Resource modelResource = new ClassPathResource(MODEL_PATH);
        modelAvailable = modelResource.exists();
        if (modelAvailable) {
            logger.info("Found kidney health model at {}", MODEL_PATH);
        } else {
            logger.warn("Could not find kidney health model at {}. Will use fallback predictions.", MODEL_PATH);
        }
    }

    public double predict(KidneyHealthRequest request) {
        if (!modelAvailable) {
            return calculateFallbackRiskScore(request);
        }

        // TODO: Implement actual model prediction using DJL or PyTorch Java bindings
        // For now, return fallback prediction
        return calculateFallbackRiskScore(request);
    }

    private double calculateFallbackRiskScore(KidneyHealthRequest request) {
        // Simple rule-based risk calculation
        double riskScore = 0.0;
        
        // HbA1c risk factor (normal range 4.0-5.6%)
        if (request.getHbA1c() > 6.5) {
            riskScore += 0.3;
        } else if (request.getHbA1c() > 5.7) {
            riskScore += 0.15;
        }
        
        // Albumin risk factor (normal range 3.4-5.4 g/dL)
        if (request.getAlbumin() < 3.4) {
            riskScore += 0.25;
        }
        
        // Urine creatinine risk factor
        if (request.getUrineCreatinine() > 300) {
            riskScore += 0.2;
        }
        
        // Serum creatinine risk factor (normal range 0.7-1.3 mg/dL)
        if (request.getSerumCreatinine() > 1.3) {
            riskScore += 0.25;
        }
        
        return Math.min(1.0, riskScore);
    }
} 