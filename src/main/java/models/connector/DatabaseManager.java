package models.connector;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс для работы с БД
 * Singleton
 */
public class DatabaseManager {

    private static Logger logger = Logger.getLogger(DatabaseManager.class);

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/LFLChat";
    private static final String DB_LOGIN = "postgres";
    private static final String DB_PASSWORD = "123456";

    private static Connection connection;

    private DatabaseManager() {
    }

    /**
     * Инициализация подключения к БД
     * устанавливает
     * @throws ClassNotFoundException - при не загруженном драйвере
     * @throws SQLException           - при ошибке подключения к БД
     * @see DatabaseManager#connection
     */
    private static void initDatabase() {

        try {

            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);

            logger.trace("Successful connect to base: " + DB_URL);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Возвращает подключение к БД
     * @return connection
     */
    public static Connection getConnection() {
        if (connection == null) {
            initDatabase();
        }
        return connection;
    }

    /**
     * Закрывает подключение к БД
     */
    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();
            logger.trace("Connection was closed");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
