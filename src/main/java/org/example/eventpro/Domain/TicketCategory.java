package org.example.eventpro.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_categories")
@Getter
@Setter
@NoArgsConstructor
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @OneToMany(mappedBy = "category")
    private List<TicketCategoriesPerEvent> eventCategories = new ArrayList<>();
}
