package feildmaster.modules.chanchat.chunkyvillage;

import com.feildmaster.chanchat.Chan.CustomChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.getchunky.chunkyvillage.objects.ChunkyResident;
import org.getchunky.chunkyvillage.objects.ChunkyTown;

public class VillageChannel extends CustomChannel {
    private ChunkyTown town;

    public VillageChannel(String name) {
        super(name);
    }

    public String getDisplayName() {
        return super.getDisplayName().replaceAll("(?i)\\{name}", town == null?getName():town.getName());
    }

    public Boolean isMember(Player player) {
        if(town == null) return super.isMember(player);
        return super.isMember(player) && town.equals(new ChunkyResident(player).getTown());
    }

    public void sendJoinMessage(Player player) {
        town = new ChunkyResident(player).getTown();
        if(town == null) {
            player.sendMessage("You have joined \""+getName()+"\"");
            return;
        }

        super.sendJoinMessage(player);

        town = null;
    }

    public void handleEvent(PlayerChatEvent event) {
        town = new ChunkyResident(event.getPlayer()).getTown();

        if(town == null) {
            event.getPlayer().sendMessage("You do not have a town.");
            event.setCancelled(true);
            return;
        }

        super.handleEvent(event);

        town = null;
    }
}
