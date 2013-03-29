package com.evosysdev.bukkit.taylorjb.simplecensor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Handle chat events
 * 
 * @author taylorjb
 * 
 */
public class SimpleCensorPlayerListener implements Listener
{
    // the plugin using our listener
    private final SimpleCensor plugin;

    /**
     * Initialize the player listener
     * 
     * @param instance
     *            instance of our plugin for this listener
     */
    public SimpleCensorPlayerListener(SimpleCensor instance)
    {
        plugin = instance;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When a player chats, censor the message
     * 
     * @param event
     *            chat event of the player
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        // replace message in the chat event with censored message
        event.setMessage(plugin.getCensor().censorMessage(event.getMessage()));
    }
}