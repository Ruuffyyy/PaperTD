package de.ruffy.papertd;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

public class Enemy {
    private final Zombie zombie;
    private double distanceMoved = 0;
    private final Path path;

    private double health = 100;

    public Enemy(Location location, Path path) {
        this.zombie = location.getWorld().spawn(location, Zombie.class);
        this.path = path;
        //zombie.setAI(false);
        zombie.setInvulnerable(true);

        Bukkit.getMobGoals().removeAllGoals(zombie);
        zombie.customName(Component.text("Enemy", NamedTextColor.RED));
        zombie.setCustomNameVisible(true);
        zombie.setPersistent(false);
        zombie.setShouldBurnInDay(false);
    }


    public boolean move(double distance) {
        distanceMoved += distance;
        Vector position = path.getPositionOnPath(distanceMoved);
        if (position == null) {
            zombie.remove();
            return false;
        }
        Vector delta = position.clone().subtract(zombie.getLocation().toVector());
        float yaw = (float) Math.toDegrees(Math.atan2(-delta.getX(), delta.getZ()));
        zombie.teleport(position.toLocation(zombie.getWorld(), yaw, 0));
        return true;
    }

    public Vector getPosition() {
        return zombie.getLocation().toVector();

    }

    public void damage(double damage) {
        health -= damage;
        if (health <= 0) {
            zombie.remove();
        }
    }

    public boolean isDead() {
        return health <= 0;
    }



}
