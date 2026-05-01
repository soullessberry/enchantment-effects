package soullessberry.enchantmenteffects;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import soullessberry.enchantmenteffects.particles.*;

public class EnchantmentEffectsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleProviderRegistry.getInstance().register(EnchantmentEffects.BANE_PARTICLE, BaneParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(EnchantmentEffects.BEAM_PARTICLE, BeamParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(EnchantmentEffects.BEAM_IMPACT_PARTICLE, BeamImpactParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(EnchantmentEffects.SLASH_PARTICLE, SlashParticle.Provider::new);
    }
}
