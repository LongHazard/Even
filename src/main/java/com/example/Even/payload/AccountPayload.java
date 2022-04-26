package com.example.Even.payload;

import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class AccountPayload {
    private String name;
    private String username;
    private String password;
    @ElementCollection
    private List<Long> evenlist;
}
