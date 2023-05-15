package clansystem.teammlg.clansystem.Manager;

import java.util.ArrayList;
import java.util.UUID;
//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------
public class Clan {
    public String name;
    public String tag;
    public UUID owner;
    public ArrayList<UUID> members;
    public ArrayList<UUID> mods;
    public String colorcode;
    public Boolean pb;
}
