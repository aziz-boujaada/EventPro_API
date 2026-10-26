package org.example.eventpro.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizers")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Organizer extends User {

    @Column(nullable = false)
    private String organisationName;

    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();
}
