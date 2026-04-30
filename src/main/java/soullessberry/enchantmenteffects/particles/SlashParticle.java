package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class SlashParticle extends AbstractScalableParticle {
    private static final RandomSource random = RandomSource.create();
    private static final float HUNDRED_EIGHTY_DEGREES = (float) Math.toRadians(180f);

    private final float angle;

    public <T extends AbstractScalableParticleEffect> SlashParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.lifetime = 5;
        this.angle = random.nextFloat() * HUNDRED_EIGHTY_DEGREES;
    }

    @Override
    public void extract(final @NonNull QuadParticleRenderState particleTypeRenderState, final @NonNull Camera camera, final float partialTickTime) {
        Quaternionf rotation = new Quaternionf();
        this.getFacingCameraMode().setRotation(rotation, camera, partialTickTime);
        rotation.rotateZ(angle);
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SlashParticleEffect> {
        @Override
        public Particle createParticle(@NonNull SlashParticleEffect options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new SlashParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
