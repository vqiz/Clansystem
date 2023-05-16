package clansystem.teammlg.clansystem;

import clansystem.teammlg.clansystem.Manager.ClanManager;
import clansystem.teammlg.clansystem.Manager.Infos.Info;
import clansystem.teammlg.clansystem.Manager.Invite.Invitecreator;
import clansystem.teammlg.clansystem.Manager.Invite.Invitehandler;
import clansystem.teammlg.clansystem.Manager.Permission.Mote;
import clansystem.teammlg.clansystem.Manager.chat.Chat;
import clansystem.teammlg.clansystem.commands.CC;
import clansystem.teammlg.clansystem.commands.Clan;
import clansystem.teammlg.clansystem.commands.Clanadmin;
import clansystem.teammlg.clansystem.mysql.DatabaseManager;
import clansystem.teammlg.clansystem.mysql.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
//-------------------------
//@Copyright by vqiz
//contact discord : vqiz#5292
//----------------------------

public final class Clansystem extends Plugin {
  public Table user = new Table("systembanh214", "UUID TEXT, NAME TEXT, IP TEXT, SERVER TEXT");
  public Gson gson = new GsonBuilder().create();
  public static Clansystem instance;
  public Table clanmanager = new Table("Cmanager","NAME TEXT,TAG TEXT, DATA TEXT");
  public Table clanusermanager = new Table("Cmusermanager","UUID TEXT,NAME TEXT");
  public static String prefix;
  public static Clansystem getInstance(){
        return instance;
    }
  public static DatabaseManager db;
  Configuration config = null;
  @Override
  public void onEnable() {
        instance = this;
        config = makeConfig();
        db = new DatabaseManager(config.getString("mysql.host"), config.getString("mysql.user"), config.getString("mysql.database"), config.getString("mysql.password"));
        db.Connect();
        clanmanager.create(db);
        clanusermanager.create(db);
        prefix = config.getString("prefix");
        new ClanManager();
        new Invitehandler();
        new Mote();
        new Invitehandler();
        new Invitecreator();
        new Info();
        new Chat();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CC());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Clan());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Clanadmin());
    }
  public Configuration makeConfig() {
        /*  75 */     if (!getDataFolder().exists()) {
            /*  76 */       getLogger().info("Created config folder: " + getDataFolder().mkdir());
            /*     */     }
        /*     */
        /*  79 */     File configFile = new File(getDataFolder(), "config.yml");
        /*     */
        /*     */
        /*  82 */     if (!configFile.exists()) {
            /*     */       try {
                /*  84 */         configFile.createNewFile();
                /*  85 */       } catch (IOException e) {
                /*  86 */         throw new RuntimeException(e);
                /*     */       }
            /*     */     }
        /*     */
        /*  90 */     Configuration config = null;
        /*     */     try {
            /*  92 */       config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            /*  93 */     } catch (IOException e) {
            /*  94 */       throw new RuntimeException(e);
            /*     */     }
        /*     */
        /*  97 */     addDefault(config, "mysql.host", "127.0.0.1");
        /*  98 */     addDefault(config, "mysql.port", "3306");
        /*  99 */     addDefault(config, "mysql.database", "DatenBank");
        /* 100 */     addDefault(config, "mysql.user", "Username");
        /* 101 */     addDefault(config, "mysql.password", "DatenbankPasswort");
        /* 102 */     addDefault(config, "prefix", "§6§lParty §8§l>> §r");
        /*     */     try {
            /* 106 */       ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            /* 107 */     } catch (IOException e) {
            /* 108 */       throw new RuntimeException(e);
            /*     */     }
        /*     */
        /* 111 */     return config;
        /*     */   }

  @Override
  public void onDisable() {
        // Plugin shutdown logic
    }
  private String addDefault(Configuration conf, String path, String test1) {
      if (!conf.contains(path)) {
          conf.set(path, test1);
      }
      return conf.getString(path);
  }
}
