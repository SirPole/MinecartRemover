package name.brychta.minecartremover;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.plugin.Plugin;
//@author Martin Brychta [SirPole]
public class SQL {

    Plugin plg = null;
    Connection conn = null;
    Statement st = null;
    File db = null;

    public SQL(Plugin instance) {
        plg = instance;
        db = new File(plg.getDataFolder() + File.separator + plg.getName() + ".db");
    }
    
    /*
     * Opens SQL connection and creates tables if not already created
     * @return Connection if successful
     */
    public synchronized Connection open() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
            st = conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS homes(player text NOT NULL, world text NOT NULL, x double NOT NULL, y double NOT NULL, z double NOT NULL, yaw double, pitch double, PRIMARY KEY(player))");
            st.execute("CREATE TABLE IF NOT EXISTS spawns(world text NOT NULL, x double NOT NULL, y double NOT NULL, z double NOT NULL, yaw double, pitch double, PRIMARY KEY(world))");
            return conn;
        } catch (SQLException | ClassNotFoundException ex) {
            plg.getLogger().severe(ex.getMessage());
            return null;
        }
    }
    
    /*
     * Gets state of connection
     * @return if open, returns pointer, else opens new one
     */
    public synchronized Connection getConnection() {
        if (conn == null) {
            return open();
        }
        return conn;
    }
    
    /*
     * Closes connection if opened
     */
    public synchronized void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                plg.getLogger().severe(ex.getMessage());
            }
        }
    }
}
