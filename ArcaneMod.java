package com.example.arcane;

import com.example.arcane.spell.Spells;
import net.fabricmc.api.ModInitializer;

public class ArcaneMod implements ModInitializer {
    public static final String MOD_ID = "arcane";

    @Override
    public void onInitialize() {
        Spells.register();
        ModItems.register();
    }
}
