package net.buzz.listeners;

import net.buzz.commands.Reply;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;

public class QuitEvent  implements Listener {

    public void PlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (Reply.replyHash.containsKey(player) || Reply.replyHash.containsValue(player.getName())) {
            ProxiedPlayer player2 = (ProxiedPlayer) Reply.replyHash.get(player);
            Reply.replyHash.remove(player);
            Reply.replyHash.remove(player2);
        }
    }
}