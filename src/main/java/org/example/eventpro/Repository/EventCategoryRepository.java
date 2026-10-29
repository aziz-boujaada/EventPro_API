package org.example.eventpro.Repository;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.TicketCategoriesPerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCategoryRepository extends JpaRepository<TicketCategoriesPerEvent, Long> {
    List<TicketCategoriesPerEvent> findByEventId(Long eventId);

    boolean existsByEventIdAndCategoryId(Long eventId, Long CategoryId);

    @Query("""
            SELECT coalesce(sum(rl.quantity), 0) from ReservationLine rl
              join Reservation r on rl.reservation.id = r.id
               where r.status IN ('CONFIRMED' , 'PENDING_PAYMENT')
                 and r.client.id = :clientId
                 and rl.categoryPerEvent.id = :eventCategoryId""")
    Integer sumQuantityByClientIdAndEventCategoryId(@Param("clientId") Long clientId, @Param("eventCategoryId") Long eventCategoryId);
}
