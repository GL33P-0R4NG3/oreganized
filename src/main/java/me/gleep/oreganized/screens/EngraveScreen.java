package me.gleep.oreganized.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.util.GeneralUtility;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EngraveScreen extends Screen{

    private final String[] lines = new String[7];

    private int currentLine;

    private TextFieldHelper textField;

    private int frame;
    private final IEngravedBlocks cap;
    private final BlockPos blockPos;
    private final EngravedBlocks.Face clickedFace;
    private Block clickedBlock;
    private int textColor;

    public EngraveScreen( IEngravedBlocks cap , BlockPos blockPos , EngravedBlocks.Face clickedFace, Block clickedBlock ){
        super( new TranslatableComponent( "engraving.edit" ) );
        this.cap = cap;
        this.blockPos = blockPos;
        this.clickedFace = clickedFace;
        this.clickedBlock = clickedBlock;
        this.textColor = GeneralUtility.getBrightestColorFromBlock( Minecraft.getInstance().level.getBlockState( blockPos ).getBlock(), blockPos );
        int i = 0;
        for(String line : lines){
            this.lines[i] = "";
            i++;
        }
    }

    @Override
    public boolean keyPressed( int pKeyCode , int pScanCode , int pModifiers ){
        textColor = GeneralUtility.getBrightestColorFromBlock( Minecraft.getInstance().level.getBlockState( blockPos ).getBlock(), blockPos );
        if(pKeyCode == 265){
            this.currentLine = this.currentLine == 0 ? 6 : this.currentLine - 1;
            this.textField.setCursorToEnd();
            return true;
        }else if(pKeyCode != 264 && pKeyCode != 257 && pKeyCode != 335){
            return this.textField.keyPressed( pKeyCode ) ? true : super.keyPressed( pKeyCode , pScanCode , pModifiers );
        }else{
            this.currentLine = this.currentLine == 6 ? 0 : this.currentLine + 1;
            this.textField.setCursorToEnd();
            return true;
        }
    }

    @Override
    protected void init(){
        this.minecraft.keyboardHandler.setSendRepeatsToGui( true );
        this.addRenderableWidget( new Button( this.width / 2 - 100 , this.height / 4 + 120 , 200 , 20 , CommonComponents.GUI_DONE , ( p_169820_ ) -> {
            this.onDone();
        } ) );
        this.textField = new TextFieldHelper( () -> {
            return this.lines[this.currentLine];
        } , ( p_169824_ ) -> {
            this.lines[this.currentLine] = p_169824_;
        } , TextFieldHelper.createClipboardGetter( this.minecraft ) , TextFieldHelper.createClipboardSetter( this.minecraft ) ,
                ( p_169822_ ) -> this.minecraft.font.width( p_169822_ ) <= 75 );
    }

    @Override
    public void removed(){
        super.removed();
    }

    @Override
    public void tick(){
        frame++;
        if ((this.minecraft.level.getBlockState( blockPos ).getBlock() == Blocks.AIR) && frame > 20 ) {
            this.onDone();
        }
    }

    @Override
    public void onClose(){
        this.onDone();
    }

    private void onDone(){
        cap.setFaceString( blockPos , clickedFace , makeString( lines ) );
        this.minecraft.setScreen( (Screen) null );
    }

    private String makeString( String[] stringArray ){
        StringBuilder s = new StringBuilder();
        for(String s1 : stringArray){
            s.append( s1 ).append( "\n" );
        }
        return s.toString();
    }

    @Override
    public boolean charTyped( char pCodePoint , int pModifiers ){
        this.textField.charTyped( pCodePoint );
        return true;
    }

    @Override
    public void render( PoseStack pPoseStack , int pMouseX , int pMouseY , float pPartialTick ){
        Lighting.setupForFlatItems();
        this.renderBackground( pPoseStack );
        drawCenteredString( pPoseStack , this.font , this.title , this.width / 2 , 40 , 16777215 );
        pPoseStack.pushPose();
        pPoseStack.translate( (double) (this.width / 2 - 51.5) , 58 , 50.0D );
        pPoseStack.scale( 0.4f, 0.4f, 0.4f );
        String id = this.minecraft.getBlockRenderer().getBlockModelShaper().getTexture(clickedBlock.defaultBlockState(), this.minecraft.level, this.blockPos).getName().getNamespace();
        ResourceLocation texture = new ResourceLocation(id, "textures/block/" + clickedBlock.getRegistryName().getPath() + ".png" );
        this.minecraft.textureManager.bindForSetup( texture );
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, texture );
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(pPoseStack, 0, 0, 0, 0, 256, 256);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.translate( (double) (this.width / 2) , 0.0D , 50.0D );
        float f = 93.75F;
        pPoseStack.scale( 93.75F , -93.75F , 93.75F );
        pPoseStack.translate( 0.0D , -1.5325D , 0.0D );

        boolean flag1 = this.frame / 6 % 2 == 0;
        float f1 = 0.6666667F;
        pPoseStack.pushPose();
        pPoseStack.scale( 0.2666667F , -0.2666667F , -0.2666667F );
        MultiBufferSource.BufferSource multibuffersource$buffersource = this.minecraft.renderBuffers().bufferSource();
        pPoseStack.popPose();
        float f2 = 0.010416667F;
        pPoseStack.translate( 0.0D , (double) 0.33333334F , (double) 0.046666667F );
        pPoseStack.scale( 0.010416667F , -0.010416667F , 0.010416667F );
        int j = this.textField.getCursorPos();
        int k = this.textField.getSelectionPos();
        int l = this.currentLine * 9 - this.lines.length * 5;
        Matrix4f matrix4f = pPoseStack.last().pose();


        for(int i1 = 0; i1 < this.lines.length; ++i1){
            String s = this.lines[i1];
            if(s != null){
                if(this.font.isBidirectional()){
                    s = this.font.bidirectionalShaping( s );
                }

                float f3 = (float) (-this.minecraft.font.width( s ) / 2) - 0.5f;
                this.minecraft.font.drawInBatch( s , f3+1f , (float) (i1 * 9 - this.lines.length * 5) + 0.4f , this.minecraft.level.getBlockState( blockPos ).getBlock() != RegistryHandler.ENGRAVED_NETHER_BRICKS.get() ?  textColor : 4991023 , false , matrix4f , multibuffersource$buffersource , false , 0 , 15728880 , false );
                this.minecraft.font.drawInBatch( s , f3 , (float) (i1 * 9 - this.lines.length * 5) , this.minecraft.level.getBlockState( blockPos ).getBlock() != RegistryHandler.ENGRAVED_NETHER_BRICKS.get() ? GeneralUtility.modifyColorBrightness( textColor, -0.1f) : 787976 , false , matrix4f , multibuffersource$buffersource , false , 0 , 15728880 , false );
                if(i1 == this.currentLine && j >= 0 && flag1){
                    int j1 = this.minecraft.font.width( s.substring( 0 , Math.max( Math.min( j , s.length() ) , 0 ) ) );
                    int k1 = j1 - this.minecraft.font.width( s ) / 2;
                    if(j >= s.length()){
                        this.minecraft.font.drawInBatch( "_" , (float) k1 , (float) l , 0 , false , matrix4f , multibuffersource$buffersource , false , 0 , 15728880 , false );
                    }
                }
            }
        }

        multibuffersource$buffersource.endBatch();

        for(int i3 = 0; i3 < this.lines.length; ++i3){
            String s1 = this.lines[i3];
            if(s1 != null && i3 == this.currentLine && j >= 0){
                int j3 = this.minecraft.font.width( s1.substring( 0 , Math.max( Math.min( j , s1.length() ) , 0 ) ) );
                int k3 = j3 - this.minecraft.font.width( s1 ) / 2;
                if(flag1 && j < s1.length()){
                    fill( pPoseStack , k3 , l - 1 , k3 + 1 , l + 9 , -16777216 );
                }

                if(k != j){
                    int l3 = Math.min( j , k );
                    int l1 = Math.max( j , k );
                    int i2 = this.minecraft.font.width( s1.substring( 0 , l3 ) ) - this.minecraft.font.width( s1 ) / 2;
                    int j2 = this.minecraft.font.width( s1.substring( 0 , l1 ) ) - this.minecraft.font.width( s1 ) / 2;
                    int k2 = Math.min( i2 , j2 );
                    int l2 = Math.max( i2 , j2 );
                    Tesselator tesselator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tesselator.getBuilder();
                    RenderSystem.setShader( GameRenderer::getPositionColorShader );
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp( GlStateManager.LogicOp.OR_REVERSE );
                    bufferbuilder.begin( VertexFormat.Mode.QUADS , DefaultVertexFormat.POSITION_COLOR );
                    bufferbuilder.vertex( matrix4f , (float) k2 , (float) (l + 9) , 0.0F ).color( 0 , 0 , 255 , 255 ).endVertex();
                    bufferbuilder.vertex( matrix4f , (float) l2 , (float) (l + 9) , 0.0F ).color( 0 , 0 , 255 , 255 ).endVertex();
                    bufferbuilder.vertex( matrix4f , (float) l2 , (float) l , 0.0F ).color( 0 , 0 , 255 , 255 ).endVertex();
                    bufferbuilder.vertex( matrix4f , (float) k2 , (float) l , 0.0F ).color( 0 , 0 , 255 , 255 ).endVertex();
                    bufferbuilder.end();
                    BufferUploader.end( bufferbuilder );
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }
        pPoseStack.popPose();
        Lighting.setupFor3DItems();
        super.render( pPoseStack , pMouseX , pMouseY , pPartialTick );
    }
}
