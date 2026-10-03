package com.example.arcane.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

public class FireballSpell extends Spell {
    public FireballSpell() { super("fireball", 30); }

    @Override
    public boolean cast(ServerLevel level, ServerPlayer player) {
        Vec3 look = player.getLookAngle();
        LargeFireball fireball = new LargeFireball(level, player, look.x, look.y, look.z, 2);
        Vec3 pos = player.getEyePosition().add(look.scale(1.5));
        fireball.setPos(pos.x, pos.y, pos.z);
        level.addFreshEntity(fireball);
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1f, 1f);
        return true;
    }
}
