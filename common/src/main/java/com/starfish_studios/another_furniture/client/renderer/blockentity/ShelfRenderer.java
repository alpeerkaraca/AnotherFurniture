package com.starfish_studios.another_furniture.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.starfish_studios.another_furniture.block.ShelfBlock;
import com.starfish_studios.another_furniture.block.entity.ShelfBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

@Environment(value= EnvType.CLIENT)
public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

    public ShelfRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(ShelfBlock.FACING);
        NonNullList<ItemStack> items = blockEntity.getItems();
        for(int j = 0; j < items.size(); j++) {
            ItemStack stack = items.get(j);
            if (stack.isEmpty()) return;

            int renderCount = getAmount(stack.getCount());
            for (int i = 0; i < renderCount; ++i) {
                float fx = (-0.12375f * (float)(i - 1) * 0.5f) % 0.09f;
                float fy = (-0.08375f * (float)(i - 1) * 0.5f) % 0.09f;
                float fz = (-0.09375f * (float)(i - 1) * 0.5f) % 0.09f;

                poseStack.pushPose();

                poseStack.translate(0.5, 1.18, 0.5);
                poseStack.translate(-0.225 + 0.45 * (j % 2), 0.0, -0.225 + 0.45 * (j / 2));
                poseStack.translate(-0.061875f, 0.0, -0.046875f);
                poseStack.translate(fx, fy, fz);

                poseStack.mulPose(Vector3f.YP.rotationDegrees(-90F * direction.get2DDataValue()));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180F));

                poseStack.scale(0.375F, 0.375F, 0.375F);
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, packedLight, packedOverlay, poseStack, bufferSource, 0);

                poseStack.popPose();
            }

        }
    }

    public int getAmount(int count) {
        if (count > 48) return 5;
        if (count > 32) return 4;
        if (count > 16) return 3;
        if (count > 1) return 2;
        return 1;
    }
}