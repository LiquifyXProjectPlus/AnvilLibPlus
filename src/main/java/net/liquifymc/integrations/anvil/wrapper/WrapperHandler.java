package net.liquifymc.integrations.anvil.wrapper;

import net.liquifymc.integrations.anvil.container.AnvilContainer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
