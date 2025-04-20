package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
        Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);

        log.info("connection1 : {}, class : {}", connection1, connection1.getClass());
        log.info("connection2 : {}, class : {}", connection2, connection2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        useDataSource(dataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();
        log.info("connection1 : {}, class : {}", connection1, connection1.getClass());
        log.info("connection2 : {}, class : {}", connection2, connection2.getClass());
    }

    @Test
    void dataSourceConnectionPool() throws InterruptedException, SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }
}
