package com.example.arcane.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FrostNovaSpell extends Spell {
    public FrostNovaSpell() { super("frost_nova", 100); }

    @Override
    public boolean cast(ServerLevel level, ServerPlayer player) {
        double r = 6.0;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(r), t -> t != player && t.distanceToSqr(player) <= r * r)) {
            e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3));
            e.setTicksFrozen(e.getTicksRequiredToFreeze() + 60);
            e.hurt(level.damageSources().freeze(), 4.0f);
        }
        level.sendParticles(ParticleTypes.SNOWFLAKE, player.getX(), player.getY() + 1, player.getZ(),
                200, r / 2, 0.5, r / 2, 0.05);
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1f, 0.8f);
        return true;
    }
}
