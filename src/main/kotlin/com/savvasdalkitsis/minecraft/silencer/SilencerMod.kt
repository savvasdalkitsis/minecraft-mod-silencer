package com.savvasdalkitsis.minecraft.silencer

import net.minecraft.util.ActionResultType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.KotlinModLoadingContext


private const val MOD_ID = "silencer"

@Mod(MOD_ID)
class SilencerMod {
    private val shh = SoundEvent(ResourceLocation(MOD_ID, "shh"))
    private val whoo = SoundEvent(ResourceLocation(MOD_ID, "whoo"))

    init {
        MinecraftForge.EVENT_BUS.register(this)
        with (DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)) {
            register("silencer") { SilencerItem() }
            register("unsilencer") { UnsilencerItem() }
            register(KotlinModLoadingContext.get().getKEventBus())
        }
        with (DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID)) {
            register("shh") { shh }
            register("whoo") { whoo }
            register(KotlinModLoadingContext.get().getKEventBus())
        }
    }

    @SubscribeEvent
    fun entityInteract(e: PlayerInteractEvent.EntityInteractSpecific) {
        when {
            !e.target.isSilent && e.itemStack.item is SilencerItem -> e.use(true, shh)
            e.target.isSilent && e.itemStack.item is UnsilencerItem -> e.use(false, whoo)
        }
    }

    private fun PlayerInteractEvent.EntityInteractSpecific.use(silent: Boolean, soundEvent: SoundEvent) {
        target.isSilent = silent
        itemStack.shrink(1)
        cancellationResult = ActionResultType.SUCCESS
        isCanceled = true
        player.playSound(soundEvent, 1f, 1f)
    }
}