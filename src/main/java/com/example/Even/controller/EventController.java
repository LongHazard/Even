package com.example.Even.controller;

import com.example.Even.model.Account;
import com.example.Even.model.Event;
import com.example.Even.payload.EventPayload;
import com.example.Even.payload.MessagePayload;
import com.example.Even.repository.AccountRepository;
import com.example.Even.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
//
//
    @PostMapping("create")
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return ResponseEntity.ok().body(eventRepository.save(event));
    }


    @PostMapping("addAttendeeInEvent")
    public ResponseEntity<?> addAttendeeInEvent(@RequestParam("idEvent") Long idEvent, @RequestBody Account account) {
            try {
                Optional<Event> eventOptional = eventRepository.findById(idEvent);
                if (eventOptional.isPresent()) {
                    Optional<Account> accountOptional = accountRepository.findById(account.getId());
                    if (accountOptional.isPresent()) {
                        Set<String> attendeeNameSetx = eventOptional.get().getAttendessName();
                        if (attendeeNameSetx.size() > 0) {
                            for (String attendeeName : attendeeNameSetx) {
                                if (attendeeName.equals(account.getUsername())) {
                                    return ResponseEntity.badRequest().build();
                                }
                            }
                        } else {
                            attendeeNameSetx = new HashSet<>(Arrays.asList(account.getUsername()));
                            eventOptional.get().setAttendessName(attendeeNameSetx);
                            eventRepository.save(eventOptional.get());
                        }
                    } else {
                        accountRepository.save(account);
                        Set<String> attendeeNameSetx = eventOptional.get().getAttendessName();
                        if (attendeeNameSetx.size() > 0) {
                            for (String attendeeName : attendeeNameSetx
                            ) {
                                if (attendeeName.equals(account.getUsername())) {
                                    return ResponseEntity.badRequest().build();

                                }
                            }

                        } else {
                            attendeeNameSetx = new HashSet<>(Arrays.asList(account.getUsername()));
                            eventOptional.get().setAttendessName(attendeeNameSetx);
                            eventRepository.save(eventOptional.get());
                        }
                    }

                } else{
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok().build();
            }catch (Exception e){
                return ResponseEntity.ok().body(new MessagePayload (e.getMessage()));
            }

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
    public ResponseEntity<Event> update(@RequestBody Event event) {
        Optional<Event> eventOptional = eventRepository.findById(event.getId());
        if (eventOptional.isPresent()) {
            List<Account> accounts = accountRepository.findByEvents(eventOptional.get());
            for (Account a : accounts) {
                eventOptional.get().setEvenName(event.getEvenName());
                eventOptional.get().setStartDate(event.getStartDate());
                eventOptional.get().setEndDate((event.getEndDate()));
                accountRepository.save(a);
            }
            return ResponseEntity.ok().body(eventRepository.save(eventOptional.get()));
        } else
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
        if (accountOptional.isPresent()) {
            Set<Event> events = accountOptional.get().getEvents();
            return ResponseEntity.ok().body(events);
        } else
            return ResponseEntity.badRequest().build();
    }


}
