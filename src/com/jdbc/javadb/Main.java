package com.jdbc.javadb;
//        Begin     -> Memulai proses yang kita inginkan
//        Commit    -> Kita lakukan , ketika semua transaksi yang kita lakukan sudah berhasil
//        Rollback  -> Mengembalikan kondisi seperti semula seperti sebelum transaksi
import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        1. Connect
        String username = "postgres";
        String password = "22101706";
        String url = "jdbc:postgresql://localhost:5433/java-db";

        // try(){} try with resource -> biar otomatis ke close ketika sudah tidak digunakan
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            System.out.println("Connected to the database");
            //        2.Bikin querry
            // CRUD
            // - masuk dalam postgrsql : psql -U postgres (username)
            // - berikan perintah \l untuk menampilkan list database yang ada
            // - berikan perintah \c <nama_db> untuk terkoneksi dengan database tertentu
            // - berikan perintah \d untuk menampilkan table yang ada
            // - create table baru : CREATE TABLE nama_table(id INT/SERIAL PRIMARY KEY, name VARCHAR(50), amount FLOAT);

            // Jenis query yang bisa dipakai :
            // -native query -> SLECT * from users WHERE id =1;
            // dapat menyebabkan SQL injection
            // - bisa menggunakan preparedStatement -> untuk menhindari sql injection
            // --> SELECT * from users WHERE id = ?, id;
            // ketika tidak ada inputan dari users kita bisa menggunakan native query

//        a. create
            String insertQuery = "INSERT INTO users(id, name, amount) VALUES (?, ?, ?)";

            //try with resource
            try(
                    PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                    ) {
                //data 1
//                insertPs.setInt(1, 1);
//                insertPs.setString(2, "Rizal");
//                insertPs.setInt(3, 100);
                // data selanjutnya
                insertPs.setInt(1, 9);
                insertPs.setString(2, "Zay");
                insertPs.setInt(3, 1000_000_000);

                // mendapatkan rows yang terdampak  perubahan
                // berupa DML
               int rowsAffected = insertPs.executeUpdate();
                System.out.println("Inserted date " + rowsAffected + " rows");

//                harus select ulang (MENGGUNAKAN SELECT , TIDAK BISA KALO INSERT)
//                try (
//                        ResultSet result = insertPs.executeQuery()
//                        ){
//                    if(result.next()){
//                        System.out.println(
//                                "Inserted data : "+
//                                        result.getInt("id") +
//                                        result.getString("name") +
//                                        result.getFloat("amount")
//                        );
//                    }
//                }

            }
            // boleh tidak ada catch karena udah ke handle ooleh exception yang dibawah
            // tapi boleh ditambahkan juga
            catch (SQLException e) {
                System.err.println(e.getMessage());
                System.err.println("failed to add data.");
            }

            //        2. Read (read all)
//            -Deklarasi sebuah variable string
            String readQuery = "SELECT * FROM users ";
            try(  PreparedStatement readPs = connection.prepareStatement(readQuery);
                  ResultSet resultRead = readPs.executeQuery();
            ){
                while (resultRead.next()){
                    String id = resultRead.getString("id");
                    String name = resultRead.getString("name");
                    Float amount = resultRead.getFloat("amount");
                    // karena gua kepanjangan masukin datanya pake long baru bisa tampil
                    // Long amount = reultRead.getLong("amount);

                    System.out.println("id : " + id + " name : " + name + " amount :" + amount);
//                    System.out.println(
//                                "Inserted data : "+
//                                        resultRead.getInt("id") +
//                                        resultRead.getString("name") +
//                                        resultRead.getFloat("amount")
//                        );
                }

            }catch (SQLException e){
                System.err.println(e.getMessage());
                System.err.println("Read All data failed");
            }
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
            System.err.println("connection failed");
        }











    }
}


