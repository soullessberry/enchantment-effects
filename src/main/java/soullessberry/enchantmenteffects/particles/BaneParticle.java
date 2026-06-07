package soullessberry.enchantmenteffects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BaneParticle extends AbstractScalableParticle {

    private final float baseScale;

    public <T extends AbstractScalableParticleEffect> BaneParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            T params,
            SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, params, sprites);
        baseScale = params.getScale();
        this.lifetime = 7;
    }

    @Override
    public void tick() {
        super.tick();
        this.quadSize = (float) (baseScale * Math.log(age) / 4 + baseScale);
    }

    @Override
    protected void extractRotatedQuad(
            @NonNull QuadParticleRenderState quadParticleRenderState, Camera camera, @NonNull Quaternionf quaternionf, float f
    ) {
        Vec3 camPos = camera.position();
        Vec3 oldPos = new Vec3(this.xo, this.yo, this.zo);
        Vec3 newPos = new Vec3(this.x, this.y, this.z);
        Vec3 oldDir = camPos.subtract(oldPos).normalize().scale(.5);
        Vec3 newDir = camPos.subtract(newPos).normalize().scale(.5);

        double oldX = this.xo + oldDir.x();
        double oldY = this.yo + oldDir.y();
        double oldZ = this.zo + oldDir.z();
        double newX = this.xo + newDir.x();
        double newY = this.yo + newDir.y();
        double newZ = this.zo + newDir.z();

        float x = (float)(Mth.lerp(f, oldX, newX) - camPos.x());
        float y = (float)(Mth.lerp(f, oldY, newY) - camPos.y());
        float z = (float)(Mth.lerp(f, oldZ, newZ) - camPos.z());
        this.extractRotatedQuad(quadParticleRenderState, quaternionf, x, y, z, f);
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<BaneParticleEffect> {
        @Override
        public Particle createParticle(@NonNull BaneParticleEffect options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new BaneParticle(level, x, y, z, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
