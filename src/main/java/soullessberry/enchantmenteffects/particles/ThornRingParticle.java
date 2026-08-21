package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

public class ThornRingParticle extends AbstractFloorParticle {

    private final int entityId;

    public <T extends TrackingParticleOptions> ThornRingParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites
    ) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.entityId = params.getEntityId();
        this.lifetime = 5;
    }

    @Override
    protected void extractRotatedQuad(
            @NonNull QuadParticleRenderState particleTypeRenderState,
            @NonNull Camera camera,
            @NonNull Quaternionf rotation,
            float partialTickTime
    ) {
        Entity entity = level.getEntity(entityId);
        Entity cameraEntity = camera.entity();
        CameraType cameraType = Minecraft.getInstance().options.getCameraType();
        if (entity != null && (!cameraType.isFirstPerson() || (cameraEntity != null && cameraEntity.getId() != entityId))) {
            Vec3 pos = camera.position();
            float offset = entity.getDimensions(entity.getPose()).height() / 2;
            float x = (float)(Mth.lerp(partialTickTime, entity.xo, entity.getX()) - pos.x());
            float y = (float)(Mth.lerp(partialTickTime, entity.yo + offset, entity.getY() + offset) - pos.y());
            float z = (float)(Mth.lerp(partialTickTime, entity.zo, entity.getZ()) - pos.z());
            this.extractRotatedQuad(particleTypeRenderState, rotation, x, y, z, partialTickTime);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<TrackingParticleOptions> {
        @Override
        public Particle createParticle(
                @NonNull TrackingParticleOptions options,
                @NonNull ClientLevel level,
                double x, double y, double z,
                double xAux, double yAux, double zAux,
                @NonNull RandomSource random
        ) {
            return new ThornRingParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
