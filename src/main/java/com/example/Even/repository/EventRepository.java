package com.example.Even.repository;

import com.example.Even.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
   List<String> findByAttendessName(String username);
}
