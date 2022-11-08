package net.liquifymc.integrations.anvil.wrapper;

import net.liquifymc.integrations.anvil.container.AnvilContainer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WrapperHandler {

    public static void setActiveContainerDefault(EntityPlayer entityPlayer) {
        entityPlayer.bV = entityPlayer.bU;
    }

    public static void setActiveContainer(EntityPlayer entityPlayer, Object container) {
        entityPlayer.bV = (Container) container;
    }

    public static Object newContainerAnvil(Player player) {
        return new AnvilContainer(player, ((CraftPlayer) player).getHandle().nextContainerCounter());
    }

    public static Inventory toBukkitInventory(Object container) {
        return ((Container) container).getBukkitView().getTopInventory();
    }

    public static int getNextContainerId(Object container) {
        return ((AnvilContainer) container).j;
    }

    public static void addActiveContainerSlotListener(Object container, EntityPlayer entityPlayer) {
        entityPlayer.a((Container) container);
    }
}
