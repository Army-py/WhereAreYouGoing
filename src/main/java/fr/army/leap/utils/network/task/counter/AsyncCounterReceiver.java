package fr.army.leap.utils.network.task.counter;

import fr.army.leap.LeapPlugin;
import org.bukkit.Bukkit;

public class AsyncCounterReceiver {

    public void receiveCounter(LeapPlugin plugin, byte[] data){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new ReceiveCounterRunnable(plugin, data));
    }
}
