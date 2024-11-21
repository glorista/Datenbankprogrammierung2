package at.campus02.dbp2.repository;

import java.sql.*;

public class JdbcRepository implements CustomerRepository{

    private Connection connection;
    public JdbcRepository(String jdbcUrl) {
        try{
            connection = DriverManager.getConnection(jdbcUrl);
            ensureTable();
        }catch(SQLException e){
            throw new IllegalStateException("No database connection established", e);
        }
    }
    private void ensureTable() throws SQLException {
        boolean tableExists = connection.getMetaData().getTables(null,null,"CUSTOMER",null).next();
        if (!tableExists) {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE CUSTOMER (" +
                            "EMAIL varchar(50) primary key, " +
                            "LASTNAME varchar(50), " +
                            "FIRSTNAME varchar(50))"
            );
            statement.execute();
        }
    }

    @Override
    public void create(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO CUSTOMER VALUES (?,?,?)"
            );
            statement.setString(1, customer.getEmail());
            statement.setString(2, customer.getLastname());
            statement.setString(3, customer.getFirstname());
            statement.executeUpdate();

        }catch (SQLException e) {
            throw new IllegalStateException("Unable to insert customer", e);
        }
    }

    @Override
    public Customer read(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM CUSTOMER WHERE EMAIL = ?"
            );
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Customer fromDB = new Customer();
                fromDB.setEmail(resultSet.getString(1));
                fromDB.setLastname(resultSet.getString(2));
                fromDB.setFirstname(resultSet.getString(3));

                return fromDB;
            }else{
                return null;
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Unable to read customer", e);
        }

    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE CUSTOMER SET FIRSTNAME = ?," +
                            "LASTNAME = ?" +
                            "WHERE EMAIL = ?"
            );
            statement.setString(1,customer.getFirstname());
            statement.setString(2, customer.getLastname());
            statement.setString(3, customer.getEmail());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new IllegalStateException("Unable to update customer", e);
        }
    }

    @Override
    public void delete(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM CUSTOMER WHERE EMAIL = ?"
            );
                statement.setString(1, customer.getEmail());
                statement.execute(); //executeUpdate() nur bei insert und update
        }catch (SQLException e) {
            throw new IllegalStateException("Unable to delete customer", e);
        }

    }
}
