package soullessberry.enchantmenteffects.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import soullessberry.enchantmenteffects.EffectHandler;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method="attack", at=@At(value="HEAD"))
    private void enchantmenteffects$attack(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide() && entity instanceof LivingEntity livingEntity && !livingEntity.isDeadOrDying()) {
            EffectHandler.applyWeaponEffects(player.getWeaponItem(), entity);
        }
    }
}
