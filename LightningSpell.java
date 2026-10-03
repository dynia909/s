package com.example.arcane.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.HitResult;

public class LightningSpell extends Spell {
    public LightningSpell() { super("lightning", 60); }

    @Override
    public boolean cast(ServerLevel level, ServerPlayer player) {
        HitResult hit = player.pick(40.0, 1.0f, false);
        if (hit.getType() == HitResult.Type.MISS) return false;

        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) return false;
        bolt.moveTo(hit.getLocation());
        bolt.setCause(player);
        level.addFreshEntity(bolt);
        return true;
    }
}
