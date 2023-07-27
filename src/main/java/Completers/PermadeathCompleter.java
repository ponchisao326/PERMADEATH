package Completers;

import org.bukkit.command.TabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PermadeathCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();

            if (sender.hasPermission("permadeath.reset")) {
                arguments.add("resetlives");
            }
            if (sender.hasPermission("permadeath.reload")) {
                arguments.add("reload");
            }
            if (sender.hasPermission("permadeath.daychange")) {
                arguments.add("resetlives");
            }
            
            arguments.add("info");
            arguments.add("help");
            arguments.add("remaining");
            arguments.add("dia");
            arguments.add("vidas");
            arguments.add("ticket");

            return arguments;
        }
        return null;
    }

}
