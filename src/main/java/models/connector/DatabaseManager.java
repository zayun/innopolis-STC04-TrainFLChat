package models.connector;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс для работы с БД
 * dbcp
 * https://habrahabr.ru/post/101342/
 */
public class DatabaseManager {

    private static Logger logger = Logger.getLogger(DatabaseManager.class);

    private static Connection connection;


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

}
