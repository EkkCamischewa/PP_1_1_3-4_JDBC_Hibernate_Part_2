package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        dropUsersTable();
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                String CREATE_TABLE = "CREATE TABLE users " +
                        "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        " name VARCHAR(50), " +
                        " last_name VARCHAR (50), " +
                        " age INT " +
                        ")";
                session.createSQLQuery(CREATE_TABLE).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка создания таблицы");
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка удаления таблицы");
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                User user = new User(name, lastName, age);
                session.save(user);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка добавления пользователя");
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                session.delete(session.get(User.class,id));

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка удаления пользователя");
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                list = session.createQuery("from User").getResultList();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка получения всех пользователей");
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();

                session.createSQLQuery("DELETE FROM users").executeUpdate();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.err.println("Ошибка очистки таблицы");
            }
        }
    }
}
