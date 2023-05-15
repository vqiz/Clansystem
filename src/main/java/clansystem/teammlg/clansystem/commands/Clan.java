package clansystem.teammlg.clansystem.commands;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.ClanCreator;
import clansystem.teammlg.clansystem.Manager.ClanManager;
import clansystem.teammlg.clansystem.Manager.Infos.Info;
import clansystem.teammlg.clansystem.Manager.Invite.Invitecreator;
import clansystem.teammlg.clansystem.Manager.Invite.Invitehandler;
import clansystem.teammlg.clansystem.Manager.Permission.Mote;
import clansystem.teammlg.clansystem.Manager.chat.Chat;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.util.internal.StringUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clan extends Command implements TabExecutor {
    public Clan() {
        super("clan", null);
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = ((ProxiedPlayer) commandSender);
        if (args.length == 0){
            sendhelp(player);
            return;
        }
        if (args[0].equalsIgnoreCase("create")){
            if (args.length != 3){
                sendhelp(player);
                return;
            }
            new ClanCreator(args[1], args[2], player.getUniqueId()).create();
            return;
        }
        if (args[0].equalsIgnoreCase("delete")){

            if (args.length != 1 && args.length != 2){
                sendhelp(player);
                return;
            }
            if (args.length == 1){
                player.sendMessage(Clansystem.prefix + "§7if you realy want to delete your clan confirm with §6/clan delete confirm");
            }
            if (args.length == 2 && args[1].equalsIgnoreCase("confirm")){
                ClanManager.getInstance().deleteclan(player.getUniqueId());
            }
            return;
        }
        if (args[0].equalsIgnoreCase("invite")){
            if (args.length != 2){
                sendhelp(player);
                return;
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
            if (target != null){
                Invitecreator.getInstance().execute(target, player);
            }else {
                player.sendMessage(Clansystem.prefix + "§7Player §cnot §7found!");
            }
            return;


        }
        if (args[0].equalsIgnoreCase("kick")){
            if (args.length != 2){
                sendhelp(player);
                return;

            }
            ClanManager.getInstance().kick(player.getUniqueId(), args[1]);
            return;

        }
        if (args[0].equalsIgnoreCase("accept")){
            if (args.length == 2){
                if (ClanManager.getInstance().getclan(args[1]) == null){
                    System.out.println("debug null");
                    player.sendMessage(Clansystem.prefix + "§7You are not invited to this clan!");
                    return;
                }
                clansystem.teammlg.clansystem.Manager.Clan clan = ClanManager.getInstance().getclan(args[1]);
                if (clan.members.contains(player.getUniqueId())){
                    player.sendMessage(Clansystem.prefix + "you are already in this clan!");
                    return;
                }
                if (Invitehandler.instance.isinvited(player,args[1])){

                    clan.members.add(player.getUniqueId());
                    ClanManager.getInstance().updateclan(clan);
                    ClanManager.getInstance().setclanuser(player.getUniqueId(),clan.name);
                    clan.members.forEach(uuid -> {
                        ProxiedPlayer player1 = ProxyServer.getInstance().getPlayer(uuid);
                        if (player1 != null){
                            player1.sendMessage(Clansystem.prefix + "§7" + player.getDisplayName() + " §ajoined §7the clan !");
                        }

                    });
                }else {
                    System.out.println("debug123");
                    player.sendMessage(Clansystem.prefix + "§7You are not invited to this clan!");
                }



            }else {
                player.sendMessage(Clansystem.prefix + "§7Provide a valid command!");
            }
            return;
        }
        if (args[0].equalsIgnoreCase("deny")){
            Invitehandler.getinstance().removeinvite(player);
            player.sendMessage(Clansystem.prefix + "§7You §cdenyed §7this clan request");
            return;


        }
        if (args[0].equalsIgnoreCase("leave")){
            if (args.length == 1){
                ClanManager.getInstance().leaveclan(player);
                return;
            }else {
                sendhelp(player);
            }
            return;

        }
        if (args[0].equalsIgnoreCase("promote")){
            if (args.length == 2){
                Mote.getInstance().promote(player, args[1]);
                return;
            }else {
                sendhelp(player);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("demote")){
            if (args.length == 2){
                Mote.getInstance().demote(player, args[1]);
                return;

            }else {
                sendhelp(player);
                return;
            }


        }
        if (args[0].equalsIgnoreCase("msg")){
            Chat.getinstance().send(player, args);

        }
        if (args[0].equalsIgnoreCase("info")){
            if (args.length == 1){
                Info.getinstance().getinfo(player);
                return;
            }else {
                sendhelp(player);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("listmembers")){
            if (args.length == 1){
                Info.getinstance().listmembers(player);
                return;
            }else {
                sendhelp(player);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("public")){
            if (args.length == 1){
                ClanManager.getInstance().setpublic(player);
                return;


            }else {
                sendhelp(player);
                return;
            }

        }
        if (args[0].equalsIgnoreCase("private")){
            if (args.length == 1){
                ClanManager.getInstance().setprivate(player);
                return;


            }else {
                sendhelp(player);
                return;
            }

        }
        if (args[0].equalsIgnoreCase("setcolor")){
            if (args.length == 2){
                ClanManager.getInstance().setcolor(player, args[1]);
                return;


            }else {
                sendhelp(player);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("join")){
            if (args.length != 2){
                sendhelp(player);
                return;
            }
            clansystem.teammlg.clansystem.Manager.Clan clan = ClanManager.getInstance().getclan(args[1]);
            clansystem.teammlg.clansystem.Manager.Clan clan1 = ClanManager.getInstance().getclan(player.getUniqueId());
            if (clan1 != null){
                if (clan1.equals(ClanManager.getInstance().getclan(args[1]))){
                    player.sendMessage(Clansystem.prefix + "§7you are already in this clan!");
                    return;
                }
            }
            if (clan != null){
                if (clan.pb){
                    clan.members.add(player.getUniqueId());
                    ClanManager.getInstance().updateclan(clan);
                    ClanManager.getInstance().setclanuser(player.getUniqueId(),clan.name);
                    clan.members.forEach(uuid -> {
                        ProxiedPlayer player1 = ProxyServer.getInstance().getPlayer(uuid);
                        if (player1 != null){
                            player1.sendMessage(Clansystem.prefix + "§7" + player.getDisplayName() + " §ajoined §7the clan !");
                        }

                    });
                }else {
                    player.sendMessage(Clansystem.prefix + "§7Clan is not public!");
                }

            }else {
                player.sendMessage(Clansystem.prefix + "§7Clan dont exists!");
            }

        }
    }

    public void sendhelp(ProxiedPlayer player){
        player.sendMessage("§8§m---------------§8[" + Clansystem.prefix.replace(">", "").replace(" ", "") + "§8]§8§m---------------");
        player.sendMessage("§6/clan §7create <name> <tag> -create your own clan");
        player.sendMessage("§6/clan §7delete -delete your own clan");
        player.sendMessage("§6/clan §7invite <player> -invite player to your clan");
        player.sendMessage("§6/clan §7kick <player> -kick a player out of your clan");
        player.sendMessage("§6/clan §7accept -accept the invite of a clan");
        player.sendMessage("§6/clan §7deny -deny the invite of a clan");
        player.sendMessage("§6/clan §7leave -leave your clan");
        player.sendMessage("§6/clan §7promote <player> -promote a player of your clan");
        player.sendMessage("§6/clan §7demote <player> -demote a player of your clan");
        player.sendMessage("§6/cc §7<message>");
        player.sendMessage("§6/clan §7info -get some infos of your clans");
        player.sendMessage("§6/clan §7listmembers -list of all members of your clan");
        player.sendMessage("§6/clan §7public -everyone can join your clan");
        player.sendMessage("§6/clan §7private -only invite clan");
        player.sendMessage("§6/clan §7setcolor <colorcode without &> -changes the color of your tag");
        player.sendMessage("§6/clan §7join <clanname>");
    }

    List<String> validArguments = new ArrayList<>();
    List<String> c = new ArrayList<>();
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> validArguments = this.validArguments;
            validArguments.clear();
            validArguments.add("create");
            validArguments.add("delete");
            validArguments.add("invite");
            validArguments.add("kick");
            validArguments.add("accept");
            validArguments.add("deny");
            validArguments.add("leave");
            validArguments.add("promote");
            validArguments.add("demote");
            validArguments.add("msg");
            validArguments.add("info");
            validArguments.add("listmembers");
            List<String> c = this.c;
            c.clear();
            validArguments.forEach(s -> {
                if (args[0].startsWith(s)){
                    c.add(s);
                }
            });
            return c;
        } else {
            return Collections.emptyList();
        }
    }
}































































































//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------