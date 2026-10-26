package org.example.eventpro.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_categories_per_event", uniqueConstraints =
        @UniqueConstraint(columnNames = {"event_id", "category_id"}))
@Getter
@Setter
@NoArgsConstructor
public class TicketCategoriesPerEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private TicketCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer maxQuotaPerClient;

    @OneToMany(mappedBy = "categoryPerEvent")
    private List<ReservationLine> reservationLines = new ArrayList<>();
}
