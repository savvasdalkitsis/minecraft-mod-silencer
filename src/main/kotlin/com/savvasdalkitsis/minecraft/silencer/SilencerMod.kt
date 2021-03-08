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

    init {
        MinecraftForge.EVENT_BUS.register(this)
        with (DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)) {
            register("silencer") { SilencerItem() }
            register(KotlinModLoadingContext.get().getKEventBus())
        }
        with (DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID)) {
            register("shh") { shh }
            register(KotlinModLoadingContext.get().getKEventBus())
        }
    }

    @SubscribeEvent
    fun entityInteract(e: PlayerInteractEvent.EntityInteractSpecific) {
        if (!e.target.isSilent && e.itemStack.item is SilencerItem) {
            e.target.isSilent = true
            e.itemStack.shrink(1)
            e.cancellationResult = ActionResultType.SUCCESS
            e.isCanceled = true
            e.player.playSound(shh, 1f, 1f)
        }
    }
}