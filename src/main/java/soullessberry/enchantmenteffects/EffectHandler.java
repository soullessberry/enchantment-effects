package soullessberry.enchantmenteffects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import soullessberry.enchantmenteffects.particles.BaneParticleEffect;
import soullessberry.enchantmenteffects.particles.BeamImpactParticleEffect;
import soullessberry.enchantmenteffects.particles.BeamParticleEffect;
import soullessberry.enchantmenteffects.particles.SlashParticleEffect;

import static soullessberry.enchantmenteffects.EnchantmentEffects.LOGGER;

public class EffectHandler {
    private static final RandomSource random = RandomSource.create();
    private static final int BEAM_HEIGHT = 10;

    public static void applyEffects(Player player, Entity target) {
        attemptBaneOfArthropods(player, target);
        attemptFireAspect(player, target);
        attemptSharpness(player, target);
        attemptSmite(player, target);
    }

    private static void attemptBaneOfArthropods(Player player, Entity target) {
        int baneLevel = getEnchantmentLevel(player.getWeaponItem(), Enchantments.BANE_OF_ARTHROPODS);
        if (target.is(EntityTypeTags.ARTHROPOD) && baneLevel > 0) {
            applyBaneEffect(target, baneLevel);
        }
    }

    private static void attemptFireAspect(Player player, Entity target) {
        int fireLevel = getEnchantmentLevel(player.getWeaponItem(), Enchantments.FIRE_ASPECT);
        if (!target.fireImmune() && fireLevel > 0) {
            applyFireEffect(target, fireLevel);
        }
    }

    private static void attemptSharpness(Player player, Entity target) {
        int sharpnessLevel = getEnchantmentLevel(player.getWeaponItem(), Enchantments.SHARPNESS);
        if (sharpnessLevel > 0) {
            applySharpnessEffect(target, sharpnessLevel);
        }
    }

    private static void attemptSmite(Player player, Entity target) {
        int smiteLevel = getEnchantmentLevel(player.getWeaponItem(), Enchantments.SMITE);
        if (target.is(EntityTypeTags.UNDEAD) && smiteLevel > 0) {
            applySmiteEffect(target, smiteLevel);
        }
    }

    private static int getEnchantmentLevel(ItemInstance item, ResourceKey<Enchantment> enchantment) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            LOGGER.warn("getEnchantmentLevel was called on a null Level instance");
            return -1;
        }
        Holder<Enchantment> enchantmentHolder = level.registryAccess().getOrThrow(enchantment);
        return EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder,item);
    }

    private static void applyBaneEffect(Entity target, int baneLevel) {
        playSoundEffect(
                EnchantmentEffects.BANE_SOUND,
                target.position(),
                (0.7F + (0.1F * baneLevel)),
                (random.triangle(1.15F - (0.05F * baneLevel), 0.1F))
        );

        float offset = target.getDimensions(target.getPose()).height() / 2 + 0.25f;
        float scale = 0.15f * baneLevel + 0.25f;
        spawnBaneParticle(target.level(), target.position().add(0, offset, 0), scale);
    }

    private static void applyFireEffect(Entity target, int fireLevel) {
        playSoundEffect(
                SoundEvents.FIRECHARGE_USE,
                target.position(),
                (0.4F + (0.2F * fireLevel)),
                (random.triangle(0.8F - (0.1F * fireLevel), 0.1F))
        );
        float height = target.getDimensions(target.getPose()).height();
        spawnFireParticles(target.level(), target.position(), fireLevel, height);
    }

    private static void applySharpnessEffect(Entity target, int sharpnessLevel) {
        playSoundEffect(
                EnchantmentEffects.SLASH_SOUND,
                target.position(),
                (0.7F + (0.1F * sharpnessLevel)),
                (random.triangle(1.15F - (0.05F * sharpnessLevel), 0.1F))
        );

        float offset = target.getDimensions(target.getPose()).height() / 2;
        float scale = 0.1f * sharpnessLevel + 0.5f;
        spawnSlashParticle(target.level(), target.position().add(0, offset, 0), scale);
    }

    private static void applySmiteEffect(Entity target, int smiteLevel) {
        playSoundEffect(
                EnchantmentEffects.SMITE_SOUND,
                target.position(),
                (0.5F + (0.1F * smiteLevel)),
                (random.triangle(1.15F - (0.05F * smiteLevel), 0.1F))
        );
        float scale = 0.15f * smiteLevel + 0.25f;
        spawnBeamParticles(target.level(), target.position(), scale);
        spawnBeamImpactParticle(target.level(), target.position(), scale);
    }

    private static void playSoundEffect(SoundEvent sound, Vec3 pos, float volume, float pitch) {
        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(
                sound,
                SoundSource.PLAYERS,
                volume,
                pitch,
                SoundInstance.createUnseededRandom(),
                BlockPos.containing(pos)
        ));
    }

    private static void spawnBaneParticle(Level level, Vec3 pos, float scale) {
        level.addParticle(new BaneParticleEffect(scale), true, true, pos.x, pos.y, pos.z, 0, 0.1F, 0);
    }

    private static void spawnBeamParticles(Level level, Vec3 pos, float scale) {
        float step = scale * 2;
        double base = pos.y + scale;

        int num_particles = Mth.ceil(BEAM_HEIGHT / scale * 2);

        for (int i=0; i < num_particles; ++i) {
            level.addParticle(new BeamParticleEffect(scale), true, true, pos.x, base + (i * step), pos.z, 0, 0, 0);
        }
    }

    private static void spawnBeamImpactParticle(Level level, Vec3 pos, float scale) {
        level.addParticle(new BeamImpactParticleEffect(scale), true, true, pos.x, pos.y + 0.01, pos.z, 0, 0, 0);
    }

    private static void spawnFireParticles(Level level, Vec3 pos, int count, float height) {
        final double STEP = height / (count + 1);
        for (int i=1; i < count+1; ++i) {
            spawnFireRingParticles(level, pos.add(0, i * STEP, 0));
        }
    }

    private static void spawnFireRingParticles(Level level, Vec3 pos) {
        final int COUNT = 8;
        final double VEL = 0.1;
        final double STEP = 2 * Math.PI / COUNT;

        for (int i=0; i < COUNT; ++i) {
            double radians = STEP * i;
            double xVel = Math.sin(radians) * VEL;
            double zVel = Math.cos(radians) * VEL;
            double yVel = random.triangle(0, 0.01);
            level.addParticle(ParticleTypes.FLAME, true, true, pos.x, pos.y, pos.z, xVel, yVel, zVel);
        }
    }

    private static void spawnSlashParticle(Level level, Vec3 pos, float scale) {
        level.addParticle(new SlashParticleEffect(scale), true, true, pos.x, pos.y, pos.z, 0, 0, 0);
    }
}
