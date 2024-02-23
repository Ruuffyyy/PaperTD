package de.ruffy.papertd;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.util.List;

public class Tower {
    private final IronGolem ironGolem;
    private final int range = 10;
    private final int attackSpeed = 5;

    private int tickCounter = 0;

    public Tower(Location location) {
        this.ironGolem = location.getWorld().spawn(location, IronGolem.class);
        ironGolem.setAI(false);
        ironGolem.setInvulnerable(true);
        ironGolem.setCustomNameVisible(true);
        ironGolem.customName(Component.text("Tower", NamedTextColor.BLUE));
        ironGolem.setPersistent(false);

    }


    public void tick(List<Enemy> enemies) {
        tickCounter++;
        if (tickCounter % attackSpeed == 0) {
            attack(enemies);
        }

    }

    private void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().distance(ironGolem.getLocation().toVector()) > range) {
                continue;
            }
            Vector target = enemy.getPosition();
            Vector delta = target.clone().subtract(ironGolem.getLocation().toVector());
            ironGolem.teleport(ironGolem.getLocation().setDirection(delta));
            ironGolem.launchProjectile(Snowball.class, delta.normalize());
            enemy.damage(10);
            return;
        }
    }

    public void remove() {
        ironGolem.remove();
    }

}


