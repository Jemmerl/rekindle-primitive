package com.jemmerl.rekindleprimitive.event;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import com.jemmerl.rekindleprimitive.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RekindlePrimitive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HotBrickSpawn {

    @SubscribeEvent
    public static void onWorldJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (event.getEntity() instanceof ItemEntity) {
            ItemEntity iEntity = (ItemEntity) entity;
            if (iEntity.getItem().isItemEqual(ModItems.HOT_BRICK.get().getDefaultInstance())) {
                if (event.getWorld().rand.nextFloat() > 0.5F) {
                    iEntity.setItem(Items.BRICK.getDefaultInstance());
                } else {
                    iEntity.remove();
                }
            }
        }
    }
}
