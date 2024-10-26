package org.example.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Trainee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long traineeId;
    @Nullable
    private String address;
    @Nullable
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    @ManyToMany
    @JoinTable(name = "trainee2trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @org.hibernate.annotations.OrderBy(clause = "trainingDate DESC")
    private Set<Training> trainings = new HashSet<>();

    public Trainee() {
    }

    public Trainee(User user, String address, LocalDate dateOfBirth) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }
}
