package net.liquifymc.integrations.anvil.container;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AnvilContainer extends ContainerAnvil {

    public AnvilContainer(Player player, int containerId) {
        super(containerId, ((CraftPlayer) player).getHandle().fr(), ContainerAccess.a(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return true;
    }
}
