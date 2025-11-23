package net.charno.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnectionHandler implements DBDriverInterface {

    final public static String ACCESS = "com.hxtt.sql.access.AccessDriver";
    final public static String ORACLE = "oracle.jdbc.driver.OracleDriver";
    final public static String MYSQL = "com.mysql.jdbc.Driver";
    private String _USER;
    private String _PASSWORD;
    private String _URL;
    private String _DRIVER;
    private Connection _connection;


    public DBConnectionHandler(final String usr, final String pass, final String url,
                               final String driver) {
        this._USER = usr;
        this._PASSWORD = pass;
        this._URL = url;
        this._DRIVER = driver;
    }

    public Connection connect() {
        try {
            Class.forName(_DRIVER);
            _connection = DriverManager.getConnection(_URL, _USER, _PASSWORD);
            System.out.println("Database Connection Established");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getMessage());
        } catch (ClassNotFoundException cnfEx) {
            System.out.println(cnfEx.getMessage());
        }

        return _connection;
    }

    public void disconnect() {
        try {
            if (_connection != null) {
                _connection.close();
                System.out.println("Database Connection Closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws NullPointerException {
        if (_connection == null) {
            throw new NullPointerException();
        }
        return _connection;
    }

    /**
     * process method is for pre handling code if any
     */
    public abstract void process();

    public void setAuto(final boolean auto) {
        try {
            _connection.setAutoCommit(auto);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
