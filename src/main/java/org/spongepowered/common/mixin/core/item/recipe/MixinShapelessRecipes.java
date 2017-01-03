/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.mixin.core.item.recipe;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.recipe.RecipeRegistry;
import org.spongepowered.api.item.recipe.crafting.ShapelessCraftingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.SpongeImpl;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ShapelessRecipes.class)
public abstract class MixinShapelessRecipes implements ShapelessCraftingRecipe {

    @Shadow @Final private ItemStack recipeOutput;
    @Shadow @Final private List<ItemStack> recipeItems;

    @Override
    public ImmutableCollection<ItemStackSnapshot> getResults() {
        return ImmutableSet.of(ItemStackUtil.snapshotOf(recipeOutput));
    }

    @Override
    public ImmutableCollection<ItemStackSnapshot> getIngredients() {
        return recipeItems.stream().map(stack -> ItemStackUtil.snapshotOf(stack)).collect(
                Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    @Override
    public RecipeRegistry<?> getRegistry() {
        return SpongeImpl.getRegistry().getCraftingRegistry();
    }

}
