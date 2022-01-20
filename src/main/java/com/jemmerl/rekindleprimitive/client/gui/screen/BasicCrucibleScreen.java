package com.jemmerl.rekindleprimitive.client.gui.screen;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import com.jemmerl.rekindleprimitive.inventory.container.BasicCrucibleContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BasicCrucibleScreen extends ContainerScreen<BasicCrucibleContainer> {
    private final ResourceLocation GUI = new ResourceLocation(RekindlePrimitive.MOD_ID, "textures/gui/basic_crucible_gui_1.png");

    public BasicCrucibleScreen(BasicCrucibleContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    // GUI images should be 256x256; the u/v offset tells where to read from, i/j tells where to draw it, last two are dim of thing to draw
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }
}
