package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
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

    private final static String GET_USERS = "SELECT * FROM users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");;
        }

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
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            long id = 0L;
            while (resultSet.next()){
                User userAdd = new User(resultSet.getString("name"),resultSet.getString("last_name"),resultSet.getByte("age"));
                userAdd.setId(++id);
                users.add(userAdd);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения информации");;
        }
        return users;
    }

    public void cleanUsersTable() {

    }
}
