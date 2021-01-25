package de.miinoo.factions.addon;

public abstract class FactionsAddon {

    protected final String name;

    public FactionsAddon(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract void onEnable(AddonRegistry registry);

    // Ausgeführt nach dem alles gestartet wurde
    // falls APIs genutzt werden
    public void onPostEnable(AddonRegistry registry){}

}
