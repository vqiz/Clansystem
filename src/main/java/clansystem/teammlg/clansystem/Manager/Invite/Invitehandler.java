package clansystem.teammlg.clansystem.Manager.Invite;

import clansystem.teammlg.clansystem.Clansystem;
import clansystem.teammlg.clansystem.Manager.Clan;
import clansystem.teammlg.clansystem.Manager.ClanManager;
import clansystem.teammlg.clansystem.Manager.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------
import java.util.HashMap;

public class Invitehandler {
    public static Invitehandler instance;
    public Invitehandler(){
        instance = this;
    }
    public static Invitehandler getinstance(){
        return instance;
    }
    public HashMap<ProxiedPlayer, String> invites = new HashMap<>();
    public Boolean isinvited(ProxiedPlayer player, String s ){
        if (invites.containsKey(player)){
            if (invites.get(player).equals(s)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }
    public Boolean isinvited(ProxiedPlayer player){
        if (invites.containsKey(player)){
            return true;
        }else {
            return false;
        }
    }
    public void removeinvite(ProxiedPlayer player){
        invites.remove(player);
    }
    public void invite(ProxiedPlayer target, ProxiedPlayer from){
        Clan c = ClanManager.getInstance().getclan(from.getUniqueId());
        if (c == null){
            from.sendMessage(Clansystem.prefix + "§7You are not in a clan!");
            return;
        }
        if (c.members.contains(target.getUniqueId())){
            from.sendMessage(Clansystem.prefix + "§7this member is already in your clan!");
            return;
        }
        if (c.pb == true){
            invites.put(target, c.name);
            target.sendMessage(Clansystem.prefix + "§7You were invited to join the clan §6" + c.name);
            TextComponent c1 = new TextComponent(Clansystem.prefix + "§7Click here to : ");
            c1.addExtra("§a[accept]");
            c1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept " + c.name));
            TextComponent c2 = new TextComponent("");
            c2.addExtra(" §c[deny]");
            c2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan deny " + c.name));
            c1.addExtra(c2);
            target.sendMessage(c1);
            from.sendMessage(Clansystem.prefix + "§7You invited §6" + target.getDisplayName() + " §7to your clan!");

        }else {
            if (ClanManager.getInstance().getrank(from.getUniqueId()).equals(Rank.mod) || ClanManager.getInstance().getrank(from.getUniqueId()).equals(Rank.leader)){
                invites.put(target, c.name);
                target.sendMessage(Clansystem.prefix + "§7You were invited to join the clan §6" + c.name);
                TextComponent c1 = new TextComponent(Clansystem.prefix + "§7Click here to : ");
                c1.addExtra("§a[accept]");
                c1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept " + c.name));
                TextComponent c2 = new TextComponent("");
                c2.addExtra(" §c[deny]");
                c2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan deny " + c.name));
                c1.addExtra(c2);
                target.sendMessage(c1);
                from.sendMessage(Clansystem.prefix + "§7You invited §6" + target.getDisplayName() + " §7to your clan!");



            }else {
                from.sendMessage(Clansystem.prefix + "§7The clan is not public and you are not allowed to invite §6" + target.getDisplayName() + "§7!");
            }

        }
    }
}
