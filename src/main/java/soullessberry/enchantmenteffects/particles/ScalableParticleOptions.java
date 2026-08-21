package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;

public class ScalableParticleOptions implements ParticleOptions {
    private final ParticleType<ScalableParticleOptions> type;
    private final float scale;

    public static MapCodec<ScalableParticleOptions> codec(final ParticleType<ScalableParticleOptions> type) {
        return Codec.FLOAT.xmap(scale -> new ScalableParticleOptions(type, scale), o -> o.scale).fieldOf("scale");
    }

    public static StreamCodec<? super ByteBuf, ScalableParticleOptions> streamCodec(final ParticleType<ScalableParticleOptions> type) {
        return ByteBufCodecs.FLOAT.map(scale -> new ScalableParticleOptions(type, scale), o -> o.scale);
    }

    public ScalableParticleOptions(final ParticleType<ScalableParticleOptions> type, final float scale) {
        this.type = type;
        this.scale = scale;
    }

    @Override
    public @NonNull ParticleType<ScalableParticleOptions> getType() {
        return this.type;
    }

    @Override
    public float getScale() {
        return this.scale;
    }
}
