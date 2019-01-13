package com.patterns.crm.Helpers;

import com.patterns.crm.api.Account;
import com.patterns.crm.api.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseContactSynchronizer {
    private List<Contact> insert;
    private List<Contact> update;
    private List<Contact> delete;


    public DatabaseContactSynchronizer(List<Contact> insert, List<Contact> update, List<Contact> delete) {
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public void execute() {
        if (!delete.isEmpty()) deleteFromDb();
        if (!insert.isEmpty()) insertIntoDb();

    }

    private void deleteFromDb() {
        String query = "DELETE FROM contact WHERE id = ?";
        System.out.println("USUWANIE: " + delete);
        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            for (Contact a : delete) {
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

    }

    private void insertIntoDb() {
        String query = "INSERT INTO contact(firstname, lastname, birthdate, email, accountid, lastmodified) VALUES(?,?,?,?,?,?)";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            Contact acc;
            for (Contact a : insert) {
                acc = a;
                preparedStmt.setString(1, acc.getFirstname());
                preparedStmt.setString(2, acc.getLastname());
                preparedStmt.setDate(3, acc.getBirthdate());
                preparedStmt.setString(4, acc.getEmail());
                preparedStmt.setInt(5, acc.getAccountid());
                preparedStmt.setTimestamp(6, acc.getLastmodified());

                preparedStmt.addBatch();
                i++;
                if (i % 1000 == 0 || i == insert.size()) {
                    preparedStmt.executeBatch();
                }
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    private boolean getRecordById(Integer id) {
        Connection conn = CentralDB.getDBconnextion();
        Contact acc = new Contact();
        try {
            String query = "SELECT * FROM contact WHERE id = " + id;

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
