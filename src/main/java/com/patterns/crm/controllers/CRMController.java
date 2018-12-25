package com.patterns.crm.controllers;

import com.patterns.crm.api.Account;
import com.patterns.crm.api.AccountFactory;
import com.patterns.crm.api.IRecords;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/crm")
public class CRMController {

    @GetMapping(value = "/account", produces = "application/json")
    public @ResponseBody ResponseEntity<List<IRecords>> getAccounts(){

        AccountFactory factory = new AccountFactory();
        factory.queryAll();
        List<IRecords> list = factory.getAll();

        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping(value = "/account/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<IRecords>> getAccountById(@PathVariable int id){

        AccountFactory factory = new AccountFactory();
        factory.queryById(id);
        List<IRecords> list = factory.getAll();

        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public @ResponseBody ResponseEntity addAccounts(@RequestBody List<Account> acc){

        if (acc.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        else if (!acc.isEmpty()) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/account", produces = "application/json")
    public @ResponseBody ResponseEntity addAccount(@RequestBody Account acc){


        AccountFactory accfac = new AccountFactory();
        accfac.add(acc);
        accfac.insertIntoDB();

        return new ResponseEntity(HttpStatus.OK);

    }
}
