package soullessberry.enchantmenteffects.particles;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.api.ScaleProvider;

public class AbstractFloorParticle extends AbstractScalableParticle {

    private static final float NINETY_DEGREES = (float) Math.toRadians(-90f);
    private boolean mirrored = false;

    public <T extends ScaleProvider> AbstractFloorParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites
    ) {
        super(level, x, y, z, xa, ya, za, params, sprites);
    }

    @Override
    public void extract(final @NonNull QuadParticleRenderState particleTypeRenderState, final @NonNull Camera camera, final float partialTickTime) {
        Quaternionf rotation = new Quaternionf();
        rotation.rotationX(NINETY_DEGREES);
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
        rotation.rotationX(NINETY_DEGREES * 3);
        mirrored = true;
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
        mirrored = false;
    }

    @Override
    protected float getV0() {
        return mirrored ? super.getV1() : super.getV0();
    }

    @Override
    protected float getV1() {
        return mirrored ? super.getV0() : super.getV1();
    }
}
