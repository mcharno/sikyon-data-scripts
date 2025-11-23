package net.charno.db;

import java.sql.Connection;

public interface DBDriverInterface {
    Connection connect();
    void disconnect();
}
