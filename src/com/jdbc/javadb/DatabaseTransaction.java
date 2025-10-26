package com.jdbc.javadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTransaction {
    public static void main(String[] args) {
        //!    1. Connect ke database
        /*harus ada name
         * harus ada password
         * harus ada URL*/
        String username = "postgres";
        String password = "22101706";
        String URL = "jdbc:postgresql://localhost:5433/java-db";


//!    2. Harus ingat Prinsip Database Transaction
        /*1.Begin
         * 2.Tidak boleh autocommit
         * 3.Kalau terjadi error dalam proses ekseskusi query -> Rollback*/
        try (Connection connection = DriverManager.getConnection(URL, username, password)) {
            try{
                //! Set autocomit false agar
                connection.setAutoCommit(false);
                //Multiple operation
                //1.Mengurangi saldo dari user pertama
                Statement stat = connection.createStatement();
                String updateZay= "UPDATE users SET amount = amount - 100000 WHERE ID = 8";
                //execute
                stat.executeUpdate(updateZay);
                //2.Menambahkan saldo pada user kedua dengan amont yang sama
                String updateRizal = "UPDATE users SET amount = amount +100000 WHERE ID =1";
                //execute
                stat.executeUpdate(updateRizal);

                //! Contoh gagal
                String kosongUpdate = "Update users SET amount = amount +100000 where column5";
                stat.executeUpdate(kosongUpdate);

                //3.commit
                connection.commit();
                System.out.println("Transfer berhasil");
            }catch(SQLException e){
                System.err.println("Error " + e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Cannot connect to database");
        }


    }

}
