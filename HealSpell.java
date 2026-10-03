package com.example.arcane.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class HealSpell extends Spell {
    public HealSpell() { super("heal", 200); }

    @Override
    public boolean cast(ServerLevel level, ServerPlayer player) {
        if (player.getHealth() >= player.getMaxHealth()) return false;
        player.heal(8.0f);
        level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 1.2, player.getZ(),
                10, 0.4, 0.4, 0.4, 0.02);
        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.7f, 1.4f);
        return true;
    }
}
