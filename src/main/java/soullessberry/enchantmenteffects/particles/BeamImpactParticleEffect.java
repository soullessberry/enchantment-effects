package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.EnchantmentEffects;

public class BeamImpactParticleEffect extends AbstractScalableParticleEffect {

    public static final MapCodec<BeamImpactParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createCodec(instance).apply(instance, BeamImpactParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BeamImpactParticleEffect> PACKET_CODEC = createPacketCodec(BeamImpactParticleEffect::new);

    public BeamImpactParticleEffect(float scale) {
        super(scale);
    }

    @Override
    public @NonNull ParticleType<?> getType() {
        return EnchantmentEffects.BEAM_IMPACT_PARTICLE;
    }
}
