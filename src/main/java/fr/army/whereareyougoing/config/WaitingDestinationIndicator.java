package fr.army.whereareyougoing.config;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public record WaitingDestinationIndicator(String title, BarColor color, BarStyle style) {

    public BossBar createBossBar(int position) {
        return Bukkit.createBossBar(
                title.replace("{POSITION}", String.valueOf(position)),
                color,
                style
        );
    }
}
