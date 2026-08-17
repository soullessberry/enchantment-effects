package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class ThornsParticle extends AbstractBillboardParticle {

    public <T extends ScalableParticleOptions> ThornsParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites
    ) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.lifetime = 7;
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<ScalableParticleOptions> {
        @Override
        public Particle createParticle(
                @NonNull ScalableParticleOptions options,
                @NonNull ClientLevel level,
                double x, double y, double z,
                double xAux, double yAux, double zAux,
                @NonNull RandomSource random
        ) {
            return new ThornsParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
