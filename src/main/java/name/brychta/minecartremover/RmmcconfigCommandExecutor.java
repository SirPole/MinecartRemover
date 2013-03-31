package name.brychta.minecartremover;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RmmcconfigCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;

    public RmmcconfigCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        cs.sendMessage("Not supported yet.");
        return true;
    }
}
