package org.example.eventpro.Repository;

import org.example.eventpro.Domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

       List<Promotion> findByEventId (Long evnetId);

       boolean existsByEventIdAndCodeIgnoreCase(Long eventId , String code);
       boolean existsByEventIdAndCodeIgnoreCaseAndIdNot(Long eventId , String code , Long promotionId);
}
