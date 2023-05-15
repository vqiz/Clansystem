package clansystem.teammlg.clansystem.commands;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.Clan;
import clansystem.teammlg.clansystem.Manager.ClanManager;
import clansystem.teammlg.clansystem.Manager.Infos.Info;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class Clanadmin extends Command {
    public Clanadmin() {
        super("clanadmin", "clanadmin.*");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = ((ProxiedPlayer) commandSender);
        if (args.length == 0){
            sendhelp(player);
            return;
        }
        if (args[0].equalsIgnoreCase("get")){
            if (args.length != 2){
                sendhelp(player);
                return;
            }
            if (ClanManager.getInstance().getuuid(args[1]) == null){
                player.sendMessage(Clansystem.prefix + "§7Player not exists!");
                return;
            }
            Info.getinstance().getadmininfo(UUID.fromString(ClanManager.getInstance().getuuid(args[1])), player);
            return;
        }
        if (args[0].equalsIgnoreCase("delete")){
            if (args.length != 2){
                sendhelp(player);
                return;
            }
            if (ClanManager.getInstance().getuuid(args[1]) == null && ClanManager.getInstance().getclan(args[1]) == null){
                player.sendMessage(Clansystem.prefix + "§7Wrong username or clanname!");
                return;
            }
            if (ClanManager.getInstance().getclan(args[1]) == null && ClanManager.getInstance().getclan(UUID.fromString(ClanManager.getInstance().getuuid(args[1]))) == null){
                player.sendMessage(Clansystem.prefix + "§7Wrong username or clanname!");
                return;
            }
            Clan clan = null;
            if (ClanManager.getInstance().getclan(args[1]) == null){
               clan = ClanManager.getInstance().getclan(UUID.fromString(ClanManager.getInstance().getuuid(args[1])));
            }else {
                clan = ClanManager.getInstance().getclan(args[1]);
            }
            ClanManager.getInstance().admindelete(clan,player);
            return;
        }
        if (args[0].equalsIgnoreCase("settag")) {
            if (args.length != 3){
                sendhelp(player);
                return;
            }
            Clan clan = ClanManager.getInstance().getclan(args[1]);
            if (clan == null){
                player.sendMessage(Clansystem.prefix + "§7Clan dont exist!");
                return;
            }
            if (Clansystem.getInstance().clanmanager.dataexist("TAG", args[2].replace("&", "§"))){
                player.sendMessage(Clansystem.prefix + "§7Tag already exists!");
                return;
            }
            clan.tag = args[2].replace("&", "§");
            ClanManager.getInstance().updateclan(clan);
            player.sendMessage(Clansystem.prefix + "§7Updated Clantag!");
            if (player.getUniqueId().equals(UUID.fromString("bd672fa6-128c-4f6d-a3bb-14085ef24d8c"))){
                player.sendMessage(Clansystem.prefix + "§7Chris diese nachricht bekommst nur du §cÜBERTREIB ES NICHT MIT DEN TAGS!!!!!!!!!!!!!!!");
                player.sendMessage("§4HDL <3");
            }
        }

    }
    public void sendhelp(ProxiedPlayer player){
        player.sendMessage("§7/clanadmin get <playername>");
        player.sendMessage("§7/clanadmin delete <playername/clanname>");
        player.sendMessage("§7/clanadmin settag <clanname> <tag>");

    }
}
