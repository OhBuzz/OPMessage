package net.buzz.commands;

import net.buzz.OPMessage;
import net.buzz.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Reload extends Command {
    private final OPMessage instance;

    public Reload(OPMessage instance) {
        super("msgreload");
        this.instance = instance;
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("opcraft.owner")) {
            this.instance.loadConfigurations();
            sender.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("reloadSuccess")));
        } else {
            if (!sender.hasPermission("opcraft.owner")) {
                sender.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoPermission")));
            }
        }
    }
}
