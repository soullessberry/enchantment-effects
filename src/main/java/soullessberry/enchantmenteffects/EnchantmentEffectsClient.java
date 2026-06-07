package soullessberry.enchantmenteffects;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import soullessberry.enchantmenteffects.particles.*;

public class EnchantmentEffectsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(EnchantmentEffects.BANE_PARTICLE, BaneParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(EnchantmentEffects.BEAM_PARTICLE, BeamParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(EnchantmentEffects.BEAM_IMPACT_PARTICLE, BeamImpactParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(EnchantmentEffects.SLASH_PARTICLE, SlashParticle.Provider::new);
    }
}
