package com.codetaylor.mc.pyrotech.modules.tech.machine.recipe.spi;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class MachineRecipeItemInBase<T extends IForgeRegistryEntry<T>>
    extends MachineRecipeBase<T> {

  protected final Ingredient input;

  public MachineRecipeItemInBase(Ingredient input, int timeTicks) {

    super(timeTicks);

    this.input = input;
  }

  public Ingredient getInput() {

    return this.input;
  }

  public boolean matches(ItemStack input) {

    return this.input.apply(input);
  }
}
