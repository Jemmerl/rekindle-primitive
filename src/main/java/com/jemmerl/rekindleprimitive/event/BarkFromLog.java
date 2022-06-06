package com.jemmerl.rekindleprimitive.event;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import com.jemmerl.rekindleprimitive.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RekindlePrimitive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BarkFromLog extends AxeItem {

    public BarkFromLog(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolInteractEvent event) {
        ItemStack heldItem = event.getHeldItemStack();
        PlayerEntity player = event.getPlayer();
        BlockState blockState = event.getState();
        if ((heldItem.getItem().getToolTypes(heldItem).contains(ToolType.AXE)) &&
                ((blockState.isIn(BlockTags.LOGS)) &&
                        !(blockState.getBlock().getRegistryName().toString().contains("stripped")))) {
            World world = (event.getPlayer()).getEntityWorld();
            if (world.rand.nextFloat() > 0.6F) {
                ItemStack itemStack = new ItemStack((IItemProvider) ModItems.BARK_STRIPS.get(),1);
                BlockPos pos = rayTrace(world, player, event.getPos());
                world.addEntity((Entity)new ItemEntity(world,
                        (pos.getX() + 0.5F), (pos.getY() + 0.3F), (pos.getZ() + 0.5F), itemStack));
            }
        }
    }
//AxeItem.BLOCK_STRIPPING_MAP.containsKey(event.getState().getBlock())
    private static BlockPos rayTrace(World world, PlayerEntity player, BlockPos pos) {
        Vector3d eyePos = player.getEyePosition(1.0F);
        Vector3d lookPos = player.getLook(1.0F);
        double length = player.getAttributeValue((Attribute) ForgeMod.REACH_DISTANCE.get()) + 1.0D;
        Vector3d endPos = eyePos.add(lookPos.getX() * length, lookPos.getY() * length, lookPos.getZ() * length);
        RayTraceContext context = new RayTraceContext(eyePos, endPos, RayTraceContext.BlockMode.OUTLINE,
                RayTraceContext.FluidMode.NONE, (Entity)player);
        BlockRayTraceResult result = world.rayTraceBlocks(context);
        Direction side = result.getFace();
        return pos.offset(side);
    }
}
