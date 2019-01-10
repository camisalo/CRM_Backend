package com.patterns.crm.api;

import com.patterns.crm.Helpers.CentralDB;

import java.sql.*;
import java.util.List;

public class OpportunityFactory implements IRecordsFactory{
    ResultSet resultSet;

    @Override
    public void add(IRecords record) {
        list.add(record);
    }

    @Override
    public void queryAll() {
        Connection conn = CentralDB.getDBconnextion();
        try {
            String query = "SELECT * FROM opportunity";

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
            String query = "SELECT * FROM opportunity WHERE id = " + id;

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
            Opportunity acc = new Opportunity();
            acc.setId(resultSet.getInt(1));
            acc.setName(resultSet.getString(2));
            acc.setAmount(resultSet.getBigDecimal(3));
            acc.setOwner(resultSet.getInt(4));
            acc.setOpendate(resultSet.getDate(5));
            acc.setClosedate(resultSet.getDate(6));
            acc.setStage(resultSet.getString(7));
            acc.setAccountid(resultSet.getInt(8));
            acc.setLastmodified(resultSet.getTimestamp(9));

            list.add(acc);
        }
    }

    public void insertIntoDB() {
        String query = "INSERT INTO opportunity(name, amount, owner, opendate, closedate, stage, accountid, lastmodified) VALUES(?,?,?,?,?,?,?,?)";
        for (IRecords a : list) {
            Opportunity acc = (Opportunity) a;
            Connection conn = CentralDB.getDBconnextion();
            try {

                PreparedStatement preparedStmt  = conn.prepareStatement(query);
                preparedStmt .setString(1, acc.getName());
                preparedStmt .setBigDecimal(2, acc.getAmount());
                preparedStmt .setInt(3, acc.getOwner());
                preparedStmt .setDate(4,acc.getOpendate());
                preparedStmt .setDate(5,acc.getClosedate());
                preparedStmt .setString(6,acc.getStage());
                preparedStmt .setInt(7,acc.getAccountid());
                preparedStmt .setTimestamp(8,acc.getLastmodified());

                preparedStmt.execute();

            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }
}
