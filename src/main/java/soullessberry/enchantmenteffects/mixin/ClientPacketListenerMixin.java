package soullessberry.enchantmenteffects.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import soullessberry.enchantmenteffects.EffectHandler;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @WrapOperation(method="handleDamageEvent", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V"))
    public void enchantmenteffects$handleDamageEvent(Entity target, DamageSource source, Operation<Void> original) {

        if (
                target != null && source.getEntity() instanceof LivingEntity attacker && source.isDirect()
                && (source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO) || source.is(DamageTypes.SPEAR) || source.is(DamageTypes.MACE_SMASH))
                && (attacker != Minecraft.getInstance().player || source.is(DamageTypes.SPEAR))
        ) {
            EffectHandler.applyWeaponEffects(attacker.getWeaponItem(), target);
        }

        if (target != null && source.is(DamageTypes.THORNS)) {
            EffectHandler.applyThornsEffect(target, source.getEntity());
        }

        original.call(target, source);
    }
}
