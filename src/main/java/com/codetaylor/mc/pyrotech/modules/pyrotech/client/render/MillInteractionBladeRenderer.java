package com.codetaylor.mc.pyrotech.modules.pyrotech.client.render;

import com.codetaylor.mc.athenaeum.util.RenderHelper;
import com.codetaylor.mc.pyrotech.modules.pyrotech.interaction.api.InteractionRenderers;
import com.codetaylor.mc.pyrotech.modules.pyrotech.interaction.api.Transform;
import com.codetaylor.mc.pyrotech.modules.pyrotech.interaction.spi.IInteractionRenderer;
import com.codetaylor.mc.pyrotech.modules.pyrotech.tile.TileMillStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MillInteractionBladeRenderer
    implements IInteractionRenderer<TileMillStone.InteractionBlade> {

  public static final MillInteractionBladeRenderer INSTANCE = new MillInteractionBladeRenderer();

  @Override
  public void renderSolidPass(TileMillStone.InteractionBlade interaction, World world, RenderItem renderItem, BlockPos pos, IBlockState blockState, float partialTicks) {

    // If the handler is not empty, render the handler's item.

    if (!interaction.isEmpty()) {
      ItemStack itemStack = interaction.getStackInSlot();
      Transform transform = interaction.getTransform(world, pos, blockState, itemStack, partialTicks);

      GlStateManager.pushMatrix();
      {
        InteractionRenderers.setupItemTransforms(transform);

        if (interaction.getTile().workerIsActive()) {
          GlStateManager.rotate(System.currentTimeMillis() % 360, 0, 0, 1);
        }
        IBakedModel model = renderItem.getItemModelWithOverrides(itemStack, null, null);
        RenderHelper.renderItemModel(itemStack, model, ItemCameraTransforms.TransformType.NONE, false, false);
      }
      GlStateManager.popMatrix();
    }
  }

  @Override
  public boolean renderAdditivePass(TileMillStone.InteractionBlade interaction, World world, RenderItem renderItem, EnumFacing hitSide, Vec3d hitVec, BlockPos hitPos, IBlockState blockState, ItemStack heldItemMainHand, float partialTicks) {

    // If the handler is empty, render the held item.
    // Else, render the handler's item if the player's hand is empty.

    if (interaction.isEmpty()
        && !heldItemMainHand.isEmpty()) {

      // Only render the held item if it is valid for the handler.
      if (interaction.isItemStackValid(heldItemMainHand)) {
        Transform transform = interaction.getTransform(world, hitPos, blockState, heldItemMainHand, partialTicks);

        // Since only one item will be rendered, it is better to wrap the
        // GL setup calls as late as possible so we're not setting it up
        // if the item isn't going to be rendered.

        InteractionRenderers.setupAdditiveGLState();
        InteractionRenderers.renderItemModelCustom(renderItem, heldItemMainHand, transform);
        InteractionRenderers.cleanupAdditiveGLState();
        return true;
      }

    } else if (!interaction.isEmpty()
        && heldItemMainHand.isEmpty()) {

      ItemStack itemStack = interaction.getStackInSlot();
      Transform transform = interaction.getTransform(world, hitPos, blockState, itemStack, partialTicks);

      if (!itemStack.isEmpty()) {

        // Since only one item will be rendered, it is better to wrap the
        // GL setup calls as late as possible so we're not setting it up
        // if the item isn't going to be rendered.

        InteractionRenderers.setupAdditiveGLState();
        GlStateManager.pushMatrix();
        {
          InteractionRenderers.setupItemTransforms(transform);

          if (interaction.getTile().workerIsActive()) {
            GlStateManager.rotate(System.currentTimeMillis() % 360, 0, 0, 1);
          }
          IBakedModel model = renderItem.getItemModelWithOverrides(itemStack, null, null);
          RenderHelper.renderItemModelCustom(itemStack, model, ItemCameraTransforms.TransformType.NONE, false, false);
        }
        GlStateManager.popMatrix();
        InteractionRenderers.cleanupAdditiveGLState();
        return true;
      }
    }

    return false;
  }
}
