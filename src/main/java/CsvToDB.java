import java.io.*;
import java.sql.*;

public class CsvToDB {

    public static void main(String[] args) {

        String jdbcURL = "jdbc:postgresql://${IP}:5432/";

        String username = "postgres";
        String userPassword = "postgres";
        String csvFilePath = "Accounts.csv";
        int numberOfAccounts = 100;

        String dbName = "accounts";
        String tableName = "account";
        String jdbcURLtoDB = jdbcURL + dbName;

        AccountGenerator.generateCSVorTXT(numberOfAccounts, csvFilePath);

        Connection connection;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, userPassword);
            connection.setAutoCommit(true);

            Database.CreateDatabaseIfNotExists(connection, dbName);

            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {

            connection = DriverManager.getConnection(jdbcURLtoDB, username, userPassword);
            connection.setAutoCommit(true);

            Statement statement = connection.createStatement();

            Table.DropTableIfExists(connection, tableName);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText;

            lineReader.readLine(); // skip header line

            statement.execute("create table " + tableName + "(account_id serial primary key, " +
                        "name_account varchar(50) not null, " +
                        "password varchar(50) not null unique, " +
                        "email varchar(50) not null unique)");

            String sql = "insert into " + tableName + " (name_account, password, email) values (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println("table " + tableName + " was created");

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String name_account = data[0];
                String password = data[1];
                String email = data[2];

                preparedStatement.setString(1, name_account);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);

                preparedStatement.execute();
                }

            lineReader.close();
            connection.close();

        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

   }
}
