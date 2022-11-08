package net.liquifymc.integrations.anvil.slots;

public enum AnvilSlot {
    INPUT_LEFT(0),
    INPUT_RIGHT(1),
    OUTPUT(2);

    private final int slot;
    AnvilSlot(int slot) {
        this.slot = slot;
    }

    public static AnvilSlot valueOf(int slot) {
        for (AnvilSlot anvilSlot : values())
            if (anvilSlot.getSlot() == slot)
                return anvilSlot;

        return null;
    }

    public int getSlot() {
        return slot;
    }
}
