package fr.army.whereareyougoing.config;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class WaitingDestinationIndicator {

    private final String title;
    private final BarColor color;
    private final BarStyle style;

    public WaitingDestinationIndicator(String title, BarColor color, BarStyle style) {
        this.title = title;
        this.color = color;
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public BarColor getColor() {
        return color;
    }

    public BarStyle getStyle() {
        return style;
    }

    public BossBar createBossBar(int position) {
        return Bukkit.createBossBar(
                title.replace("{POSITION}", String.valueOf(position)),
                color,
                style
        );
    }
}
