package net.bukkit.elementalmaster.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;

import net.bukkit.elementalmaster.enchantment.LightningProtectionEnchantment;
import net.bukkit.elementalmaster.enchantment.IceAspectEnchantment;
import net.bukkit.elementalmaster.ElementalmasterModElements;

import java.util.Map;
import java.util.HashMap;

@ElementalmasterModElements.ModElement.Tag
public class PlayerTick3Procedure extends ElementalmasterModElements.ModElement {
	public PlayerTick3Procedure(ElementalmasterModElements instance) {
		super(instance, 656);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure PlayerTick3!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure PlayerTick3!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure PlayerTick3!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure PlayerTick3!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure PlayerTick3!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((true)) {
			if ((ForgeRegistries.BIOMES.getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
					.equals(new ResourceLocation("elementalmaster:firiumforest")))) {
				if ((Math.random() < 0.25)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, x, y, z, (int) 30, 20, 20, 20, 1);
					}
				}
				if ((Math.random() < 0.25)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, x, y, z, (int) 30, 20, 20, 20, 1);
					}
				}
				if ((Math.random() < 0.25)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, x, y, z, (int) 30, 20, 20, 20, 1);
					}
				}
			}
			if ((ForgeRegistries.BIOMES.getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
					.equals(new ResourceLocation("elementalmaster:darkforest")))) {
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.PORTAL, x, y, z, (int) 35, 15, 15, 15, 1);
					}
				}
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.PORTAL, x, y, z, (int) 35, 15, 15, 15, 1);
					}
				}
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.PORTAL, x, y, z, (int) 35, 15, 15, 15, 1);
					}
				}
			}
			if ((ForgeRegistries.BIOMES.getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
					.equals(new ResourceLocation("elementalmaster:hope_forest")))) {
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FIREWORK, x, y, z, (int) 15, 15, 15, 15, 0.05);
					}
				}
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FIREWORK, x, y, z, (int) 15, 15, 15, 15, 0.05);
					}
				}
				if ((Math.random() < 0.2)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FIREWORK, x, y, z, (int) 15, 15, 15, 15, 0.05);
					}
				}
			}
		}
		if ((true)) {
			if ((!((EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT,
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY))) == 0))) {
				if ((Math.random() < 0.05)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, x, (y + 1), z, (int) 3, 0.8, 0.8, 0.8, 0.02);
					}
				}
			}
			if ((!((EnchantmentHelper.getEnchantmentLevel(IceAspectEnchantment.enchantment,
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY))) == 0))) {
				if ((Math.random() < 0.05)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.ITEM_SNOWBALL, x, (y + 1), z, (int) 3, 0.8, 0.8, 0.8, 0.02);
					}
				}
			}
			if ((!((EnchantmentHelper.getEnchantmentLevel(LightningProtectionEnchantment.enchantment,
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY))) == 0))) {
				if ((Math.random() < 0.05)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.TOTEM_OF_UNDYING, x, (y + 1), z, (int) 5, 0.8, 0.8, 0.8, 0.02);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}
}
