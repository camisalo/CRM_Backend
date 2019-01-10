package com.patterns.crm.api;

import com.patterns.crm.Helpers.CentralDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetFactory implements IRecordsFactory {
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
            String query = "SELECT * FROM asset";

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
            String query = "SELECT * FROM asset WHERE id = " + id;

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
            Asset acc = new Asset();
            acc.setId(resultSet.getInt(1));
            acc.setName(resultSet.getString(2));
            acc.setDescription(resultSet.getString(3));
            acc.setPrice(resultSet.getBigDecimal(4));
            acc.setAccountid(resultSet.getInt(5));
            acc.setLastmodified(resultSet.getTimestamp(5));

            list.add(acc);
        }
    }

    public void insertIntoDB() {
        String query = "INSERT INTO account(name, description, price, accountid, lastmodified) VALUES(?,?,?,?,?,?)";
        for (IRecords a : list) {
            Asset acc = (Asset) a;
            Connection conn = CentralDB.getDBconnextion();
            try {

                PreparedStatement preparedStmt  = conn.prepareStatement(query);
                preparedStmt .setString(1, acc.getName());
                preparedStmt .setString(2, acc.getDescription());
                preparedStmt .setBigDecimal(3, acc.getPrice());
                preparedStmt .setInt(4,acc.getAccountid());
                preparedStmt .setTimestamp(5,acc.getLastmodified());

                preparedStmt.execute();

            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }
}
