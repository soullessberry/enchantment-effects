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
import soullessberry.enchantmenteffects.particles.ScalableParticleOptions;

public class EnchantmentEffects implements ModInitializer {
	public static final String MOD_ID = "enchantment-effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SoundEvent BANE_SOUND = registerSound("bane");
	public static final SoundEvent SLASH_SOUND = registerSound("slash");
	public static final SoundEvent SMITE_SOUND = registerSound("smite");

	public static ParticleType<ScalableParticleOptions> BANE_PARTICLE = registerScalableParticle("bane");
	public static ParticleType<ScalableParticleOptions> BEAM_PARTICLE = registerScalableParticle("beam");
	public static ParticleType<ScalableParticleOptions> BEAM_IMPACT_PARTICLE = registerScalableParticle("beam_impact");
	public static ParticleType<ScalableParticleOptions> SLASH_PARTICLE = registerScalableParticle("slash");
	public static ParticleType<ScalableParticleOptions> THORNS_PARTICLE = registerScalableParticle("thorns");

	@Override
	public void onInitialize() {

	}

	private static ParticleType<ScalableParticleOptions> registerScalableParticle(String name) {
		return Registry.register(
				BuiltInRegistries.PARTICLE_TYPE,
				id(name),
				FabricParticleTypes.complex(ScalableParticleOptions::codec, ScalableParticleOptions::streamCodec)
		);
	}

	private static SoundEvent registerSound(String name) {
		Identifier id = id(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	private static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}