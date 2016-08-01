package com.LegoF4.ElytraJetPack.blocks;

import javax.annotation.Nullable;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.gui.GuiHandler;
import com.LegoF4.ElytraJetPack.items.ItemManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmorTableBlock extends Block implements ITileEntityProvider{
	
	public ArmorTableBlock(String unlocalizedName) {
        super(Material.IRON);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(ItemManager.tabElytraJet);
        this.setHardness(5.2F);
        this.setResistance(40);
        this.setHarvestLevel("pickaxe", 2);
        this.setRegistryName("armorTable");
        this.isBlockContainer = true;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		ArmorTableTileEntity armorTable = new ArmorTableTileEntity();
		
		return armorTable;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		ArmorTableTileEntity te = (ArmorTableTileEntity) world.getTileEntity(pos);
	    InventoryHelper.dropInventoryItems(world, pos, te);
	    super.breakBlock(world, pos, blockstate);
	    world.removeTileEntity(pos);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    if (stack.hasDisplayName()) {
	        ((ArmorTableTileEntity) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
	    }
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if (!worldIn.isRemote) {
	        playerIn.openGui(Main.instance, GuiHandler.ARMOR_TABLE_TILE_ENTITY_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
    }
	
}
