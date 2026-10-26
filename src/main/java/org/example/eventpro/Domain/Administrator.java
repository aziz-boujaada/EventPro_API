package org.example.eventpro.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@PrimaryKeyJoinColumn(name = "user_id")
@NoArgsConstructor
public class Administrator extends User {
}
