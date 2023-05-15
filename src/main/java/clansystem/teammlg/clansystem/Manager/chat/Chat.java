package clansystem.teammlg.clansystem.Manager.chat;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.Clan;
import clansystem.teammlg.clansystem.Manager.ClanManager;

import clansystem.teammlg.clansystem.Manager.Rank;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Chat {
    public static Chat instance;
    public static Chat getinstance(){
        return instance;
    }
    public Chat(){
        instance = this;
    }
    public String s = "§7";
    public void send(ProxiedPlayer player, String[] args){
        System.out.println("code running");
        Clan clan = ClanManager.getInstance().getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        s = "§7";
        for (int i = 0; i < args.length; i++){
            System.out.println(s);
            s = s + " " + args[i];
            System.out.println(s);
        }
        clan.members.forEach(uuid -> {
            ProxiedPlayer player1 = ProxyServer.getInstance().getPlayer(uuid);
            System.out.println("chat done");
            if (player1 != null){
                System.out.println("sending");
                if (ClanManager.getInstance().getrank(player.getUniqueId()).equals(Rank.leader)){
                    player1.sendMessage(Clansystem.prefix + "§4" + player.getDisplayName() + " §8: " + s);
                    return;
                }
                if (ClanManager.getInstance().getrank(player.getUniqueId()).equals(Rank.mod)){
                    player1.sendMessage(Clansystem.prefix + "§c" + player.getDisplayName() + " §8: " + s);
                    return;
                }else {
                    player1.sendMessage(Clansystem.prefix + "§7" + player.getDisplayName() + " §8: " + s);
                    return;
                }


            }
        });

    }
}
