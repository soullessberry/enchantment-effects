package soullessberry.enchantmenteffects.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import soullessberry.enchantmenteffects.EffectHandler;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @WrapOperation(method="handleDamageEvent", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V"))
    public void satisfyingsmite$handleDamageEvent(Entity target, DamageSource source, Operation<Void> original) {

        if (target != null && source.getEntity() instanceof Player player && source.isDirect()) {
            EffectHandler.applyEffects(player, target);
        }

        original.call(target, source);
    }
}
