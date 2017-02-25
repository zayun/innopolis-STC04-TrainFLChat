package models.dao;

import models.connector.DatabaseManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
public abstract class AbstractDAO<T, K> {

    private static Logger logger = Logger.getLogger(AbstractDAO.class);

    private static Connection connection = DatabaseManager.getConnection();

    public abstract List<T> getAll();
    public abstract T update(T entity);
    public abstract T getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(T entity);

    /**Получаем экземпляр PreparedStatement
     * @return ps*/
    public static PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e);
        }

        return ps;
    }

    /**Закрываем полученный PreparedStatement
     *@param ps*/
    public static void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    /**Получаем экземпляр Statement
     * @return statement*/
    public static Statement getStatement() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error(e);
        }

        return statement;
    }

    /**Закрываем полученный PreparedStatement
     *@param statement*/
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }
}