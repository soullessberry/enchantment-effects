package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.EnchantmentEffects;

public class BeamParticleEffect extends AbstractScalableParticleEffect {

    public static final MapCodec<BeamParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createCodec(instance).apply(instance, BeamParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BeamParticleEffect> PACKET_CODEC = createPacketCodec(BeamParticleEffect::new);

    public BeamParticleEffect(float scale) {
        super(scale);
    }

    @Override
    public @NonNull ParticleType<?> getType() {
        return EnchantmentEffects.BEAM_PARTICLE;
    }
}
