package com.patterns.crm.controllers;

//import com.patterns.crm.Helpers.DatabaseAccountSynchronizer;
import com.patterns.crm.api.*;
import com.patterns.crm.Helpers.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/crm")
public class CRMController {

    // Account
    @GetMapping(value = "/{record}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<IRecords>> getAccounts(@PathVariable String record){

        IRecordsFactory factory;
        if (record.equals("account")) factory = new AccountFactory();
        else if (record.equals("contact")) factory = new ContactFactory();
        else if (record.equals("asset")) factory = new AssetFactory();
        else if (record.equals("opportunity")) factory = new OpportunityFactory();
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

    @PostMapping(value = "/account", produces = "application/json")
    public @ResponseBody ResponseEntity addAccount(@RequestBody List<Account> records){

        if (records.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        List<Account> toinsert = new ArrayList<>();
        List<Account> toupdate = new ArrayList<>();
        List<Account> todelete = new ArrayList<>();
        for (Account acc : records){
            if (acc.getState().equals("insert")) toinsert.add(acc);
            else if (acc.getState().equals("update")) toupdate.add(acc);
            else if (acc.getState().equals("delete")) todelete.add(acc);
            else System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        DatabaseAccountSynchronizer synchronizer = new DatabaseAccountSynchronizer("account", toinsert, toupdate, todelete);
        synchronizer.execute();
        return new ResponseEntity(HttpStatus.OK);
    }
}
