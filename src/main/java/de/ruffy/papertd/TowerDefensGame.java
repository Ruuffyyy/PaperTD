package de.ruffy.papertd;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class TowerDefensGame implements Listener {
    private List<Tower> towers = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Path path;

    private int coin = 100;

    private int tickCounter = 0;
    private int livesLost;

    public TowerDefensGame(Path path) {
        this.path = path;
        Bukkit.getPluginManager().registerEvents(this, PaperTD.getPlugin());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PaperTD.getPlugin(), this::tick, 0, 1);

    }



    public void tick() {
        tickCounter++;
        if (tickCounter % 20 == 0 && tickCounter > 100) {
            enemies.add(new Enemy(path.getStartPositon().toLocation(Bukkit.getWorld("world")), path));
        }
        Bukkit.getServer().sendActionBar(Component.text("Coins: " + coin, NamedTextColor.GOLD)
                .append(Component.text(" Lives: " + (20 - livesLost), NamedTextColor.RED)));
        for (Tower tower : towers) {
            tower.tick(enemies);
        }
        for (Enemy enemy : enemies) {
            if (!enemy.move(0.1)) {
                livesLost++;

            }
            if (!enemy.isDead()) {
                continue;
            }
            enemies.remove(enemy);
            coin += 10;
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getClickedBlock() ==null)) {
            return;
        }
        if (event.getHand()!= EquipmentSlot.HAND) {
            return;
        }
        if (coin < 50) {
            return;
        }
        coin -= 50;
        towers.add(new Tower(event.getClickedBlock().getLocation().add(0.5, 1, 0.5)));

    }






}
