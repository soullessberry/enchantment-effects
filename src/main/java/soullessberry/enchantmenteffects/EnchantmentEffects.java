package soullessberry.enchantmenteffects;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soullessberry.enchantmenteffects.particles.BeamImpactParticleEffect;
import soullessberry.enchantmenteffects.particles.BeamParticleEffect;

public class EnchantmentEffects implements ModInitializer {
	public static final String MOD_ID = "enchantment-effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SoundEvent SMITE_SOUND = registerSound(id("smite"));

	public static ParticleType<BeamParticleEffect> BEAM_PARTICLE =
			FabricParticleTypes.complex(BeamParticleEffect.CODEC, BeamParticleEffect.PACKET_CODEC);
	public static ParticleType<BeamImpactParticleEffect> BEAM_IMPACT_PARTICLE =
			FabricParticleTypes.complex(BeamImpactParticleEffect.CODEC, BeamImpactParticleEffect.PACKET_CODEC);

	@Override
	public void onInitialize() {
		registerParticle(id("beam"), BEAM_PARTICLE);
		registerParticle(id("beam_impact"), BEAM_IMPACT_PARTICLE);
	}

	public static void registerParticle(Identifier id, ParticleType<?> particle) {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, particle);
	}

	public static SoundEvent registerSound(Identifier id) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	private static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}