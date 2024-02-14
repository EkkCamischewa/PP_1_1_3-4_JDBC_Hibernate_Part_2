package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final static String DELETE_TABLE = "DROP TABLE IF EXISTS users;";
    private final static String CREATE_TABLE = "DROP TABLE IF EXISTS users;\n" +
            "CREATE TABLE users(\n" +
            "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(80),\n" +
            "    lastname VARCHAR(100),\n" +
            "    age INT\n" +
            ");";
    private final static String INSERT_USER = "INSERT INTO users (name,last_name,age) VALUE (?,?,?);";


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

    }

    public void dropUsersTable() {


    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка добавления пользователя");;
        }
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
