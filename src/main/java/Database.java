import java.sql.*;

public class Database {

    static void CreateDatabaseIfNotExists(Connection connection, String dbName) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from pg_catalog.pg_database where datname = ? limit 1");
        preparedStatement.setString(1, dbName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        if(resultSet.getInt(1) == 0){

            Statement statement = connection.createStatement();
            statement.execute("create database " + dbName);
            System.out.println("database " + dbName + " was created");
        }

        else{
            System.out.println("database " + dbName + " already exists");
        }
    }
}
