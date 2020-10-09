package com.wangyifan.speedrunhelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MyTimer extends TimerTask {
    int time = 0;
    String formattedTime = "";
    MinecraftClient client = null;
    public MyTimer(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        time++;
        long h, m, s;
        long millis = time;
        h = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(h);

        m = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(m);

        s = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(s);

        millis /= 100;

        if (h == 0) {
            formattedTime = String.format("§6%02d:%02d.%01d", m, s, millis);
        } else {
            formattedTime = String.format("§6%02d:%02d:%02d.%01d", h, m, s, millis);
        }
        client.player.sendMessage(new LiteralText(formattedTime), true);
    }
}
