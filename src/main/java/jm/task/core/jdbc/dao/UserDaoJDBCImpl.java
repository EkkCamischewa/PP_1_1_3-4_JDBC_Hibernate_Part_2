package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    private final static String DELETE_TABLE = "DROP TABLE IF EXISTS users;";
    private final static String CREATE_TABLE = "CREATE TABLE users " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            " name VARCHAR(50), " +
            " last_name VARCHAR (50), " +
            " age INT " +
            ")";
    private final static String INSERT_USER = "INSERT INTO users (name,last_name,age) VALUE (?,?,?);";

    private final static String GET_USERS = "SELECT * FROM users";
    private final static String DELETE_USER = "DELETE FROM users WHERE id=?";
    private final static String CLEAR_TABLE = "DELETE FROM users";


    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        dropUsersTable();
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_TABLE);
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка добавления пользователя");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления User");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_USERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            long id = 0L;
            while (resultSet.next()) {
                User userAdd = new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                userAdd.setId(++id);
                users.add(userAdd);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения информации");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(CLEAR_TABLE)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка очистки таблицы");
        }
    }
}