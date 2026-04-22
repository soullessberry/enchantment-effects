package soullessberry.enchantmenteffects.particles;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public abstract class AbstractScalableParticleEffect implements ParticleOptions {
    private final float scale;

    public AbstractScalableParticleEffect(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public static <T extends AbstractScalableParticleEffect> Products.P1<RecordCodecBuilder.Mu<T>, Float> createCodec(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(Codec.FLOAT.fieldOf("scale").forGetter(AbstractScalableParticleEffect::getScale));
    }

    public static <T extends AbstractScalableParticleEffect> StreamCodec<RegistryFriendlyByteBuf, T> createPacketCodec(Function<Float, T> applyFunction) {
        return StreamCodec.composite(
                ByteBufCodecs.FLOAT, AbstractScalableParticleEffect::getScale,
                applyFunction
        );
    }
}
