package soullessberry.enchantmenteffects.particles;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;
import soullessberry.enchantmenteffects.api.ScaleProvider;

public class AbstractBillboardParticle extends AbstractScalableParticle {

    protected float dist;

    public <T extends ScaleProvider> AbstractBillboardParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        this.dist = .5f;
    }

    @Override
    protected void extractRotatedQuad(
            final @NonNull QuadParticleRenderState particleTypeRenderState, final Camera camera, final @NonNull Quaternionf rotation, final float partialTickTime
    ) {
        Vec3 camPos = camera.position();
        Vec3 oldPos = new Vec3(this.xo, this.yo, this.zo);
        Vec3 newPos = new Vec3(this.x, this.y, this.z);
        Vec3 oldDir = camPos.subtract(oldPos).normalize().scale(dist);
        Vec3 newDir = camPos.subtract(newPos).normalize().scale(dist);

        double oldX = this.xo + oldDir.x();
        double oldY = this.yo + oldDir.y();
        double oldZ = this.zo + oldDir.z();
        double newX = this.xo + newDir.x();
        double newY = this.yo + newDir.y();
        double newZ = this.zo + newDir.z();

        float x = (float)(Mth.lerp(partialTickTime, oldX, newX) - camPos.x());
        float y = (float)(Mth.lerp(partialTickTime, oldY, newY) - camPos.y());
        float z = (float)(Mth.lerp(partialTickTime, oldZ, newZ) - camPos.z());
        this.extractRotatedQuad(particleTypeRenderState, rotation, x, y, z, partialTickTime);
    }
}
