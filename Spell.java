package com.example.arcane.spell;

import com.example.arcane.ArcaneMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public abstract class Spell {
    private final String id;
    private final int cooldownTicks;

    protected Spell(String id, int cooldownTicks) {
        this.id = id;
        this.cooldownTicks = cooldownTicks;
    }

    public String id() { return id; }
    public int cooldownTicks() { return cooldownTicks; }

    public Component name() {
        return Component.translatable("spell." + ArcaneMod.MOD_ID + "." + id);
    }

    /** Runs on the server. Return true if the spell was cast (starts cooldown). */
    public abstract boolean cast(ServerLevel level, ServerPlayer player);
}
