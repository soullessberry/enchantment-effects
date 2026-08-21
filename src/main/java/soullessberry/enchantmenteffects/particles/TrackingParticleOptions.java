package soullessberry.enchantmenteffects.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.api.EntityIdProvider;
import soullessberry.enchantmenteffects.api.ScaleProvider;

public class TrackingParticleOptions implements ParticleOptions, ScaleProvider, EntityIdProvider {
    private final ParticleType<TrackingParticleOptions> type;
    private final float scale;
    private final int entityId;

    public static MapCodec<TrackingParticleOptions> codec(final ParticleType<TrackingParticleOptions> type) {
        return RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.FLOAT.fieldOf("scale").forGetter(TrackingParticleOptions::getScale),
                        Codec.INT.fieldOf("entityId").forGetter(TrackingParticleOptions::getEntityId)
                ).apply(instance, (scale, entityId) -> new TrackingParticleOptions(type, scale, entityId))
        );
    }

    public static StreamCodec<? super ByteBuf, TrackingParticleOptions> streamCodec(final ParticleType<TrackingParticleOptions> type) {
        return StreamCodec.composite(
                ByteBufCodecs.FLOAT, TrackingParticleOptions::getScale,
                ByteBufCodecs.INT, TrackingParticleOptions::getEntityId,
                (scale, entityId) -> new TrackingParticleOptions(type, scale, entityId)
        );
    }

    public TrackingParticleOptions(ParticleType<TrackingParticleOptions> type, float scale, int entityId) {
        this.type = type;
        this.scale = scale;
        this.entityId = entityId;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public @NonNull ParticleType<?> getType() {
        return type;
    }
}
