package org.example;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {

        insertPolaznika();
        insertProgramObrazovanja();
        insertUpis();
        prebaciPolaznikaUdrugiProgramObrazovanja();
    }
    public static void insertPolaznika() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String ime;
        String prezime;

        System.out.println("Unesi novog polaznika");
        System.out.println("Ime");
        ime = scanner.nextLine();
        System.out.println("Prezime");
        prezime = scanner.nextLine();

        Connection connection = DatabaseService.createConnection();
        CallableStatement callableStatement = null;

        String sql = "{call InsertPolaznik(?, ?)}";
        callableStatement = connection.prepareCall(sql);

        callableStatement.setString(1, ime);
        callableStatement.setString(2, prezime);

        callableStatement.executeUpdate();
    }

    public static void insertProgramObrazovanja() throws SQLException {
        Connection connection = DatabaseService.createConnection();
        Scanner scanner = new Scanner(System.in);
        PreparedStatement stmt1 = null;

        String naziv;
        int CSVET;

        String sqlForInsertProgramObrazovanja = "Insert into ProgramObrazovanja(Naziv, CSVET) VALUES (?,?)";

        stmt1 = connection.prepareStatement(sqlForInsertProgramObrazovanja);

        System.out.println("Unesi novi program obrazovanja");
        naziv = scanner.nextLine();
        stmt1.setString(1, naziv);
        System.out.println("Unesi CSVET");
        CSVET = scanner.nextInt();

        stmt1.setInt(2, CSVET);
        stmt1.executeUpdate();
        stmt1.close();
    }

    public static void insertUpis() {

        Scanner scanner1 = new Scanner(System.in);
        Connection connection = DatabaseService.createConnection();
        PreparedStatement stmt2 = null;

        String sqlForInsertUpis = "INSERT INTO Upis (IDProgramObrazovanja, IDPolaznik) VALUES (?, ?)";

        System.out.println("Upiši id obrazovanja na koji želiš upisati polaznika");
        int idObrazovanja = scanner1.nextInt();

        try {
            stmt2 = connection.prepareStatement(sqlForInsertUpis);

            stmt2.setInt(1, idObrazovanja);
            stmt2.setInt(2, findLastPolaznikId());

            stmt2.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int findLastPolaznikId() throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmt3 = null;
        int lastPolaznikId = 0;

        Connection connection = DatabaseService.createConnection();

        String sqlForFindingLastPolaznik = "SELECT PolaznikId FROM Polaznik WHERE PolaznikId = (SELECT MAX(PolaznikId) FROM Polaznik);";

        try {
            stmt3 = connection.prepareStatement(sqlForFindingLastPolaznik);

            resultSet = stmt3.executeQuery();

            if (resultSet.next()) {
                lastPolaznikId = resultSet.getInt("PolaznikId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return lastPolaznikId;
    }

    public static void prebaciPolaznikaUdrugiProgramObrazovanja() throws SQLException {
        PreparedStatement stmt4 = null;
        Connection connection = DatabaseService.createConnection();

        Scanner scanner = new Scanner(System.in);

        String sqlForUpdateUpis = "UPDATE UPIS SET IDProgramObrazovanja = ? where IDPolaznik = ?";
        try {
            connection.setAutoCommit(false);
            stmt4 = connection.prepareStatement(sqlForUpdateUpis);
            System.out.println("Unesi id obrazovanja u koji ga zelis prebaciti");
            int id = scanner.nextInt();
            stmt4.setInt(1, id);
            stmt4.setInt(2, findLastPolaznikId());
            stmt4.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }
}
