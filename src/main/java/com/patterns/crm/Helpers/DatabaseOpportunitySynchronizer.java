package com.patterns.crm.Helpers;

import com.patterns.crm.api.Account;
import com.patterns.crm.api.Contact;
import com.patterns.crm.api.Opportunity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseOpportunitySynchronizer {
    private List<Opportunity> insert;
    private List<Opportunity> update;
    private List<Opportunity> delete;


    public DatabaseOpportunitySynchronizer(List<Opportunity> insert, List<Opportunity> update, List<Opportunity> delete) {
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public void execute() {
        System.out.println("Tablica: " + delete);
        if (!delete.isEmpty()) deleteFromDb();
        if (!update.isEmpty()) updateDb();
        if (!insert.isEmpty()) insertIntoDb();

    }

    private void deleteFromDb() {
        String query = "DELETE FROM opportunity WHERE id = ?";
        System.out.println("USUWANIE: " + delete);
        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            for (Opportunity a : delete) {
                if (getRecordById(a.getId())) {
                    preparedStmt.setInt(1, a.getId());

                    preparedStmt.addBatch();
                    i++;
                    if (i % 1000 == 0 || i == delete.size()) {
                        preparedStmt.executeBatch(); // Execute every 1000 items.
                    }
                }
            }
        } catch (Exception c) {
            c.printStackTrace();
        }

    }

    private void updateDb() {
        String query = "UPDATE opportunity SET name = ?" +
                "                       , amount = ?" +
                "                       , owner = ?" +
                "                       , opendate = ?" +
                "                       , closedate = ?" +
                "                       , stage = ?" +
                "                       , accountid = ?" +
                "                       , lastmodified = ?" +
                "WHERE id = ?";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            Opportunity acc;
            for (Opportunity a : update) {
                acc = a;
                if (getRecordById(a.getId())) {

                    preparedStmt.setString(1, acc.getName());
                    preparedStmt.setBigDecimal(2, acc.getAmount());
                    preparedStmt.setInt(3, acc.getOwner());
                    preparedStmt.setDate(4, acc.getOpendate());
                    preparedStmt.setDate(5, acc.getClosedate());
                    preparedStmt.setString(6, acc.getStage());
                    preparedStmt.setInt(7, acc.getAccountid());
                    preparedStmt.setTimestamp(8, acc.getLastmodified());
                    preparedStmt.setInt(9, acc.getId());

                    preparedStmt.addBatch();
                }
                i++;
                if (i % 1000 == 0 || i == update.size()) {
                    preparedStmt.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    private void insertIntoDb() {
        String query = "INSERT INTO opportunity (name, amount, owner, opendate, closedate, stage, accountid,lastmodified) VALUES(?,?,?,?,?,?,?,?)";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            Opportunity acc;
            for (Opportunity a : insert) {
                acc = a;

                preparedStmt.setString(1, acc.getName());
                preparedStmt.setBigDecimal(2, acc.getAmount());
                preparedStmt.setInt(3, acc.getOwner());
                preparedStmt.setDate(4, acc.getOpendate());
                preparedStmt.setDate(5, acc.getClosedate());
                preparedStmt.setString(6, acc.getStage());
                preparedStmt.setInt(7, acc.getAccountid());
                preparedStmt.setTimestamp(8, acc.getLastmodified());

                preparedStmt.addBatch();
                i++;
                if (i % 1000 == 0 || i == insert.size()) {
                    preparedStmt.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    private boolean getRecordById(Integer id) {
        Connection conn = CentralDB.getDBconnextion();
        Opportunity acc = new Opportunity();
        try {
            String query = "SELECT * FROM opportunity WHERE id = " + id;

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) return true;
            else return false;

        } catch (Exception c){
            c.printStackTrace();
        }
        return false;
    }
}
