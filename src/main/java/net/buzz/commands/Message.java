package net.buzz.commands;

import net.buzz.OPMessage;
import net.buzz.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class Message extends Command {
    private final OPMessage instance;
    public static List<String> messages = new ArrayList<>();

    public Message(OPMessage instance) {
        super("msg", "", "tell", "say", "message", "w", "whisper", "ew", "emsg", "em", "m", "msg", "t", "et", "ewhisper", "etell", "emessage");
        this.instance = instance;
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("opcraft.member")) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length >= 2) {
                ProxiedPlayer player2 = OPMessage.getInstance().getProxy().getPlayer(args[0]);
                if (player2 == null) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NotFound")));
                    return;
                }
                if (player.getName().equals(player2.getName())) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.MessageSelf")));
                    return;
                }
                if (Toggle.toggle.contains(player2)) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.OtherDisabled")
                            .replace("{player}", player2.getName())));
                    return;
                }
                if (Toggle.toggle.contains(player)) {
                    player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("ToggleMsg.SelfDisabled")));
                    return;
                }
                String message = "";
                for (int i = 1; i < args.length; i++)
                    message = String.valueOf(String.valueOf(String.valueOf(message))) + args[i] + " ";
                String senderServer = player.getServer().getInfo().getName();
                String receiverServer = player2.getServer().getInfo().getName();
                String senderFormat = ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.SenderFormat")
                        .replace("{message}", message).replace("{sendername}", player.getName())
                        .replace("{senderserver}", senderServer).replace("{receiverserver}" , receiverServer)
                        .replace("{receivername}", player2.getName()));
                String receiverFormat = ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.ReceiverFormat")
                        .replace("{message}", message).replace("{sendername}", player.getName())
                        .replace("{senderserver}", senderServer).replace("{receiverserver}", receiverServer)
                        .replace("{receivername}", player2.getName()));
                player.sendMessage(senderFormat);
                player2.sendMessage(receiverFormat);
                for (ProxiedPlayer staff : OPMessage.getInstance().getProxy().getPlayers()) {
                    if (SocialSpy.spy.contains(staff)) {
                        if (player2.getName().equalsIgnoreCase(this.instance.getMainConfiguration().getString("{receiver}"))) {
                            return;
                        }
                        String format = ChatUtil.colorize(this.instance.getMainConfiguration().getString("SocialSpy.Format")
                                .replace("{sender}", player.getName())
                                .replace("{receiver}", player2.getName()).replace("{message}", message)
                                .replace("{senderserver}", senderServer));
                        if (SocialSpy.spy.contains(player) && player.getName().equalsIgnoreCase(this.instance.getMainConfiguration().getString("{sender}"))) {
                            return;
                            }
                        staff.sendMessage(format);
                    }
                }
                if (Reply.replyHash.containsKey(player) || Reply.replyHash.containsKey(player2)) {
                    Reply.replyHash.remove(player);
                    Reply.replyHash.remove(player2);
                    Reply.replyHash.put(player, player2);
                    Reply.replyHash.put(player2, player);
                }
                Reply. replyHash.put(player, player2);
                Reply.replyHash.put(player2, player);
            } else {
                player.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Message.MessageUsage")));
            }
        } else {
            sender.sendMessage(ChatUtil.colorize(this.instance.getMainConfiguration().getString("Errors.NoPermission")));
        }
    }
}
