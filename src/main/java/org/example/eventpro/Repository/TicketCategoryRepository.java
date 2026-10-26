package org.example.eventpro.Repository;

import org.example.eventpro.Domain.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory , Long> {
}
