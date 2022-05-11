package com.example.Even.repository;

import com.example.Even.model.Account;
import com.example.Even.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
  List<Account> findByEvents(Event event);







}
