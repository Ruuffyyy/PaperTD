package de.ruffy.papertd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public final class PaperTD extends JavaPlugin {

    Path path = new Path(List.of(
            new Vector(312.5, 67, -324+0.5),
            new Vector(312.5, 67, -329+0.5),
            new Vector(307.5, 67, -329+0.5),
            new Vector(307.5, 67, -333+0.5),
            new Vector(316.5, 67, -333+0.5),
            new Vector(316.5, 67, -337+0.5),
            new Vector(315.5, 67, -337+0.5),
            new Vector(315.5, 67, -339+0.5),
            new Vector(265.5, 67, -339+0.5),
            new Vector(265.5, 67, -328+0.5),
            new Vector(255.5, 67, -328+0.5),
            new Vector(255.5, 67, -342+0.5),
            new Vector(252.5, 67, -342+0.5)));


    List<Tower> towers = new ArrayList<>();

    List<Enemy> enemies = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Tower tower : towers) {
                tower.tick(enemies);
            }
        }, 0, 1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }








    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player))
            return false;
        if(towers.isEmpty())
            initTowers(player);
        Enemy enemy = new Enemy(path.getStartPositon().toLocation(player.getWorld()), path);
        enemies.add(enemy);
        AtomicInteger taskID = new AtomicInteger();
        taskID.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
           if (enemy.isDead()) {
                Bukkit.getScheduler().cancelTask(taskID.get());
                enemies.remove(enemy);
                return;
            }
            if (!enemy.move(0.1)) {
                Bukkit.getScheduler().cancelTask(taskID.get());
                enemies.remove(enemy);
            }
        }, 0, 1));
        return true;
    }

    private void initTowers(Player player) {
        towers.add(new Tower(new Vector(309.5, 67, -331+0.5).toLocation(player.getWorld())));
        towers.add(new Tower(new Vector(314.5, 67, -335+0.5).toLocation(player.getWorld())));
        towers.add(new Tower(new Vector(311.5, 67, -337+0.5).toLocation(player.getWorld())));
    }


}
