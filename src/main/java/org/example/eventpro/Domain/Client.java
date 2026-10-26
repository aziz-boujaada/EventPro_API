package org.example.eventpro.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    @Column(nullable = false, length = 20)
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations = new ArrayList<>();
}
