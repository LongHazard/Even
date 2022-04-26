package com.example.Even.controller;

import com.example.Even.model.Account;
import com.example.Even.model.Event;
import com.example.Even.repository.AccountRepository;
import com.example.Even.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("event")
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("findAll")
    public ResponseEntity<List<Event>> findAll() {
        return ResponseEntity.ok().body(eventRepository.findAll());
    }


    @PostMapping("create")
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return ResponseEntity.ok().body(eventRepository.save(event));
    }


    @DeleteMapping("delete")
    public ResponseEntity<?> delete1(@RequestParam("idEvent") Long idEvent) {
        Optional<Event> eventOptional = eventRepository.findById(idEvent);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(idEvent);
            return ResponseEntity.ok().body(null);
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("update")
    public ResponseEntity<Event> update(@RequestBody Event event){
        Optional<Event> eventOptional = eventRepository.findById(event.getId());
        if(eventOptional.isPresent()){
            List<Account> accounts = accountRepository.findByEvents(eventOptional.get());
            for(Account a : accounts){
                eventOptional.get().setEvenName(event.getEvenName());
                eventOptional.get().setStartDate(event.getStartDate());
                eventOptional.get().setEndDate((event.getEndDate()));
                accountRepository.save(a);
            }
            return ResponseEntity.ok().body(eventRepository.save(eventOptional.get()));
        }else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete/event")
    public ResponseEntity<?> delete(@RequestParam("idEvent") Long idEvent) {
        Optional<Event> eventOptional = eventRepository.findById(idEvent);
        if (eventOptional.isPresent()) {
            List<Account> accounts = accountRepository.findByEvents(eventOptional.get());
            for (Account acc : accounts) {
                Set<Event> events = acc.getEvents();
                events.remove(eventOptional.get());
                acc.setEvents(events);
                accountRepository.save(acc);
            }
            return ResponseEntity.ok().body(null);
        } else
            return ResponseEntity.badRequest().build();
    }


    @GetMapping("findById")
    public ResponseEntity<Set<Event>> findById(@RequestParam("idAccount") Long idAccount) {
        Optional<Account> accountOptional = accountRepository.findById(idAccount);
        if(accountOptional.isPresent()){
            Set<Event> events = accountOptional.get().getEvents();
            return ResponseEntity.ok().body(events);
        }
            else
                return ResponseEntity.badRequest().build();
    }
}
