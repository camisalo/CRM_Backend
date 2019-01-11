package com.patterns.crm.Helpers;

import com.patterns.crm.api.Account;
import com.patterns.crm.api.IRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseAccountSynchronizer {
    private String table;
    private List<Account> insert;
    private List<Account> update;
    private List<Account> delete;


    public DatabaseAccountSynchronizer(String table, List<Account> insert, List<Account> update, List<Account> delete) {
        this.table = table;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public void execute() {
        insertIntoDb();
    }

    private void deleteFromDb() {
        String query = "DELETE FROM account WHERE id = ?";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            for (Account a : delete) {
                if (getRecordById(a.getId())) {
                    preparedStmt.setInt(1, a.getId());

                    preparedStmt.addBatch();
                    i++;
                    if (i % 1000 == 0 || i == insert.size()) {
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
            String query = "SELECT * FROM asset WHERE id = " + id;

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) return true;
            else return false;
//            acc.setId(result.getInt(1));
//            acc.setName(result.getString(2));
//            acc.setAddress(result.getString(3));
//            acc.setPhone(result.getString(4));
//            acc.setLastmodified(result.getTimestamp(5));
        } catch (Exception c){
            c.printStackTrace();
        }
        return false;
    }

}
