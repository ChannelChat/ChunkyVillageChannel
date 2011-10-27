package feildmaster.modules.chanchat.chunkyvillage;

import com.feildmaster.chanchat.Chat;
import com.feildmaster.chanchat.Util.ModuleConfiguration;
import org.blockface.bukkitstats.CallHome;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Village extends JavaPlugin {
    private static Village plugin;
    private VillageChannel channel;

    private ModuleConfiguration config;

    // Configuration
    private boolean enabled;
    private boolean listed;
    private boolean auto;
    private String name;
    private String tag;
    private String alias;
    private ChatColor color;

    public void onEnable() {
        plugin = this;
        CallHome.load(this);

        setupConfig();

        reloadConfig();

        if(enabled)
            Chat.getChannelManager().addChannel(channel);
        // else delete (channel) // !!! need to add this...

        getServer().getLogger().info(String.format("[%1$s] v%2$s Enabled!", getDescription().getName(), getDescription().getVersion()));
    }

    public void onDisable() {
        Chat.getChannelManager().delChannel(channel.getName());
        channel = null; // Allows renaming on reload
        getServer().getLogger().info(String.format("[%1$s] Disabled!", getDescription().getName()));
    }

    private void setupConfig() {
        config = new ModuleConfiguration(this);
        if(!config.exists())
            config.saveDefaults();
    }

    private void loadConfig() {
        config.load();

        enabled = config.getBoolean("enabled");
        name = config.getString("name");
        tag = config.getString("tag");
        alias = config.getString("alias");
        listed = config.getBoolean("listed");
        color = config.getChatColor("color");
        auto = config.getBoolean("auto");
    }

    public void reloadConfig() {
        loadConfig();
        if(channel == null) channel = new VillageChannel(name);

        channel.setTag(tag);
        channel.setListed(listed);
        channel.setChatColor(color);
        if(!channel.setAlias(alias)) System.out.println(channel.getName()+" - Alias "+alias+" is taken.");
        channel.setAuto(auto);
    }

    public static Village getPlugin() {
        return plugin;
    }
}
