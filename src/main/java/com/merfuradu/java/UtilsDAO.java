package com.merfuradu.java;

import java.sql.*;

import scala.collection.mutable.StringBuilder;

public class UtilsDAO {
    public static Connection c = null;

    public static void setConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connection established succesfully");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("SQLite JDBC Driver not found");
            cnfe.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return UtilsDAO.c;
    }

    public static void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String selectData(Connection c) throws SQLException {
        Statement statement = c.createStatement();
        String sqlCmd = "select * from CARS";

        ResultSet rs = statement.executeQuery(sqlCmd);
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append(rs.getInt("id"));
            sb.append(":");
            sb.append(rs.getString("PRODUCER"));
            sb.append(":");
            sb.append(rs.getFloat("PRICE"));
            sb.append(":");
            sb.append(rs.getFloat("WEIGHT"));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
