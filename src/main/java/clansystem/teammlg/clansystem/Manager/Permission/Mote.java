package clansystem.teammlg.clansystem.Manager.Permission;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.Clan;
import clansystem.teammlg.clansystem.Manager.ClanManager;
import clansystem.teammlg.clansystem.Manager.Rank;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;
//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------
public class Mote {
    public static Mote instance;
    public static Mote getInstance(){
        return instance;
    }
    public Mote(){
        instance = this;
    }
    public void promote(ProxiedPlayer player, String target){
        Clan clan = ClanManager.getInstance().getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        if (ClanManager.instance.getuuid(target) == null){
            player.sendMessage(Clansystem.prefix + "§7User §cnot §7found!");
            return;
        }
        if (ClanManager.getInstance().isinclan(clan, UUID.fromString(ClanManager.instance.getuuid(target)))){
            if (ClanManager.getInstance().getrank(player.getUniqueId()).equals(Rank.leader)){
                if (ClanManager.getInstance().getrank(UUID.fromString(ClanManager.instance.getuuid(target))).equals(Rank.mod)){
                    player.sendMessage(Clansystem.prefix + "§7Player is already mod!");
                    return;

                }else {
                    clan.mods.add(UUID.fromString(ClanManager.getInstance().getuuid(target)));
                    ClanManager.getInstance().updateclan(clan);
                    ProxiedPlayer ta = ProxyServer.getInstance().getPlayer(target);
                    if (ta != null){
                        ta.sendMessage(Clansystem.prefix + "§7You got promoted to §cclanmod§7!");
                    }
                    player.sendMessage(Clansystem.prefix + "§7You promoted §6" + target + " §7to §cclanmod§7!");
                    return;
                }

            }else {
                player.sendMessage(Clansystem.prefix + "§7You are not allowed to do this!");
                return;
            }

        }else {
            player.sendMessage(Clansystem.prefix + "§7user is §cnot §7in your clan!");
            return;
        }


    }
    public void demote(ProxiedPlayer player , String target){
        Clan clan = ClanManager.getInstance().getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        if (ClanManager.instance.getuuid(target) == null){
            player.sendMessage(Clansystem.prefix + "§7User §cnot §7found!");
            return;
        }
        if (ClanManager.getInstance().isinclan(clan, UUID.fromString(ClanManager.instance.getuuid(target)))){
            if (ClanManager.getInstance().getrank(player.getUniqueId()).equals(Rank.leader)){
                if (!ClanManager.getInstance().getrank(UUID.fromString(ClanManager.instance.getuuid(target))).equals(Rank.mod)){
                    player.sendMessage(Clansystem.prefix + "§7Player is already member!");
                    return;

                }else {
                    clan.mods.remove(UUID.fromString(ClanManager.getInstance().getuuid(target)));
                    ClanManager.getInstance().updateclan(clan);
                    ProxiedPlayer ta = ProxyServer.getInstance().getPlayer(target);
                    if (ta != null){
                        ta.sendMessage(Clansystem.prefix + "§7You got demoted to §7clanmember§7!");
                    }
                    player.sendMessage(Clansystem.prefix + "§7You demoted §6" + target + " §7to§7 clanmember§7!");
                    return;
                }

            }else {
                player.sendMessage(Clansystem.prefix + "§7You are not allowed to do this!");
                return;
            }

        }else {
            player.sendMessage(Clansystem.prefix + "§7user is §cnot §7in your clan!");
            return;
        }



    }
}
