package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.EnchantmentEffects;

public class SlashParticleEffect extends AbstractScalableParticleEffect {
    public static final MapCodec<SlashParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createCodec(instance).apply(instance, SlashParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SlashParticleEffect> PACKET_CODEC = createPacketCodec(SlashParticleEffect::new);

    public SlashParticleEffect(float scale) {
        super(scale);
    }

    @Override
    public @NonNull ParticleType<?> getType() {
        return EnchantmentEffects.SLASH_PARTICLE;
    }
}
