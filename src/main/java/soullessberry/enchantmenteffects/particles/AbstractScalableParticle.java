package soullessberry.enchantmenteffects.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import org.jspecify.annotations.NonNull;

public class AbstractScalableParticle extends SimpleAnimatedParticle {
    public <T extends AbstractScalableParticleEffect> AbstractScalableParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites
    ) {
        super(level, x, y, z, sprites, 0);
        this.setParticleSpeed(xa, ya, za);
        this.quadSize = params.getScale();
    }

    @Override
    public @NonNull Layer getLayer() {
        return Layer.TRANSLUCENT;
    }
}
