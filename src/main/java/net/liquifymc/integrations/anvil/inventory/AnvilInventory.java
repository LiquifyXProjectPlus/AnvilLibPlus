package net.liquifymc.integrations.anvil.inventory;

import lombok.Getter;
import net.liquifymc.integrations.anvil.event.AnvilClickEvent;
import net.liquifymc.integrations.anvil.event.AnvilClickEventHandler;
import net.liquifymc.integrations.anvil.slots.AnvilSlot;
import net.liquifymc.integrations.anvil.wrapper.WrapperHandler;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Containers;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * MIT License
 * Copyright (c) 2022 AnvilGUI
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author Gdalia, LiquifyMC
 * @since 1.0-SNAPSHOT build number #1
 */
public class AnvilInventory {
    @Getter
    private final Plugin plugin;
    private Player player;
    private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();
    private int containerId;
    private Inventory inv;
    private Listener listener;

    public AnvilInventory(Player player, AnvilClickEventHandler handler, Plugin plugin) {
        this.plugin = plugin;
        this.player = player;
        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (!(event.getWhoClicked() instanceof Player) || !event.getInventory().equals(inv)) return;
                event.setCancelled(true);
                ItemStack item = event.getCurrentItem();
                int slot = event.getRawSlot();
                String name = "";

                if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                    ItemMeta itemMeta = item.getItemMeta();
                    name = itemMeta.getDisplayName();
                }

                if (AnvilSlot.valueOf(slot) == null) return;
                AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.valueOf(slot), name);
                handler.onAnvilClick(clickEvent);

                if (clickEvent.getWillClose())
                    event.getWhoClicked().closeInventory();

                if (clickEvent.getWillDestroy())
                    destroy();
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (!(event.getPlayer() instanceof Player) || !event.getInventory().equals(inv)) return;
                ((CraftPlayer) player).getHandle().b.a(new PacketPlayOutCloseWindow(containerId));
                WrapperHandler.setActiveContainerDefault(((CraftPlayer) player).getHandle());

                for (AnvilSlot slot : items.keySet())
                    player.getInventory().removeItem(items.get(slot));
                destroy();
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (!event.getPlayer().equals(getPlayer())) return;
                destroy();
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public Player getPlayer() {
        return player;
    }

    public void setSlot(AnvilSlot slot, ItemStack item) {
        items.put(slot, item);
    }

    public void open(String title) {
        try {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            Object container = WrapperHandler.newContainerAnvil(player);
            inv = WrapperHandler.toBukkitInventory(container);

            for (AnvilSlot slot : items.keySet())
                inv.setItem(slot.getSlot(), items.get(slot));

            containerId = WrapperHandler.getNextContainerId(container);
            p.b.a(new PacketPlayOutOpenWindow(containerId, Containers.h, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")));
            WrapperHandler.setActiveContainer(p, container);
            WrapperHandler.addActiveContainerSlotListener(container, p);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        HandlerList.unregisterAll(listener);
        listener = null;
        player = null;
        items = null;
    }
}
