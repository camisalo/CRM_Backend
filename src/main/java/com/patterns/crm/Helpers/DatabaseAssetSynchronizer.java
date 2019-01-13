package com.patterns.crm.Helpers;

import com.patterns.crm.api.Asset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseAssetSynchronizer {
    private List<Asset> insert;
    private List<Asset> update;
    private List<Asset> delete;


    public DatabaseAssetSynchronizer(List<Asset> insert, List<Asset> update, List<Asset> delete) {
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
        String query = "DELETE FROM asset WHERE id = ?";
        System.out.println("USUWANIE: " + delete);
        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            for (Asset a : delete) {
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
        String query = "INSERT INTO asset(name, description, price, accountid, lastmodified) VALUES(?,?,?,?,?)";

        try {
            Connection conn = CentralDB.getDBconnextion();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int i=0;
            Asset acc;
            for (Asset a : insert) {
                acc = a;

                preparedStmt.setString(1, acc.getName());
                preparedStmt.setString(2, acc.getDescription());
                preparedStmt.setBigDecimal(3, acc.getPrice());
                preparedStmt.setInt(4, acc.getAccountid());
                preparedStmt.setTimestamp(5, acc.getLastmodified());

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
        Asset acc = new Asset();
        try {
            String query = "SELECT * FROM asset WHERE id = " + id;

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
