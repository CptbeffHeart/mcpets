package fr.nocsy.mcpets.listeners;

import fr.nocsy.mcpets.mythicmobs.MythicListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class EventListener implements Listener {

    private static ArrayList<Listener> listeners = new ArrayList<>();

    public static void init(JavaPlugin plugin) {

        listeners.add(new PetMenuListener());
        listeners.add(new PetInteractionMenuListener());
        listeners.add(new PetListener());
        listeners.add(new SignalStickListener());

        listeners.add(new MythicListener());

        for (Listener l : listeners) {
            plugin.getServer().getPluginManager().registerEvents(l, plugin);
        }

    }

}
