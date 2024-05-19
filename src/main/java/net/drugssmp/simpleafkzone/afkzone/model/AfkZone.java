package net.drugssmp.simpleafkzone.afkzone.model;

import lombok.Data;
import net.drugssmp.simpleafkzone.SimpleAfkZone;
import org.bukkit.Location;

import java.util.List;

@Data
public class AfkZone {
    private Cuboid cuboid;
    private Location spawn;
    private List<String> rewards;

    public AfkZone() {
        Location pos1 = (Location) SimpleAfkZone.get().getConfig().get("afkzone.locations.pos1");
        Location pos2 = (Location) SimpleAfkZone.get().getConfig().get("afkzone.locations.pos2");
        Location spawn = (Location) SimpleAfkZone.get().getConfig().get("afkzone.locations.spawn");
        if (pos1 == null || pos2 == null || spawn == null) {
            SimpleAfkZone.LOGGER.severe("[AFKZONE] Locations not found!");
            this.cuboid = null;
            return;
        }
        this.cuboid = new Cuboid(pos1, pos2);
        this.spawn = spawn;
        this.rewards = SimpleAfkZone.get().getConfig().getStringList("afkzone.rewards");
    }
}
