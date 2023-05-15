package clansystem.teammlg.clansystem.Manager.Infos;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.Clan;
import clansystem.teammlg.clansystem.Manager.ClanManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Info {
    public static Info instance;
    public static Info getinstance(){
        return instance;
    }
    public Info(){
        instance = this;
    }
    public void getinfo(ProxiedPlayer player){
        Clan clan = ClanManager.getInstance().getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        player.sendMessage("§8§m---------------§8[" + Clansystem.prefix.replace(">", "").replace(" ", "") + "§8]§8§m---------------");
        player.sendMessage("§7Name : §6" + clan.name);
        player.sendMessage("§7Tag : §6" + clan.colorcode + clan.tag);
        player.sendMessage("§7Owner : §6" + ClanManager.getInstance().getname(clan.owner));
        player.sendMessage("§7Mods : §6" + clan.mods.size());
        player.sendMessage("§7Member : §6" + clan.members.size());

    }
    public void getadmininfo(UUID target, ProxiedPlayer player){

            Clan clan = ClanManager.getInstance().getclan(target);
            if (clan == null){
                player.sendMessage(Clansystem.prefix + "§7Clan dont exists!");
                return;
            }
            player.sendMessage("§8§m---------------§8[" + Clansystem.prefix.replace(">", "").replace(" ", "") + "§8]§8§m---------------");
            player.sendMessage("§7Name : §6" + clan.name);
            player.sendMessage("§7Tag : §6" + clan.colorcode + clan.tag);
            player.sendMessage("§7Owner : §6" + ClanManager.getInstance().getname(clan.owner));
            player.sendMessage("§7Mods : §6" + clan.mods.size());
            player.sendMessage("§7Member : §6" + clan.members.size());


    }
    public void listmembers(ProxiedPlayer player){
        Clan clan = ClanManager.getInstance().getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        player.sendMessage("§8§m---------------§8[" + Clansystem.prefix.replace(">", "").replace(" ", "") + "§8]§8§m---------------");
        player.sendMessage("§7Owner : §6" + ClanManager.getInstance().getname(clan.owner));
        player.sendMessage("§7Mods : ");
        clan.mods.forEach(uuid -> {
            player.sendMessage("    §6" + ClanManager.getInstance().getname(uuid));
        });
        player.sendMessage("§7Members : ");
        clan.members.forEach(uuid -> {
            if (!clan.mods.contains(uuid)){
                player.sendMessage("    §6" + ClanManager.getInstance().getname(uuid));

            }

        });
    }
}
