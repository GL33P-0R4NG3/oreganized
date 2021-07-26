package me.gleep.oreganized.entities.entityrenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;

import me.gleep.oreganized.blocks.StoneSign;
import me.gleep.oreganized.entities.tileentities.StoneSignTileEntity;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

@OnlyIn(Dist.CLIENT)
public class StoneSignRenderer extends TileEntityRenderer<StoneSignTileEntity> {
    public StoneSignRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(StoneSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.push();
        float f = 0.6666667F;
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.translate(0.0D, -0.3125D, 0.5D);

        matrixStackIn.push();
        matrixStackIn.scale(0.6666667F, -0.6666667F, -0.6666667F);
        matrixStackIn.pop();
        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        float f2 = 0.010416667F;
        matrixStackIn.translate(0.0D, (double)0.33333334F, 0.04D);
        matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = tileEntityIn.getTextColor().getTextColor();
        double d0 = 0.4D;
        int j = (int)((double)NativeImage.getRed(i) * 0.4D);
        int k = (int)((double)NativeImage.getGreen(i) * 0.4D);
        int l = (int)((double)NativeImage.getBlue(i) * 0.4D);
        int i1 = NativeImage.getCombined(0, l, k, j);
        int j1 = 20;

        for(int k1 = 0; k1 < 6; ++k1) {
            IReorderingProcessor ireorderingprocessor = tileEntityIn.func_242686_a(k1, (p_243502_1_) -> {
                List<IReorderingProcessor> list = fontrenderer.trimStringToWidth(p_243502_1_, 80);
                return list.isEmpty() ? IReorderingProcessor.field_242232_a : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.func_243245_a(ireorderingprocessor) / 2);
                fontrenderer.func_238416_a_(ireorderingprocessor, f3, (float)(k1 * 10 - 20), i1, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
            }
        }

        matrixStackIn.pop();
    }
}
