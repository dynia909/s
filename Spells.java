package com.example.arcane.spell;

import java.util.ArrayList;
import java.util.List;

public class Spells {
    public static final List<Spell> ALL = new ArrayList<>();

    public static void register() {
        ALL.clear();
        ALL.add(new FireballSpell());
        ALL.add(new LightningSpell());
        ALL.add(new FrostNovaSpell());
        ALL.add(new HealSpell());
        ALL.add(new BlinkSpell());
        // Add new spells here!
    }
}
