package net.bukkit.elementalmaster.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.Enchantments;

import net.bukkit.elementalmaster.item.WaterMastersArmorItem;
import net.bukkit.elementalmaster.item.ThunderSwordItem;
import net.bukkit.elementalmaster.item.SuperElementalSwordItem;
import net.bukkit.elementalmaster.item.LightningMastersArmorItem;
import net.bukkit.elementalmaster.item.IceMastersArmorItem;
import net.bukkit.elementalmaster.item.FrozenSwordItem;
import net.bukkit.elementalmaster.item.FireMastersArmorItem;
import net.bukkit.elementalmaster.item.EarthMastersArmorItem;
import net.bukkit.elementalmaster.item.DemonicSwordItem;
import net.bukkit.elementalmaster.enchantment.WaterProtectionEnchantment;
import net.bukkit.elementalmaster.enchantment.LightningProtectionEnchantment;
import net.bukkit.elementalmaster.enchantment.LightningAspectEnchantment;
import net.bukkit.elementalmaster.enchantment.IceProtectionEnchantment;
import net.bukkit.elementalmaster.enchantment.IceAspectEnchantment;
import net.bukkit.elementalmaster.ElementalmasterModElements;

import java.util.Map;
import java.util.HashMap;

@ElementalmasterModElements.ModElement.Tag
public class ItemIsCraftedProcedure extends ElementalmasterModElements.ModElement {
	public ItemIsCraftedProcedure(ElementalmasterModElements instance) {
		super(instance, 698);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("itemstack") == null) {
			System.err.println("Failed to load dependency itemstack for procedure ItemIsCrafted!");
			return;
		}
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (((itemstack).getItem() == new ItemStack(DemonicSwordItem.block, (int) (1)).getItem())) {
			((itemstack)).addEnchantment(Enchantments.FIRE_ASPECT, (int) 3);
		}
		if (((itemstack).getItem() == new ItemStack(FrozenSwordItem.block, (int) (1)).getItem())) {
			((itemstack)).addEnchantment(IceAspectEnchantment.enchantment, (int) 3);
		}
		if (((itemstack).getItem() == new ItemStack(ThunderSwordItem.block, (int) (1)).getItem())) {
			((itemstack)).addEnchantment(LightningAspectEnchantment.enchantment, (int) 3);
		}
		if (((itemstack).getItem() == new ItemStack(SuperElementalSwordItem.block, (int) (1)).getItem())) {
			((itemstack)).addEnchantment(Enchantments.FIRE_ASPECT, (int) 1);
			((itemstack)).addEnchantment(LightningAspectEnchantment.enchantment, (int) 1);
			((itemstack)).addEnchantment(IceAspectEnchantment.enchantment, (int) 1);
		}
		if (((((itemstack).getItem() == new ItemStack(IceMastersArmorItem.helmet, (int) (1)).getItem())
				|| ((itemstack).getItem() == new ItemStack(IceMastersArmorItem.body, (int) (1)).getItem()))
				|| (((itemstack).getItem() == new ItemStack(IceMastersArmorItem.legs, (int) (1)).getItem())
						|| ((itemstack).getItem() == new ItemStack(IceMastersArmorItem.boots, (int) (1)).getItem())))) {
			((itemstack)).addEnchantment(IceProtectionEnchantment.enchantment, (int) 5);
		}
		if (((((itemstack).getItem() == new ItemStack(FireMastersArmorItem.helmet, (int) (1)).getItem())
				|| ((itemstack).getItem() == new ItemStack(FireMastersArmorItem.body, (int) (1)).getItem()))
				|| (((itemstack).getItem() == new ItemStack(FireMastersArmorItem.legs, (int) (1)).getItem())
						|| ((itemstack).getItem() == new ItemStack(FireMastersArmorItem.boots, (int) (1)).getItem())))) {
			((itemstack)).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 5);
		}
		if (((((itemstack).getItem() == new ItemStack(LightningMastersArmorItem.helmet, (int) (1)).getItem())
				|| ((itemstack).getItem() == new ItemStack(LightningMastersArmorItem.body, (int) (1)).getItem()))
				|| (((itemstack).getItem() == new ItemStack(LightningMastersArmorItem.legs, (int) (1)).getItem())
						|| ((itemstack).getItem() == new ItemStack(LightningMastersArmorItem.boots, (int) (1)).getItem())))) {
			((itemstack)).addEnchantment(LightningProtectionEnchantment.enchantment, (int) 5);
		}
		if (((((itemstack).getItem() == new ItemStack(WaterMastersArmorItem.helmet, (int) (1)).getItem())
				|| ((itemstack).getItem() == new ItemStack(WaterMastersArmorItem.body, (int) (1)).getItem()))
				|| (((itemstack).getItem() == new ItemStack(WaterMastersArmorItem.legs, (int) (1)).getItem())
						|| ((itemstack).getItem() == new ItemStack(WaterMastersArmorItem.boots, (int) (1)).getItem())))) {
			((itemstack)).addEnchantment(WaterProtectionEnchantment.enchantment, (int) 5);
		}
		if (((((itemstack).getItem() == new ItemStack(EarthMastersArmorItem.helmet, (int) (1)).getItem())
				|| ((itemstack).getItem() == new ItemStack(EarthMastersArmorItem.body, (int) (1)).getItem()))
				|| (((itemstack).getItem() == new ItemStack(EarthMastersArmorItem.legs, (int) (1)).getItem())
						|| ((itemstack).getItem() == new ItemStack(EarthMastersArmorItem.boots, (int) (1)).getItem())))) {
			((itemstack)).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 5);
		}
	}

	@SubscribeEvent
	public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		Entity entity = event.getPlayer();
		World world = entity.world;
		double i = entity.getPosX();
		double j = entity.getPosY();
		double k = entity.getPosZ();
		ItemStack itemStack = event.getCrafting();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("x", i);
		dependencies.put("y", j);
		dependencies.put("z", k);
		dependencies.put("world", world);
		dependencies.put("entity", entity);
		dependencies.put("itemstack", itemStack);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
