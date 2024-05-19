package net.drugssmp.simpleafkzone.afkzone.model;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Data
public class Cuboid {
    private final World world;

    private int minX, maxX, minY, maxY, minZ, maxZ;

    public Cuboid(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.world = world;
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public void clearCuboid() {
        for (int x = this.minX; x <= this.maxX; x++)
            for (int y = this.minY; y <= this.maxY; y++)
                for (int z = this.minZ; z <= this.maxZ; z++)
                    if (this.world.getBlockAt(x, y, z).getType() != Material.AIR)
                        this.world.getBlockAt(x, y, z).setType(Material.AIR);
    }

    public boolean contains(Cuboid cuboid) {
        return (cuboid.getWorld().equals(this.world) && cuboid
                .getMinX() >= this.minX && cuboid.getMaxX() <= this.maxX && cuboid
                .getMinY() >= this.minY && cuboid.getMaxY() <= this.maxY && cuboid
                .getMinZ() >= this.minZ && cuboid.getMaxZ() <= this.maxZ);
    }

    public boolean contains(Location location) {
        if (location.getWorld().getName().equals(this.world.getName()))
            return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return false;
    }

    public boolean isIn(Player player) {
        return contains(player.getLocation());
    }

    public boolean contains(int x, int y, int z) {
        return (x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ);
    }

    public boolean overlaps(Cuboid cuboid) {
        return (cuboid.getWorld().equals(this.world) && cuboid
                .getMinX() <= this.maxX && cuboid.getMinY() <= this.maxY && cuboid.getMinZ() <= this.maxZ && this.minZ <= cuboid
                .getMaxX() && this.minY <= cuboid.getMaxY() && this.minZ <= cuboid.getMaxZ());
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Cuboid other))
            return false;
        return (this.world.equals(other.world) && this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ && this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ);
    }

    public String toString() {
        return "Cuboid[world:" + this.world.getName() + ", minX:" + this.minX + ", minY:" + this.minY + ", minZ:" + this.minZ + ", maxX:" + this.maxX + ", maxY:" + this.maxY + ", maxZ:" + this.maxZ + "]";
    }
}