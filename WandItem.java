package com.example.arcane;

import com.example.arcane.spell.Spell;
import com.example.arcane.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class WandItem extends Item {
    private static final String KEY = "SpellIndex";

    public WandItem(Properties props) {
        super(props);
    }

    public static int getIndex(ItemStack stack) {
        int i = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getInt(KEY);
        return Math.floorMod(i, Spells.ALL.size());
    }

    private static void setIndex(ItemStack stack, int index) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(KEY, index));
    }

    public static Spell getSpell(ItemStack stack) {
        return Spells.ALL.get(getIndex(stack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide || !(player instanceof ServerPlayer sp)) {
            return InteractionResultHolder.success(stack);
        }

        // Sneak + right-click: next spell
        if (player.isShiftKeyDown()) {
            int next = (getIndex(stack) + 1) % Spells.ALL.size();
            setIndex(stack, next);
            player.displayClientMessage(
                    Component.literal("Spell: ").append(Spells.ALL.get(next).name().copy().withStyle(ChatFormatting.AQUA)),
                    true);
            return InteractionResultHolder.success(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        Spell spell = getSpell(stack);
        if (spell.cast((ServerLevel) level, sp)) {
            player.getCooldowns().addCooldown(this, spell.cooldownTicks());
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Spell: ").withStyle(ChatFormatting.GRAY)
                .append(getSpell(stack).name().copy().withStyle(ChatFormatting.AQUA)));
        tooltip.add(Component.literal("Right-click: cast  |  Sneak + right-click: switch").withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
