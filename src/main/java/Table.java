import java.sql.*;

public class Table {

    static boolean Exists(Connection connection, String tableName) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    static void DropTableIfExists(Connection connection, String tableName) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        if(resultSet.getInt(1) != 0){
            Statement statement = connection.createStatement();
            statement.execute("drop table " + tableName);
            System.out.println("table " + tableName + " was dropped");

        }
    }

}
