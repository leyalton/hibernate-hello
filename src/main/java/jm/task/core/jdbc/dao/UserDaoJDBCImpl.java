package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util.connection("CREATE TABLE IF NOT EXISTS users\n" +
                "(\n" +
                "    id int primary key auto_increment,\n" +
                "    name text,\n" + "    lastName text,\n" + "" +
                "    age tinytext\n" + ");");
    }

    public void dropUsersTable() {
        Util.connection("DROP TABLE IF EXISTS users");
    }

    public void saveUser(String name, String lastName, byte age) {
        Util.connection("INSERT INTO users (name, lastName, age) VALUES ('%s', '%s', %s)".formatted(name, lastName, age));
        System.out.println("User с именем – %s добавлен в базу данных".formatted(name));
    }

    public void removeUserById(long id) {
        Util.connection(String.format("DELETE FROM users WHERE id = %s", id));
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (ResultSet resultSet = Util.getConnection().createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        Util.connection("TRUNCATE TABLE users");
    }
}