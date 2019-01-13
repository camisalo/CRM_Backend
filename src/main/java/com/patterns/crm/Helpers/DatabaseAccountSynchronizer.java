package com.patterns.crm.Helpers;

import com.patterns.crm.api.Account;
import com.patterns.crm.api.IRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseAccountSynchronizer {
    private List<Account> insert;
    private List<Account> update;
    private List<Account> delete;


    public DatabaseAccountSynchronizer(List<Account> insert, List<Account> update, List<Account> delete) {
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public void execute() {
        System.out.println("Tablica: " + delete);
        if (!delete.isEmpty()) deleteFromDb();
        if (!insert.isEmpty()) insertIntoDb();

    }

    private void deleteFromDb() {
        String query = "DELETE FROM account WHERE id = ?";
        System.out.println("USUWANIE: " + delete);
        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            for (Account a : delete) {
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
        String query = "INSERT INTO account(name, address, phone, lastmodified) VALUES(?,?,?,?)";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            Account acc;
            for (Account a : insert) {
                acc = a;

                preparedStmt.setString(1, acc.getName());
                preparedStmt.setString(2, acc.getAddress());
                preparedStmt.setString(3, acc.getPhone());
                preparedStmt.setTimestamp(4, acc.getLastmodified());

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
        Account acc = new Account();
        try {
            String query = "SELECT * FROM account WHERE id = " + id;

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
