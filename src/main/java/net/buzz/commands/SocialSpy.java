package net.buzz.commands;

import net.buzz.OPMessage;
import net.buzz.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialSpy extends Command {
    private final OPMessage instance;

    public SocialSpy(OPMessage instance) {
        super("socialspy");
        this.instance = instance;
    }

    public static HashMap<ProxiedPlayer, ProxiedPlayer> replyHash = new HashMap<>();

    public static List<ProxiedPlayer> spy = new ArrayList<>();

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (player.hasPermission("opcraft.staff")) {
                if (args.length == 0) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.Usage")));
                    return;
                }
                if (args[0].equalsIgnoreCase("off")) {
                    if (!spy.contains(player)) {
                        player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.AlreadyDisabled")));
                    } else if (spy.contains(player)) {
                        spy.remove(player);
                        player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.Disabled")));
                    }
                } else if (args[0].equalsIgnoreCase("on")) {
                    if (spy.contains(player)) {
                        player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.AlreadyEnabled")));
                        return;
                    }
                    spy.add(player);
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.Enabled")));
                }
            } else {
                player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoPermission")));
            }
        }
    }

    @EventHandler
    public void SocialSpyJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        try {
            if (player.hasPermission("opcraft.staff")) {
                spy.add(player);
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
    }
}
