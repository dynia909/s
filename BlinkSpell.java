package com.example.arcane.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BlinkSpell extends Spell {
    private static final double RANGE = 12.0;

    public BlinkSpell() { super("blink", 40); }

    @Override
    public boolean cast(ServerLevel level, ServerPlayer player) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(RANGE));

        HitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 target = hit.getType() == HitResult.Type.MISS ? end : hit.getLocation().subtract(look.scale(0.6));
        // Convert eye position to feet position
        target = target.subtract(0, player.getEyeHeight(), 0);

        level.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1, player.getZ(), 40, 0.3, 0.6, 0.3, 0.3);
        player.teleportTo(target.x, target.y, target.z);
        player.fallDistance = 0;
        level.sendParticles(ParticleTypes.PORTAL, target.x, target.y + 1, target.z, 40, 0.3, 0.6, 0.3, 0.3);
        level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
        return true;
    }
}
