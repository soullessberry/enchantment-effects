package soullessberry.enchantmenteffects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import soullessberry.enchantmenteffects.particles.BeamImpactParticleEffect;
import soullessberry.enchantmenteffects.particles.BeamParticleEffect;

import static soullessberry.enchantmenteffects.EnchantmentEffects.LOGGER;

public class EffectHandler {
    private static final RandomSource random = RandomSource.create();
    private static final int HEIGHT = 10;

    public static void applyEffects(Player player, Entity target) {
        attemptSmite(player, target);
    }

    private static void attemptSmite(Player player, Entity target) {
        int smiteLevel = getSmiteLevel(player.getWeaponItem());
        if (target.is(EntityTypeTags.UNDEAD) && smiteLevel > 0) {
            smiteEntity(target, smiteLevel);
        }
    }

    private static int getSmiteLevel(ItemInstance item) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            LOGGER.warn("getSmiteLevel was called on a null Level instance");
            return -1;
        }
        Holder<Enchantment> smiteHolder = level.registryAccess().getOrThrow(Enchantments.SMITE);
        return EnchantmentHelper.getItemEnchantmentLevel(smiteHolder,item);
    }

    private static void smiteEntity(Entity target, int smiteLevel) {
        playSmiteSound(
                target.position(),
                (0.7F + (0.1F * smiteLevel)),
                (random.triangle(1.15F - (0.05F * smiteLevel), 0.1F))
        );
        spawnBeamParticles(target.level(), target.position(), (float) smiteLevel / 3);
        spawnBeamImpactParticle(target.level(), target.position(), (float) smiteLevel / 3);
    }

    private static void playSmiteSound(Vec3 pos, float volume, float pitch) {
        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(
                EnchantmentEffects.SMITE_SOUND,
                SoundSource.PLAYERS,
                volume,
                pitch,
                SoundInstance.createUnseededRandom(),
                BlockPos.containing(pos)
        ));
    }

    private static void spawnBeamParticles(Level level, Vec3 pos, float scale) {
        float step = scale * 2;
        double base = pos.y + scale;

        int num_particles = Mth.ceil(HEIGHT / scale * 2);

        for (int i=0; i < num_particles; ++i) {
            level.addParticle(new BeamParticleEffect(scale), true, true, pos.x, base + (i * step), pos.z, 0, 0, 0);
        }
    }

    private static void spawnBeamImpactParticle(Level level, Vec3 pos, float scale) {
        level.addParticle(new BeamImpactParticleEffect(scale), true, true, pos.x, pos.y + 0.01, pos.z, 0, 0, 0);
    }
}
