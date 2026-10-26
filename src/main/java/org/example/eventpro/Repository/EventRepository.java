package org.example.eventpro.Repository;

import org.example.eventpro.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrganizerId(Long organizerId);
}
