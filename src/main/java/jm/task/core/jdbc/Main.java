package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args){
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("Иван1", "Иван1", (byte) 1);
        userDao.saveUser("Иван2", "Иван2", (byte) 2);
        userDao.saveUser("Иван3", "Иван3", (byte) 3);
        userDao.saveUser("Иван4", "Иван4", (byte) 4);

        System.out.print(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
