package com.patterns.crm.controllers;

import com.patterns.crm.api.*;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
//        else if (record.equals("asset")) factory = new AssetFactory();
//        else if (record.equals("opportunity")) factory = new OpportunityFactory();
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

//    // Contact
//    @GetMapping(value = "/contact", produces = "application/json")
//    public @ResponseBody ResponseEntity<List<IRecords>> getContacts(){
//
//        IRecordsFactory factory = new ContactFactory();
//        factory.queryAll();
//        List<IRecords> list = factory.getAll();
//
//        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        else return new ResponseEntity<>(list,HttpStatus.OK);
//    }

}
