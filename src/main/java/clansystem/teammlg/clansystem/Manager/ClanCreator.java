package clansystem.teammlg.clansystem.Manager;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.mysql.Table;
import com.google.gson.Gson;
import com.sun.imageio.plugins.tiff.TIFFDecompressor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.guieffect.qual.UI;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.nio.charset.StandardCharsets;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.UUID;

public class ClanCreator {
    private Clan clan;
    private Table cm = Clansystem.getInstance().clanmanager;
    private Gson gson = Clansystem.getInstance().gson;
    public ClanCreator(String name, String tag, UUID owner){
        this.clan = new Clan();
        clan.name = name;
        clan.tag = tag;
        clan.mods = new ArrayList<>();
        clan.members = new ArrayList<>();
        clan.owner = owner;
        clan.members.add(owner);
        clan.colorcode = "";
        clan.pb = false;
    }
    public void create(){
        if (ClanManager.getInstance().getclan(clan.owner) != null){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            player.sendMessage(Clansystem.prefix + "§7You already in a clan!");
        }
          if (clan.name.contains("&")
                || clan.name.contains("&")
                || clan.name.contains("%")
                || clan.name.contains("$")
                || clan.name.getBytes(StandardCharsets.UTF_8).toString() == ""
                || clan.name.getBytes(StandardCharsets.UTF_8).toString() == ""
                || !check(clan.name)
                || !check(clan.name)) {
              ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
              if (player != null){
                  player.sendMessage(Clansystem.prefix + "§cInvalid §7letter in §6clanname!");
              }
              return;
        }
        if (clan.tag.contains("&")
                || clan.tag.contains("&")
                || clan.tag.contains("%")
                || clan.tag.contains("$")
                || clan.tag.getBytes(StandardCharsets.UTF_8).toString() == ""
                || clan.tag.getBytes(StandardCharsets.UTF_8).toString() == ""
                || !check(clan.tag)
                || !check(clan.tag)) {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            if (player != null){
                player.sendMessage(Clansystem.prefix + "§cInvalid §7letter in §6clantag!");
            }
            return;
        }
        if (clan.name.length() > 12 || clan.name.length() < 3){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            player.sendMessage(Clansystem.prefix + "§7the clan name must contain over 3 and under 12 letters");
            return;
        }
        if (clan.tag.length() < 2 || clan.tag.length() > 4){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            player.sendMessage(Clansystem.prefix + "§7the clan tag must contain over 1 and under 4 letters");
            return;

        }
        if (!cm.dataexist("NAME", clan.name) && !cm.dataexist("TAG", clan.tag)){
            cm.pinsert(clan.name, clan.tag, gson.toJson(clan));
            ClanManager.getInstance().setclanuser(clan.owner, clan.name);
            System.out.println("Created clan " + clan.name + " " + clan.tag + " " + gson.toJson(clan));
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            if (player != null){
                player.sendMessage(Clansystem.prefix + "§7created clan §6" + clan.name + " §7with tag §6" + clan.tag + "§7!");
            }

        }else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(clan.owner);
            if (player != null){
                player.sendMessage(Clansystem.prefix + "§7clanname or tag §calready §7exists!");
            }

        }

    }
    /*     */   boolean check(String s) {
        /* 466 */     if (s == null) {
            /* 467 */       return false;
            /*     */     }
        /* 469 */     for (int len = s.length(), i = 0; i < len; i++) {
            /* 470 */       if (!Character.isLetter(s.charAt(i)) && !Character.isDigit(i)) {
                /* 471 */         return false;
                /*     */       }
            /*     */     }
        /* 474 */     return true;
        /*     */   }
}



