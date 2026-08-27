package soullessberry.enchantmenteffects;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import soullessberry.enchantmenteffects.particles.*;

public class EnchantmentEffectsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerParticle(EnchantmentEffects.BANE_PARTICLE, BaneParticle.Provider::new);
        registerParticle(EnchantmentEffects.BEAM_PARTICLE, BeamParticle.Provider::new);
        registerParticle(EnchantmentEffects.BEAM_IMPACT_PARTICLE, BeamImpactParticle.Provider::new);
        registerParticle(EnchantmentEffects.SLASH_PARTICLE, SlashParticle.Provider::new);
        registerParticle(EnchantmentEffects.THORNS_PARTICLE, ThornsParticle.Provider::new);
        registerParticle(EnchantmentEffects.THORN_RING_PARTICLE, ThornRingParticle.Provider::new);
    }

    private static <T extends ParticleOptions> void registerParticle(
            ParticleType<T> type,
            ParticleFactoryRegistry.PendingParticleFactory<T> provider
    ) {
        ParticleFactoryRegistry.getInstance().register(type, provider);
    }
}
