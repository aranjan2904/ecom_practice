package com.ecommerce.main;

import java.sql.Connection;

import com.ecommerce.util.DBConnection;

public class EcommerceApplication {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Database Connected Successfully!");
        } else {
            System.out.println("Connection Failed!");
        }

    }
}