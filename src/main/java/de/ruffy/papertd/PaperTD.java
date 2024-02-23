package de.ruffy.papertd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public final class PaperTD extends JavaPlugin {

    private static Plugin instance;
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




    public static Plugin getPlugin() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }








    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player))
            return false;
        new TowerDefensGame(path);

        return true;
    }



}
