package name.brychta.minecartremover;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
//@author Martin Brychta [SirPole]
public class MinecartRemover extends JavaPlugin {

    private static SQL sql;
    private Statement st;
    private ResultSet rs;

    /*
     * Actions, that are done, when plugin is loaded (usually start)
     */
    public void onEnable() {
        this.saveDefaultConfig();
        sql = new SQL(this);
        sql.getConnection();
        getServer().getPluginManager().registerEvents(new MRPlayerListener(this), this);
        getCommand("rmmc").setExecutor(new MinecraftRemoverCommandExecutor(this));
        if (this.getConfig().getBoolean("home")) {
            getCommand("home").setExecutor(new HomeCommandExecutor(this));
            getCommand("sethome").setExecutor(new SethomeCommandExecutor(this));
        }
        if (this.getConfig().getBoolean("spawn")) {
            getCommand("spawn").setExecutor(new SpawnCommandExecutor(this));
            getCommand("setspawn").setExecutor(new SetspawnCommandExecutor(this));
        }
    }

    /*
     * Actions, that are done, when plugin is unloaded (usually shutdown)
     */
    public void onDisable() {
        sql.close();
    }

    /*
     * Gets location of player's home
     * @param target - name of player
     * @return Location or null
     */
    public Location getHome(String target) {
        Location loc = null;
        try {
            st = sql.getConnection().createStatement();
            rs = st.executeQuery("SELECT * FROM homes WHERE player='" + target + "'");
            if (rs.next()) {
                loc = new Location(getServer().getWorld(rs.getString(2)), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getFloat(6), rs.getFloat(7));
                rs.close();
                st.close();
                return loc;
            }
        } catch (SQLException ex) {
            getLogger().severe(ex.getMessage());
        }
        return null;
    }

    /*
     * Gets locations of world's spawn
     * @param world - name of world
     * @return Location or null
     */
    public Location getSpawn(String world) {
        Location loc = null;
        try {
            st = sql.getConnection().createStatement();
            rs = st.executeQuery("SELECT * FROM spawns WHERE world='" + world + "'");
            if (rs.next()) {
                loc = new Location(getServer().getWorld(rs.getString(1)), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4), rs.getFloat(5), rs.getFloat(6));
                rs.close();
                st.close();
                return loc;
            } else {
                loc = getServer().getWorld(world).getSpawnLocation();
                return loc;
            }
        } catch (SQLException ex) {
            getLogger().severe(ex.getMessage());
        }
        return null;
    }

    /*
     * Sets home of player specified at location source is standing
     * @param target - target player
     * @param player - Location source
     * @return true if successful
     */
    public boolean setHome(String target, Player player) {
        Location loc = player.getLocation();
        try {
            st = sql.getConnection().createStatement();
            st.execute("INSERT OR REPLACE INTO homes VALUES('" + target + "', '" + loc.getWorld().getName() + "', " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch() + ")");
            st.close();
            return true;
        } catch (SQLException ex) {
            getLogger().severe(ex.getMessage());
        }
        return false;
    }

    /*
     * Sets spawn at location source is standing
     * @param player - Location source
     * @return true if successful
     */
    public boolean setSpawn(Player player) {
        Location loc = player.getLocation();
        try {
            st = sql.getConnection().createStatement();
            st.execute("INSERT OR REPLACE INTO spawns VALUES('" + loc.getWorld().getName() + "', " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch() + ")");
            st.close();
            return true;
        } catch (SQLException ex) {
            getLogger().severe(ex.getMessage());
        }
        return false;
    }
}