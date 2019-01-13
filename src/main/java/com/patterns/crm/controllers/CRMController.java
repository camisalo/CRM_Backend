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

//    @GetMapping(value = "/account/{id}", produces = "application/json")
//    public @ResponseBody ResponseEntity<List<IRecords>> getAccountById(@PathVariable int id){
//
//        AccountFactory factory = new AccountFactory();
//        factory.queryById(id);
//        List<IRecords> list = factory.getAll();
//
//        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        else return new ResponseEntity<>(list,HttpStatus.OK);
//    }

    @PostMapping(value = "/account", produces = "application/json")
    public @ResponseBody ResponseEntity syncAccount(@RequestBody List<Account> records){
        List<Account> toinsert = new ArrayList<>();
        List<Account> toupdate = new ArrayList<>();
        List<Account> todelete = new ArrayList<>();
        for (Account acc : records){
            System.out.println(acc.getId());
            if (acc.getState().equals("insert")) toinsert.add(acc);
            else if (acc.getState().equals("update")) toupdate.add(acc);
            else if (acc.getState().equals("delete")) todelete.add(acc);
        }

        DatabaseAccountSynchronizer synchronizer = new DatabaseAccountSynchronizer(toinsert, toupdate, todelete);
        synchronizer.execute();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/contact", produces = "application/json")
    public @ResponseBody ResponseEntity syncContact(@RequestBody List<Contact> records){
        List<Contact> toinsert = new ArrayList<>();
        List<Contact> toupdate = new ArrayList<>();
        List<Contact> todelete = new ArrayList<>();
        for (Contact con : records){
            System.out.println(con.getId());
            if (con.getState().equals("insert")) toinsert.add(con);
            else if (con.getState().equals("update")) toupdate.add(con);
            else if (con.getState().equals("delete")) todelete.add(con);
        }

        DatabaseContactSynchronizer synchronizer = new DatabaseContactSynchronizer(toinsert, toupdate, todelete);
        synchronizer.execute();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/asset", produces = "application/json")
    public @ResponseBody ResponseEntity syncAsset(@RequestBody List<Asset> records){
        List<Asset> toinsert = new ArrayList<>();
        List<Asset> toupdate = new ArrayList<>();
        List<Asset> todelete = new ArrayList<>();
        for (Asset acc : records){
            System.out.println(acc.getId());
            if (acc.getState().equals("insert")) toinsert.add(acc);
            else if (acc.getState().equals("update")) toupdate.add(acc);
            else if (acc.getState().equals("delete")) todelete.add(acc);
        }

        DatabaseAssetSynchronizer synchronizer = new DatabaseAssetSynchronizer(toinsert, toupdate, todelete);
        synchronizer.execute();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/opportunity", produces = "application/json")
    public @ResponseBody ResponseEntity syncOpportunity(@RequestBody List<Opportunity> records){
        List<Opportunity> toinsert = new ArrayList<>();
        List<Opportunity> toupdate = new ArrayList<>();
        List<Opportunity> todelete = new ArrayList<>();
        for (Opportunity acc : records){
            System.out.println(acc.getId());
            if (acc.getState().equals("insert")) toinsert.add(acc);
            else if (acc.getState().equals("update")) toupdate.add(acc);
            else if (acc.getState().equals("delete")) todelete.add(acc);
        }

        DatabaseOpportunitySynchronizer synchronizer = new DatabaseOpportunitySynchronizer(toinsert, toupdate, todelete);
        synchronizer.execute();
        return new ResponseEntity(HttpStatus.OK);
    }
}
