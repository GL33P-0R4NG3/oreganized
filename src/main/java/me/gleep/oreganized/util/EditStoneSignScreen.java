package me.gleep.oreganized.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.stream.IntStream;

import me.gleep.oreganized.entities.tileentities.StoneSignTileEntity;
import me.gleep.oreganized.util.messages.StoneSignToServerMessage;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditStoneSignScreen extends Screen {
    private final SignTileEntityRenderer.SignModel signModel = new SignTileEntityRenderer.SignModel();
    /** Reference to the sign object. */
    private final StoneSignTileEntity tileSign;
    /** Counts the number of screen updates. */
    private int updateCounter;
    /** The index of the line that is being edited. */
    private int editLine;
    private TextInputUtil textInputUtil;
    private final String[] text;

    public EditStoneSignScreen(StoneSignTileEntity teSign) {
        super(new TranslationTextComponent("stonesign.edit"));
        this.text = IntStream.range(0, 6).mapToObj(teSign::getText).map(ITextComponent::getString).toArray((p_243354_0_) -> {
            return new String[p_243354_0_];
        });
        this.tileSign = teSign;
    }

    @Override
    protected void init() {
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, DialogTexts.GUI_DONE, (p_238847_1_) -> {
            this.close();
        }));
        this.tileSign.setEditable(false);
        this.textInputUtil = new TextInputUtil(() -> {
            return this.text[this.editLine];
        }, (p_238850_1_) -> {
            this.text[this.editLine] = p_238850_1_;
            this.tileSign.setText(this.editLine, new StringTextComponent(p_238850_1_));
        }, TextInputUtil.getClipboardTextSupplier(Minecraft.getInstance()), TextInputUtil.getClipboardTextSetter(Minecraft.getInstance()), (p_238848_1_) -> {
            return Minecraft.getInstance().fontRenderer.getStringWidth(p_238848_1_) <= 80;
        });
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
        StoneSignToServerMessage msg = new StoneSignToServerMessage(
                this.tileSign.getPos(),
                this.text[0], this.text[1], this.text[2],
                this.text[3], this.text[4], this.text[5]
        );

        SimpleNetwork.CHANNEL.sendToServer(msg);

        this.tileSign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.updateCounter;
        if (!this.tileSign.getType().isValidBlock(this.tileSign.getBlockState().getBlock())) {
            this.close();
        }

    }

    private void close() {
        this.tileSign.markDirty();
        Minecraft.getInstance().displayGuiScreen((Screen)null);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        this.textInputUtil.putChar(codePoint);
        return true;
    }

    @Override
    public void closeScreen() {
        this.close();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) {
            this.editLine = this.editLine - 1 & 5;
            this.textInputUtil.moveCursorToEnd();
            return true;
        } else if (keyCode != 264 && keyCode != 257 && keyCode != 335) {
            return this.textInputUtil.specialKeyPressed(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            this.editLine = this.editLine + 1 & 5;
            this.textInputUtil.moveCursorToEnd();
            return true;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderHelper.setupGuiFlatDiffuseLighting();
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
        matrixStack.push();
        matrixStack.translate((double)(this.width / 2), 0.0D, 50.0D);
        float f = 93.75F;
        matrixStack.scale(93.75F, -93.75F, 93.75F);
        matrixStack.translate(0.0D, -1.3125D, 0.0D);
        BlockState blockstate = this.tileSign.getBlockState();
        matrixStack.translate(0.0D, -0.3125D, 0.0D);

        boolean flag1 = this.updateCounter / 6 % 2 == 0;
        float f1 = 0.6666667F;
        matrixStack.push();
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        RenderMaterial rendermaterial = Atlases.getSignMaterial(WoodType.OAK);
        IVertexBuilder ivertexbuilder = rendermaterial.getBuffer(irendertypebuffer$impl, this.signModel::getRenderType);
        this.signModel.signBoard.render(matrixStack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);

        matrixStack.pop();
        float f2 = 0.010416667F;
        matrixStack.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
        matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.tileSign.getTextColor().getTextColor();
        int j = this.textInputUtil.getEndIndex();
        int k = this.textInputUtil.getStartIndex();
        int l = this.editLine * 10 - this.text.length * 5;
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();

        for(int i1 = 0; i1 < this.text.length; ++i1) {
            String s = this.text[i1];
            if (s != null) {
                if (this.font.getBidiFlag()) {
                    s = this.font.bidiReorder(s);
                }

                float f3 = (float)(-Minecraft.getInstance().fontRenderer.getStringWidth(s) / 2);
                Minecraft.getInstance().fontRenderer.func_238411_a_(s, f3, (float)(i1 * 10 - this.text.length * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
                if (i1 == this.editLine && j >= 0 && flag1) {
                    int j1 = Minecraft.getInstance().fontRenderer.getStringWidth(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
                    int k1 = j1 - Minecraft.getInstance().fontRenderer.getStringWidth(s) / 2;
                    if (j >= s.length()) {
                        Minecraft.getInstance().fontRenderer.func_238411_a_("_", (float)k1, (float)l, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
                    }
                }
            }
        }

        irendertypebuffer$impl.finish();

        for(int i3 = 0; i3 < this.text.length; ++i3) {
            String s1 = this.text[i3];
            if (s1 != null && i3 == this.editLine && j >= 0) {
                int j3 = Minecraft.getInstance().fontRenderer.getStringWidth(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
                int k3 = j3 - Minecraft.getInstance().fontRenderer.getStringWidth(s1) / 2;
                if (flag1 && j < s1.length()) {
                    fill(matrixStack, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
                }

                if (k != j) {
                    int l3 = Math.min(j, k);
                    int l1 = Math.max(j, k);
                    int i2 = Minecraft.getInstance().fontRenderer.getStringWidth(s1.substring(0, l3)) - Minecraft.getInstance().fontRenderer.getStringWidth(s1) / 2;
                    int j2 = Minecraft.getInstance().fontRenderer.getStringWidth(s1.substring(0, l1)) - Minecraft.getInstance().fontRenderer.getStringWidth(s1) / 2;
                    int k2 = Math.min(i2, j2);
                    int l2 = Math.max(i2, j2);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos(matrix4f, (float)k2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)l2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)l2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)k2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.finishDrawing();
                    WorldVertexBufferUploader.draw(bufferbuilder);
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        matrixStack.pop();
        RenderHelper.setupGui3DDiffuseLighting();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
