package arc.haldun.hurda.database;

import cc.carm.lib.easysql.hikari.HikariConfig;
import cc.carm.lib.easysql.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

    private static HikariDataSource dataSource;

    public static void connect(DatabaseConfig databaseConfig) {
        connect(
                databaseConfig.getDatabaseAddress(),
                databaseConfig.getDatabaseName(),
                databaseConfig.getUserName(),
                databaseConfig.getPassword()
        );
    }

    public static void connect(String databaseAddress, String databaseName, String userName, String password) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String connectionURL = String.format(
                "jdbc:mysql://%s/%s?characterEncoding=UTF-8",
                databaseAddress,
                databaseName
        );

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(connectionURL);
        config.setUsername(userName);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMaxLifetime(600000);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static void printHikariStats() {
        System.out.println();
        System.out.println("Toplam bağlantı: " + dataSource.getHikariPoolMXBean().getTotalConnections());
        System.out.println("Aktif bağlantı: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Boşta bekleyen bağlantı: " + dataSource.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Bekleyen istekler: " + dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        System.out.println();
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean isValid() {

        try (Statement statement = getConnection().createStatement()) {
            return getConnection() != null && !getConnection().isClosed() && statement.execute("SELECT 1");
        } catch (SQLException e) {
            return false;
        }
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
