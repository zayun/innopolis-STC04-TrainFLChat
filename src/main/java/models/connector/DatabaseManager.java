package models.connector;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс для работы с БД
 * dbcp
 * https://habrahabr.ru/post/101342/
 */
public class DatabaseManager {

    private static Logger logger = Logger.getLogger(DatabaseManager.class);

    private static Connection connection = getConnection();

    /**
     * Инициализация подключения к БД
     * устанавливает
     * @throws NamingException - ошибка доступа к конф файлу
     * @throws SQLException    - при ошибке подключения к БД
     * @see DatabaseManager#connection
     */
    private static void initDatabase() {

        try {

            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/LFLChat");
            connection = ds.getConnection();

        } catch (NamingException e) {
            logger.error(e);
        } catch (SQLException e) {
            logger.error(e);
        }

    }

    /** Возвращает подключение к БД
    * @return connection
    */
    public static Connection getConnection() {
        if (connection == null) {
            initDatabase();
        }
        return connection;
    }

    /**
     * Получаем экземпляр PreparedStatement
     *
     * @return ps
     */
    public static PreparedStatement getPrepareStatement(String sql) {

        if (connection == null) return null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e);
        }

        return ps;
    }

    /**
     * Закрываем полученный PreparedStatement
     *
     * @param ps
     */
    public static void closePrepareStatement(PreparedStatement ps) {

        if (connection == null) return;

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Получаем экземпляр Statement
     *
     * @return statement
     */
    public static Statement getStatement() {

        if (connection == null) return null;

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error(e);
        }

        return statement;
    }

    /**
     * Закрываем полученный Statement
     *
     * @param statement
     */
    public static void closeStatement(Statement statement) {

        if (connection == null) return;

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

}
