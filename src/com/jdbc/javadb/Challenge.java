package com.jdbc.javadb;

import java.sql.*;

public class Challenge {
    public static void main(String[] args) {
        String username = "postgres";
        String password = "22101706";
        String URL = "jdbc:postgresql://localhost:5433/java-db";

        try(Connection connection = DriverManager.getConnection(URL, username, password)){
            System.out.println("Connected to database");
//            !kalau mau pakai commit / roll back
//            connection.setAutoCommit(false);

//             TODO:  create
            String insertQuery = "INSERT INTO cash_flow(id, type, description, amont, created_at) VALUES(?, ?, ?, ?, ?)";
//            Prepare stat untuk diisi value
            String insertQueryNoTime = "INSERT INTO cash_flow(id, type, description, amont) VALUES(?, ?, ?, ?)";
//             insert
            try(PreparedStatement insertPs = connection.prepareStatement(insertQuery)){
//                set value
                insertPs.setInt(1, 5);
                insertPs.setString(2, "Expense");
                insertPs.setString(3, "Freelance");
//                Date/Timestamp
//                java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

                // tanggal manual
//                String dateString = "2025-09-09";
//                java.sql.Date sqlDate = java.sql.Date.valueOf(dateString); // konversi
                // tanggal dan waktu
                String timeStamp = "2025-09-09 09:09:09";
                java.sql.Timestamp sqlTimeStamp = java.sql.Timestamp.valueOf(timeStamp);
                insertPs.setFloat(4, 8_000_000);
                insertPs.setTimestamp(5, sqlTimeStamp);


//                execute query isertPs (by executedUpdate)
                int rowAffectedUpdate = insertPs.executeUpdate();
                System.out.println("Inserted data " + rowAffectedUpdate + " rows");
//                !commit kalau berhasil
//                connection.commit();
            } catch (SQLException e){
//                !rollback kalau error
//                connection.rollback();
                System.err.println("failed to add data");
                System.err.println(e.getMessage());
            }

//             TODO : read
            String readQuery = "SELECT * from cash_flow";
            try(PreparedStatement readPs = connection.prepareStatement(readQuery);
                ResultSet resultRead = readPs.executeQuery();
            ){
                while (resultRead.next()){
                    String id = resultRead.getString("id");
                    String type = resultRead.getString("type");
                    String desc = resultRead.getString("description");
                    String amount = resultRead.getString("amont");
                    Date created_at = resultRead.getDate("created_at");

                    System.out.println("id : " +id+ ", type : " + type +
                            ", description : "+ desc+ ", amount : " + amount +
                            ", dibuat : " + created_at
                            );
                }
            } catch (SQLException e){
                System.err.println("cannot read the data");
            }
//            TODO: readById
            String readById = "SELECT id, type, description, amont, created_at FROM cash_flow WHERE id = ?";
            try(PreparedStatement readId = connection.prepareStatement(readById);
            ){
                // id yang dicari : id 4
                readId.setInt(1, 4);

                try(ResultSet resultById = readId.executeQuery()){
                    // cek apakah idnya ada
                    if(resultById.next()){
                        String id = resultById.getString("id");
                        String type = resultById.getString("type");
                        String desc = resultById.getString("description");
                        String amount = resultById.getString("amont");
                        Date created_at = resultById.getDate("created_at");

                        System.out.println("=====[ID FOUND]=====");
                        System.out.println("id : " +id+ ", type : " + type +
                                ", description : "+ desc+ ", amount : " + amount +
                                ", dibuat : " + created_at
                        );
                    } else {
                        System.out.println("id not found");
                    }
                } catch (SQLException e){
                    System.err.println(e.getMessage());
                    System.err.println("id not found");
                }
            }

//            TODO: update
            // case 1 update type dan description
            String updateQueryDnT = "UPDATE cash_flow SET description = ?, created_at = ? WHERE id = ?";
            String updateQuery = "UPDATE cash_flow SET type =?, description = ? WHERE id = ?";
            try(PreparedStatement updatePs = connection.prepareStatement(updateQueryDnT)) {
//                isi value
                updatePs.setString(1, "Investasi");
                // update tanggal baru
//                java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
                // tanggal dan jam
//                java.sql.Timestamp sqlTime = new java.sql.Timestamp(System.currentTimeMillis());
                // update tanggal manual
//                String dateUpdate = "2025-08-08";
//                java.sql.Date sqlDate = java.sql.Date.valueOf(dateUpdate);
//                updatePs.setDate(2, sqlDate);
//                !Date and time
                String timeUpdate = "2025-08-08 08:08:08";
                java.sql.Timestamp sqlTime = java.sql.Timestamp.valueOf(timeUpdate);
                updatePs.setTimestamp(2, sqlTime);
                updatePs.setInt(3, 5);
                //isi value yang ingin diganti
//                updatePs.setString(1, "Income");
//                updatePs.setString(2, "Gaji Freelance");
//                updatePs.setInt(3,3); // (updateQuery: ?, targetId yang di ganti : ? )

                //execute / jalankan perintah update
                int rowAffected = updatePs.executeUpdate();
                if(rowAffected > 0){
                    System.out.println("Data berhasil di update " + rowAffected +" row");
//                    !commit kalau berahsil
//                    connection.commit();
                } else {
                    System.err.println("cannot update data, Id not found");
//                    !rollback kalau gagal
//                    connection.rollback();
                }
            }catch (SQLException e){
//                connection.rollback();
                System.out.println("Failed to update");
                System.out.println(e.getMessage());
            }


//            TODO: delete
            String deleteQuery = "DELETE from cash_flow WHERE id =?";
            try(PreparedStatement deletPs = connection.prepareStatement(deleteQuery)){
                deletPs.setInt(1, 5); // hapus id 5

                int rowAffected = deletPs.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println(rowAffected + " row has been deleted");
//                    connection.commit();
                } else {
                    System.err.println("Failed to delete, id not found");
//                    connection.rollback();
                }
            }catch (SQLException e){
//                connection.rollback();
                System.err.println("Id not found , cannot delete");
                System.out.println(e.getMessage());
            }

//! ==================== [eot]
        }catch (SQLException e) {
            System.out.println("Cannot connect to database");
            System.err.println(e.getMessage());
        }
    }
}
