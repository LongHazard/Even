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
@Table(name = "event_data")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long id;
    private String evenName;
    private Long startDate;
    private Long endDate;
    @Column
    @ElementCollection(targetClass=String.class)
    private Set<String> attendessName;
//    @JsonIgnore
//    @ManyToMany(mappedBy = "setEvents",fetch = FetchType.LAZY ,cascade = {CascadeType.ALL})
//        private Set<Account> setAccounts = new HashSet<>();

}
