package com.example.Even.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "account_data")
public class Account {
    @Id
    @Column (name = "id_account")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany (fetch = FetchType.LAZY ,cascade = {CascadeType.ALL})
    @JoinTable(
            name = "account_event",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_event"))

    private Set<Event> events = new HashSet<>();
}
