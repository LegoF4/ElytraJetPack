package com.LegoF4.ElytraJetPack.Items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Jetpack extends Item {
	
	public Jetpack (String unlocalizedName) {
		super();
		
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(ItemManager.tabElytraJet);
	    this.maxStackSize = 1;
        this.setMaxDamage(432);
        
        this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
            {
                return Jetpack.isBroken(stack) ? 0.0F : 1.0F;
            }
        });
        
        
	}
	
	public static boolean isBroken(ItemStack stack)
    {
        return stack.getItemDamage() < stack.getMaxDamage() - 1;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	if (playerIn.inventory.armorInventory[2] == null) {
    		EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemStackIn);
            ItemStack itemstack = playerIn.getItemStackFromSlot(entityequipmentslot);
            ItemStack itemStackOut = itemStackIn.copy();
            playerIn.inventory.armorInventory[2] = itemStackOut;
            itemStackIn.stackSize = 0;
            
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    	}
    	else {
    		return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    	}
        
        
    }
    
    

}
