package com.patterns.crm.api;

import com.patterns.crm.Helpers.CentralDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactFactory implements IRecordsFactory {
    ResultSet resultSet;

    private List<IRecords> list = new ArrayList<>();

    @Override
    public void add(IRecords record) {
        list.add(record);
    }

    @Override
    public void queryAll() {
        Connection conn = CentralDB.getDBconnextion();
        try {
            String query = "SELECT * FROM contact";

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
            String query = "SELECT * FROM contact WHERE id = " + id;

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
            Contact acc = new Contact();
            acc.setId(resultSet.getInt(1));
            acc.setFirstname(resultSet.getString(2));
            acc.setLastname(resultSet.getString(3));
            acc.setBirthdate(resultSet.getDate(4));
            acc.setEmail(resultSet.getString(5));
            acc.setAccountid(resultSet.getInt(6));
            acc.setLastmodified(resultSet.getTimestamp(7));

            list.add(acc);
        }
    }

    public void insertIntoDB() {
        String query = "INSERT INTO contact(firstname, lastname, birthdate, email, accountid, lastmodified) VALUES(?,?,?,?,?,?)";
        for (IRecords a : list) {
            Contact acc = (Contact) a;
            Connection conn = CentralDB.getDBconnextion();
            try {

                PreparedStatement preparedStmt  = conn.prepareStatement(query);
                preparedStmt .setString(1, acc.getFirstname());
                preparedStmt .setString(2, acc.getLastname());
                preparedStmt .setDate(3, acc.getBirthdate());
                preparedStmt .setString(4, acc.getEmail());
                preparedStmt .setInt(5, acc.getAccountid());
                preparedStmt .setTimestamp(6,acc.getLastmodified());

                preparedStmt.execute();

            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }
}
