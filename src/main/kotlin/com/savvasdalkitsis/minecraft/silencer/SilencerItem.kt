package com.savvasdalkitsis.minecraft.silencer

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

class SilencerItem : Item(Properties().apply {
    maxDamage(0)
    maxStackSize(64)
    setNoRepair()
    group(ItemGroup.MISC)
})