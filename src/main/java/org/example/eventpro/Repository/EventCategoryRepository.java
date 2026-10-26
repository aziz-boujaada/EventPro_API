package org.example.eventpro.Repository;

import org.example.eventpro.Domain.TicketCategoriesPerEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<TicketCategoriesPerEvent , Long > {
}
