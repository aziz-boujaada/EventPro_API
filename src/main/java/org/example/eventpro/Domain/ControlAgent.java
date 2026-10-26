package org.example.eventpro.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "control_agents")
@PrimaryKeyJoinColumn(name = "user_id")
@NoArgsConstructor
public class ControlAgent extends User {
}
