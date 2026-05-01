package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.EnchantmentEffects;

public class BaneParticleEffect extends AbstractScalableParticleEffect {
    public static final MapCodec<BaneParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createCodec(instance).apply(instance, BaneParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BaneParticleEffect> PACKET_CODEC = createPacketCodec(BaneParticleEffect::new);

    public BaneParticleEffect(float scale) {
        super(scale);
    }

    @Override
    public @NonNull ParticleType<?> getType() {
        return EnchantmentEffects.BANE_PARTICLE;
    }
}
