package net.buzz.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabComplete implements Listener {
    @EventHandler
    public void BungeeTabComplete(TabCompleteEvent event) {
        String[] args = event.getCursor().toLowerCase().split(" ");
        if ((args.length >= 1 && args[0].startsWith("/") &&
                args.length > 1 && args[0].equalsIgnoreCase("/tell")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/say")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/message")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/t")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/w")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/ew")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/ewhisper")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/emsg")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/emessage")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/et")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/etell")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/m")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/whisper")) || (
                args.length > 1 && args[0].equalsIgnoreCase("/msg"))) {
            String check = args[args.length - 1];
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(check))
                    event.getSuggestions().add(player.getName());
            }
        }
    }
}
