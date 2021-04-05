package net.buzz.commands;

import net.buzz.OPMessage;
import net.buzz.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class Toggle extends Command {
    public OPMessage instance;

    public Toggle(OPMessage instance) {
        super("togglemsg", "", "tmsg", "togglemessage", "togglemessages", "tmsgs", "pmtoggle", "togglepm", "msgtoggle", "togglemsg", "msgt", "pmt");
        this.instance = instance;
    }

    public static List<ProxiedPlayer> toggle = new ArrayList<>();

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            if (sender.hasPermission("opcraft.member")) {
                ProxiedPlayer player = (ProxiedPlayer) sender;
                if (sender.hasPermission("opcraft.staff")) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.StaffToggle")));
                    return;
                } else if (toggle.contains(player)) {
                    toggle.remove(player);
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.Disabled")));
                    return;
                }
                toggle.add(player);
                player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.Enabled")));
                if (Message.messages.contains(player.getName())) {
                }
            } else {
                sender.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoPermission")));
            }
        } else {
            sender.sendMessage("This command can only be used in game!");
        }
    }
}
