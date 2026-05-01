package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BaneParticle extends AbstractScalableParticle {

    public <T extends AbstractScalableParticleEffect> BaneParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.lifetime = 7;
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<BaneParticleEffect> {
        @Override
        public Particle createParticle(@NonNull BaneParticleEffect options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new BaneParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
