package com.patterns.crm.api;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface IRecordsFactory {

    //Abstract Factory --> wzorzec projektowy
    List<IRecords> list = new ArrayList<>();

    public void add(IRecords record);
    public void queryAll();
    public void queryById(int id);
    public List<IRecords> getAll();

}
