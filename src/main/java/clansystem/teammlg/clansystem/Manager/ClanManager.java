package clansystem.teammlg.clansystem.Manager;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.mysql.Table;
import com.google.gson.Gson;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.units.qual.A;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClanManager {
    private Gson gson = Clansystem.instance.gson;
    public static ClanManager instance;
    public static ClanManager getInstance(){
        return instance;
    }
    public ArrayList<String> list = new ArrayList<>();
    public ClanManager(){
        instance = this;
        list.add("4");
        list.add("c");
        list.add("6");
        list.add("e");
        list.add("2");
        list.add("a");
        list.add("b");
        list.add("3");
        list.add("1");
        list.add("9");
        list.add("d");
        list.add("5");
        list.add("7");
        list.add("8");
        list.add("0");
    }
    public void setcolor(ProxiedPlayer player, String code){
        if (player.hasPermission("group.premium")){
            Clan clan = getclan(player.getUniqueId());
            if (clan == null){
                player.sendMessage(Clansystem.prefix + "§7you are not in a clan!");
                return;
            }
            if (getrank(player.getUniqueId()).equals(Rank.leader)){
                if (list.contains(code)){
                    clan.colorcode = "§" + code;
                    ClanManager.getInstance().updateclan(clan);
                    player.sendMessage(Clansystem.prefix + "§7You changed the colorcode to &" + code + "§7!");
                    return;

                }else if (code.equalsIgnoreCase("f") && player.hasPermission("group.mlg")){
                    clan.colorcode = "§" + code;
                    ClanManager.getInstance().updateclan(clan);
                    player.sendMessage(Clansystem.prefix + "§7You changed the colorcode to &" + code + "§7!");
                    return;

                }else {
                    player.sendMessage(Clansystem.prefix + "§7Your rank doesent contain this code!");
                    return;
                }

            }else {
                player.sendMessage(Clansystem.prefix + "§7You only can change this if you are the clan §cleader§7!");
            }

        }else {
            player.sendMessage(Clansystem.prefix + "You need premium or higher to get custom clan colors!");
        }


    }
    public Clan getclan(String name){
        if (Clansystem.getInstance().clanmanager.dataexist("NAME",name)) {
            Clan clan = gson.fromJson(Clansystem.getInstance().clanmanager.getString(name, "NAME", "DATA"), Clan.class);
            return clan;
        }else {
            return null;
        }
    }
    public void leaveclan(ProxiedPlayer player){
        UUID uuid = player.getUniqueId();
        if (getclan(uuid) != null){
            Clan clan = getclan(uuid);
            if (getrank(uuid) != Rank.leader){
                clan.members.remove(uuid);
                removeclan(uuid);
                clan.mods.remove(uuid);
                clan.members.forEach(uuid1 -> {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(uuid1);
                    if (target != null){
                        target.sendMessage(Clansystem.prefix + "§7The member §6" + player.getDisplayName()+ " §7left the clan!");
                    }
                });
                player.sendMessage(Clansystem.prefix + "§7You left this clan!");
                updateclan(clan);
            }else {
                player.sendMessage(Clansystem.prefix + "§7You cant leave your own clan!");
            }


        }else {
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");

        }


    }
    public void setpublic(ProxiedPlayer player){
        Clan clan = getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }else {
           if (clan.pb){
               player.sendMessage(Clansystem.prefix + "§7Clan is already public!");
           }else {
               if (getrank(player.getUniqueId()).equals(Rank.leader)){
                    clan.pb = true;
                    ClanManager.getInstance().updateclan(clan);
                    player.sendMessage(Clansystem.prefix + "§7You set the clan public!");
                    return;
               }else {
                   player.sendMessage(Clansystem.prefix + "§7You are not allowed to do this!");
               }
           }

        }

    }


    public void setprivate(ProxiedPlayer player){
        Clan clan = getclan(player.getUniqueId());
        if (clan == null){
            player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }else {
            if (!clan.pb){
                player.sendMessage(Clansystem.prefix + "§7Clan is already private!");
            }else {
                if (getrank(player.getUniqueId()).equals(Rank.leader)){
                    clan.pb = false;
                    ClanManager.getInstance().updateclan(clan);
                    player.sendMessage(Clansystem.prefix + "§7You set the clan private!");
                    return;
                }else {
                    player.sendMessage(Clansystem.prefix + "§7You are not allowed to do this!");
                }
            }

        }

    }
    public Clan getclan(UUID uuid){
        String clanname = null;
        if (Clansystem.getInstance().clanusermanager.dataexist("UUID", uuid.toString())){
            clanname =  Clansystem.getInstance().clanusermanager.getString(uuid.toString(),"UUID", "NAME");
        }else {
            return null;
        }
        if (Clansystem.getInstance().clanmanager.dataexist("NAME",clanname)) {
            Clan clan = gson.fromJson(Clansystem.getInstance().clanmanager.getString(clanname, "NAME", "DATA"), Clan.class);
            return clan;
        }else {
            return null;
        }
    }
    public Boolean isinclan(Clan clan, UUID uuid){
        if (clan.members.contains(uuid)){
            return true;
        }else {
            return false;
        }
    }
    public void kick(UUID uuid, String target){
        if (getuuid(target) == null){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            player.sendMessage(Clansystem.prefix + "§7Player §cnot §7found!");
        }else {
            Clan c = getclan(uuid);
            if (c == null){
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                player.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            }else {
                if (!c.members.contains(UUID.fromString(getuuid(target)))){
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                    player.sendMessage(Clansystem.prefix + "§7You are not in the same clan as " + target + "!");
                }else {
                    if (getrank(uuid).equals(Rank.leader)){
                        removeclan(UUID.fromString(getuuid(target)));
                        c.members.remove(UUID.fromString(getuuid(target)));
                        c.mods.remove(UUID.fromString(getuuid(target)));
                        updateclan(c);
                        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                        player.sendMessage(Clansystem.prefix + "§7You kicked §6" + target + " §7out of your clan!");
                    }
                    if (getrank(uuid).equals(Rank.mod)){
                        if (getrank(UUID.fromString(getuuid(target))) != Rank.member){
                            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                            player.sendMessage(Clansystem.prefix + "§7You §ccant §7kick a member with the same rank or higher as yours!");
                        }else {
                            removeclan(UUID.fromString(getuuid(target)));
                            c.members.remove(UUID.fromString(getuuid(target)));
                            c.mods.remove(UUID.fromString(getuuid(target)));
                            updateclan(c);
                            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                            player.sendMessage(Clansystem.prefix + "§7You kicked §6" + target + " §7out of your clan!");
                        }
                    }
                }

            }


        }


    }
    public void updateclan(Clan clan){
        Clansystem.getInstance().clanmanager.setString(clan.name,"NAME", "DATA", Clansystem.getInstance().gson.toJson(clan));
        Clansystem.getInstance().clanmanager.setString(clan.name, "NAME", "TAG", clan.tag);
    }
    public void deleteclan(UUID uuid){
        if (getclan(uuid) == null){
            ProxiedPlayer deleter = ProxyServer.getInstance().getPlayer(uuid);
            if (deleter != null){
                deleter.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            }
        }
        if (getrank(uuid).equals(Rank.leader)){
            Clan clan = getclan(uuid);

            clan.members.forEach(uuid1 -> {
                removeclan(uuid1);
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid1);
                if (player != null && player.getUniqueId() != uuid){
                    player.sendMessage(Clansystem.prefix + "§7your clan got §cdeleted§7!");
                }
            });
            Clansystem.getInstance().clanmanager.pdelete("NAME", clan.name);
            ProxiedPlayer deleter = ProxyServer.getInstance().getPlayer(uuid);
            if (deleter != null){
                deleter.sendMessage(Clansystem.prefix + "§7Your clan got deleted!");
            }

        }else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            if (player != null){
                player.sendMessage(Clansystem.prefix + "§7You are §cnot §7allowed to delete this clan!");
            }
        }


    }
    public void admindelete(Clan clan, ProxiedPlayer teammember){
        clan.members.forEach(uuid1 -> {
            removeclan(uuid1);
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid1);
            if (player == null) return;
                player.sendMessage(Clansystem.prefix + "§7your clan got §cdeleted§7!");
        });
        Clansystem.getInstance().clanmanager.pdelete("NAME", clan.name);
        teammember.sendMessage(Clansystem.prefix + "§7You deleted " + clan.name +"!");
    }
    public void setclanuser(UUID uuid, String Name){
        if (!Clansystem.getInstance().clanusermanager.dataexist("UUID", uuid.toString())){
            Clansystem.getInstance().clanusermanager.pinsert(uuid.toString(), Name);
        }
        Clansystem.getInstance().clanusermanager.setString(uuid.toString(), "UUID", "NAME", Name);
    }
    public void removeclan(UUID uuid){
        Clansystem.getInstance().clanusermanager.pdelete("UUID", uuid.toString());
    }
    public Rank getrank(UUID uuid){

        Clan clan = getclan(uuid);
        if (clan == null){
            return null;
        }

        if (clan.owner.equals(uuid)){
            return Rank.leader;
        }
        if (clan.mods.contains(uuid)){
            return Rank.mod;
        }
        if (clan.members.contains(uuid)){
            return Rank.member;
        }
        return null;

    }
    Table user = Clansystem.getInstance().user;
    /*     */   public String getname(UUID uuid){
        if (user.dataexist("UUID", uuid.toString())){
            return user.getString(uuid.toString(), "UUID", "NAME");
        }
        return null;


    }
    public String getuuid(String name){
        if (name == "Console"){
            return "Console";
        }
        if (user.dataexist("NAME", name)){
            if (user.getString(name, "NAME", "UUID") != "Console"){
                return user.getString(name, "NAME", "UUID");
            }
        }

        return null;

    }

}
