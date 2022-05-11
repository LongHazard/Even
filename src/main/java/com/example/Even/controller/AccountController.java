package com.example.Even.controller;

import com.example.Even.model.Account;
import com.example.Even.model.Event;
import com.example.Even.payload.AccountPayload;
import com.example.Even.payload.AccountPayload1;
import com.example.Even.repository.AccountRepository;
import com.example.Even.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    EventRepository eventRepository;
//
    @GetMapping("findAll")
    ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.ok().body(accountRepository.findAll());
    }

    @PostMapping("create1")
    public ResponseEntity<Account> create(@RequestBody Account account){
        return ResponseEntity.ok().body(accountRepository.save(account));
    }

    @PostMapping("create")
    ResponseEntity<Account> create(@RequestBody AccountPayload accountPayload) {
        Account account = new Account();
        account.setName(accountPayload.getName());
        account.setUsername(accountPayload.getUsername());
        account.setPassword((accountPayload.getPassword()));
        for (Long along : accountPayload.getEvenlist()) {
            Optional<Event> eventOptional = eventRepository.findById(along);
            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();
                // account.getSetEvents().add(event);
                account.getEvents().add(event);
            }
        }
        return ResponseEntity.ok().body(accountRepository.save(account));
    }




    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("idAccount") Long idAccount) {
        Optional<Account> accountOptional = accountRepository.findById(idAccount);
        if (accountOptional.isPresent()) {
            accountRepository.deleteById(idAccount);
            return ResponseEntity.ok().body(null);
        } else
            return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    ResponseEntity<Account> update(@RequestBody Account account){
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        if(accountOptional.isPresent()){
            accountOptional.get().setUsername(account.getUsername());
            accountOptional.get().setPassword(account.getPassword());
            accountOptional.get().setName(account.getName());
            return ResponseEntity.ok().body(accountRepository.save(accountOptional.get()));
        }else
            return ResponseEntity.notFound().build();

    }


}
