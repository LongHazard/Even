package com.example.Even.payload;

import com.example.Even.model.Account;
import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Set;

@Data
public class EventPayload {
    private String evenName;
    private Long startDate;
    private Long endDate;
    private Set<String> attendessAccount;
//    @ElementCollection
//    private List<Long> accountList;

}
