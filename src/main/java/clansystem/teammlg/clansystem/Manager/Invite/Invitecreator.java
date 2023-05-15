package clansystem.teammlg.clansystem.Manager.Invite;

import clansystem.teammlg.clansystem.Manager.Clan;
import net.md_5.bungee.api.connection.ProxiedPlayer;
//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------
public class Invitecreator {
    public static Invitecreator instance;
    public static Invitecreator getInstance(){
        return instance;
    }


    public Invitecreator(){
        instance = this;

    }
    public void execute(ProxiedPlayer targetplayer, ProxiedPlayer from){
        Invitehandler.getinstance().invite(targetplayer, from);
    }
}
