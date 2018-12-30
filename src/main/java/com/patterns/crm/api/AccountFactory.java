package com.patterns.crm.api;

import com.patterns.crm.Helpers.CentralDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountFactory implements IRecordsFactory {
    ResultSet resultSet;

    private List<IRecords> list = new ArrayList<>();


    @Override
    public void add(IRecords acc) {
        list.add(acc);
    }

    @Override
    public void queryAll() {
        Connection conn = CentralDB.getDBconnextion();
        try {
            String query = "SELECT * FROM account";

            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);

            addResoultToList();

        } catch (Exception c){
            c.printStackTrace();
        }
    }

    @Override
    public void queryById(int id) {
        Connection conn = CentralDB.getDBconnextion();
        try {
            String query = "SELECT * FROM account WHERE accountid = " + id;

            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);

            addResoultToList();

        } catch (Exception c){
            c.printStackTrace();
        }
    }

    @Override
    public List<IRecords> getAll() {
        return list;
    }

    private void addResoultToList() throws SQLException {
        while(resultSet.next()){
            Account acc = new Account();
            acc.setAccountid(resultSet.getInt(1));
            acc.setName(resultSet.getString(2));
            acc.setAddress(resultSet.getString(3));
            acc.setPhone(resultSet.getString(4));
            acc.setDate(resultSet.getTimestamp(5));

            list.add(acc);
        }
    }

    public void insertIntoDB() {
        String query = "INSERT INTO account(name, address, phone, lastmodified) VALUES(?,?,?,?)";
        for (IRecords a : list) {
            Account acc = (Account) a;
            Connection conn = CentralDB.getDBconnextion();
            try {

                PreparedStatement preparedStmt  = conn.prepareStatement(query);
                preparedStmt .setString(1, acc.getName());
                preparedStmt .setString(2, acc.getAddress());
                preparedStmt .setString(3, acc.getPhone());
                preparedStmt .setString(4,acc.getDate().toString());

                preparedStmt.execute();

            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }
}
