package net.liquifymc.integrations.anvil.event;

import net.liquifymc.integrations.anvil.slots.AnvilSlot;

public class AnvilClickEvent {
    private final AnvilSlot slot;
    private final String name;
    private boolean close = true;
    private boolean destroy = true;

    public AnvilClickEvent(AnvilSlot slot, String name) {
        this.slot = slot;
        this.name = name;
    }

    public AnvilSlot getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public boolean getWillClose() {
        return close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
    }

    public boolean getWillDestroy() {
        return destroy;
    }

    public void setWillDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
