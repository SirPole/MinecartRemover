package name.brychta.minecartremover;

import org.bukkit.plugin.java.JavaPlugin;
//@author Martin Brychta [SirPole]
public class MinecartRemover extends JavaPlugin {

    /*
     * Actions, that are done, when plugin is loaded (usually start)
     */
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getCommand("rmmc").setExecutor(new RmmcCommandExecutor(this));
        getCommand("rmmcchest").setExecutor(new RmmcchestCommandExecutor(this));
        getCommand("rmmcconfig").setExecutor(new RmmcconfigCommandExecutor(this));
    }

    /*
     * Actions, that are done, when plugin is unloaded (usually shutdown)
     */
    @Override
    public void onDisable() {
    }
}