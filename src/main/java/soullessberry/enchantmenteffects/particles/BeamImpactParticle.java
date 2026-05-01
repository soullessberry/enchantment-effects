package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

public class BeamImpactParticle extends AbstractScalableParticle {

    private static final float NINETY_DEGREES = (float) Math.toRadians(90f);

    public <T extends AbstractScalableParticleEffect> BeamImpactParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, T params, SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.lifetime = 7;
    }

    @Override
    public void extract(final @NonNull QuadParticleRenderState particleTypeRenderState, final @NonNull Camera camera, final float partialTickTime) {
        Quaternionf rotation = new Quaternionf();
        rotation.rotationX(NINETY_DEGREES);
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
        rotation.rotationX(NINETY_DEGREES * 3);
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<BeamImpactParticleEffect> {
        @Override
        public Particle createParticle(@NonNull BeamImpactParticleEffect options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new BeamImpactParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
