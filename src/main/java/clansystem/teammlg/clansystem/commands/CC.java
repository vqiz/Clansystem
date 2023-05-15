package clansystem.teammlg.clansystem.commands;

import clansystem.teammlg.clansystem.Manager.chat.Chat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CC extends Command {
    public CC() {
        super("cc");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Chat.getinstance().send(((ProxiedPlayer) commandSender), args);
    }
}
