package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    private void executeInTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void createUsersTable() {
        String HQL = "CREATE TABLE User (id bigint not null auto_increment, age tinyint, lastName varchar(255), name varchar(255), primary key (id))";
        executeInTransaction(session -> {
            Query query = session.createSQLQuery(HQL);
            query.executeUpdate();
        });
    }

    @Override
    public void dropUsersTable() {
        executeInTransaction(session -> {
            session.createSQLQuery("DroP TABLE IF EXISTS User").executeUpdate();
        });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executeInTransaction(session -> {
            session.persist(new User(name, lastName, age));
        });
    }

    @Override
    public void removeUserById(long id) {
        executeInTransaction(session -> {
            session.delete(session.get(User.class, id));
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("from User", User.class).list();
        } return users;
    }

    @Override
    public void cleanUsersTable() {
        executeInTransaction(session -> {
            session.createQuery("DELETE FROM User").executeUpdate();

        });
    }
}
