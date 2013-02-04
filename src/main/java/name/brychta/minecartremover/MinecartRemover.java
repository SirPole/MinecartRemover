package name.brychta.minecartremover;

import org.bukkit.plugin.java.JavaPlugin;

public class MinecartRemover extends JavaPlugin {
    public void onEnable() {
        this.saveDefaultConfig();
        getCommand("rmmc").setExecutor(new MinecraftRemoverCommandExecutor(this));
    }
    public void onDisable() {}
    
}