package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.Bukkit;

public class AsyncCounterReceiver {

    public void receiveCounter(WhereAreYouGoingPlugin plugin, byte[] data){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new ReceiveCounterRunnable(plugin, data));
    }
}
