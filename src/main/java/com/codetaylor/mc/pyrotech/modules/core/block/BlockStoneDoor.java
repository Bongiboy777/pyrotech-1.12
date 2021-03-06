package com.codetaylor.mc.pyrotech.modules.core.block;

import com.codetaylor.mc.pyrotech.modules.core.ModuleCore;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStoneDoor
    extends BlockDoor {

  public static final String NAME = "stone_door";

  public BlockStoneDoor() {

    super(Material.ROCK);
    this.setHardness(3);
    this.setSoundType(SoundType.STONE);
    this.disableStats();
  }

  @Override
  public MapColor getMapColor(
      IBlockState state, IBlockAccess worldIn, BlockPos pos
  ) {

    return MapColor.STONE;
  }

  @Override
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

    return new ItemStack(this.getItem());
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
  }

  public Item getItem() {

    return ModuleCore.Items.STONE_DOOR;
  }
}
