package net.buzz.commands;

import net.buzz.OPMessage;
import net.buzz.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;

public class Reply extends Command {
    public OPMessage instance;

    public Reply(OPMessage instance) {
        super("reply", "", "r", "er", "ereply");
        this.instance = instance;
    }

    public static HashMap<ProxiedPlayer, ProxiedPlayer> replyHash = new HashMap<>();

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            if (player.hasPermission("opcraft.member")) {
                if (replyHash.containsKey(player)) {
                    ProxiedPlayer player2 = replyHash.get(player);
                    if (replyHash.containsValue(player2)) {
                        if (player2 != null) {
                            if (args.length >= 1) {
                                if (Toggle.toggle.contains(player2)) {
                                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.OtherDisabled")
                                            .replace("{player}", player2.getName())));
                                    replyHash.remove(player);
                                    replyHash.remove(player2);
                                    return;
                                }
                                if (Toggle.toggle.contains(player)) {
                                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.SelfDisabled")));
                                    return;
                                }
                                String message = "";
                                for (int i = 0; i < args.length; i++)
                                    message = String.valueOf(String.valueOf(String.valueOf(message))) + args[i] + " ";
                                String senderServer = player.getServer().getInfo().getName();
                                String receiverServer = player2.getServer().getInfo().getName();
                                String senderFormat = ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.SenderFormat")
                                        .replace("{message}", message).replace("{sendername}", player.getName())
                                        .replace("{senderserver}", senderServer).replace("{receiverserver}", receiverServer)
                                        .replace("{receivername}", player2.getName()));
                                String receiverFormat = ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.ReceiverFormat")
                                        .replace("{message}", message).replace("{sendername}", player.getName())
                                        .replace("{senderserver}", senderServer).replace("{receiverserver}", receiverServer)
                                        .replace("{receivername}", player2.getName()));
                                player.sendMessage(senderFormat);
                                player2.sendMessage(receiverFormat);
                                for (ProxiedPlayer staff : OPMessage.getInstance().getProxy().getPlayers()) {
                                    if (player2.getName().equalsIgnoreCase(this.instance.getMainConfiguration().getString("{receiver}"))) {
                                        return;
                                    }
                                    if (SocialSpy.spy.contains(staff)) {
                                        String format = ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.Format")
                                                .replace("{sender}", player.getName())
                                                .replace("{receiver}", player2.getName()).replace("{message}", message)
                                                .replace("{senderserver}", senderServer));
                                        staff.sendMessage(format);
                                    }
                                }
                                if (replyHash.containsKey(player) || replyHash.containsKey(player2)) {
                                    replyHash.remove(player);
                                    replyHash.remove(player2);
                                    replyHash.put(player, player2);
                                    replyHash.put(player2, player);
                                }
                                replyHash.put(player, player2);
                                replyHash.put(player2, player);
                            } else {
                                player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.ReplyUsage")));
                            }
                        } else {
                            player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NotFound")));
                            replyHash.remove(player);
                            replyHash.remove(player2);
                        }
                    } else {
                        player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NotFound")));
                    }
                } else {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoReply")));
                }
            } else {
                player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoPermission")));
            }
        }
    }
}
