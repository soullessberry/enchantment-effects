package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BeamParticle extends AbstractScalableParticle {
    public BeamParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            BeamParticleEffect params,
            SpriteSet sprites
    ) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.lifetime = 7;
    }

    @Override
    public @NonNull FacingCameraMode getFacingCameraMode() {
        return SingleQuadParticle.FacingCameraMode.LOOKAT_Y;
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<BeamParticleEffect> {
        @Override
        public Particle createParticle(@NonNull BeamParticleEffect options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new BeamParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
