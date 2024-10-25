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
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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

    public Trainee(String firstName, String lastName, String username, String password, boolean isActive, String address, LocalDate dateOfBirth) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.user = new User(firstName, lastName, username, password, isActive);
    }

    //    public Trainee(long traineeId, String firstName, String lastName, String username, String password, boolean isActive, String address, LocalDate dateOfBirth) {
//        super(firstName, lastName, username, password, isActive);
//        this.traineeId = traineeId;
//        this.address = address;
//        this.dateOfBirth = dateOfBirth;
//    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Trainee{");
        sb.append("userId=").append(traineeId);
        sb.append(super.toString());
        sb.append(", address='").append(address).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append('}');
        return sb.toString();
    }
}
