package com.vgs.vocation_guidance.repository;

import com.vgs.vocation_guidance.entity.CareerRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRecommendationRepository extends JpaRepository<CareerRecommendation, Long> {

    List<CareerRecommendation> findByUserId(Long userId);
}
