
package net.bukkit.elementalmaster.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.World;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.BossInfo;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.bukkit.elementalmaster.ElementalmasterModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@ElementalmasterModElements.ModElement.Tag
public class GiantOctopusEntity extends ElementalmasterModElements.ModElement {
	public static EntityType entity = null;
	public GiantOctopusEntity(ElementalmasterModElements instance) {
		super(instance, 208);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.7999999999999999f, 2f))
						.build("giant_octopus").setRegistryName("giant_octopus");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -16776961, -6750055, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("giant_octopus"));
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new minikraken(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("elementalmaster:textures/octopusbodytrue.png");
				}
			};
		});
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 100;
			setNoAI(false);
			enablePersistence();
			this.moveController = new MovementController(this) {
				@Override
				public void tick() {
					if (CustomEntity.this.areEyesInFluid(FluidTags.WATER))
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, 0.005, 0));
					if (this.action == MovementController.Action.MOVE_TO && !CustomEntity.this.getNavigator().noPath()) {
						double dx = this.posX - CustomEntity.this.getPosX();
						double dy = this.posY - CustomEntity.this.getPosY();
						double dz = this.posZ - CustomEntity.this.getPosZ();
						dy = dy / (double) MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
						CustomEntity.this.rotationYaw = this.limitAngle(CustomEntity.this.rotationYaw,
								(float) (MathHelper.atan2(dz, dx) * (double) (180 / (float) Math.PI)) - 90, 90);
						CustomEntity.this.renderYawOffset = CustomEntity.this.rotationYaw;
						CustomEntity.this.setAIMoveSpeed(MathHelper.lerp(0.125f, CustomEntity.this.getAIMoveSpeed(),
								(float) (this.speed * CustomEntity.this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue())));
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, CustomEntity.this.getAIMoveSpeed() * dy * 0.1, 0));
					} else {
						CustomEntity.this.setAIMoveSpeed(0);
					}
				}
			};
			this.navigator = new SwimmerPathNavigator(this, this.world);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.6, false));
			this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1, 40));
			this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, false));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source.getImmediateSource() instanceof PotionEntity)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.CACTUS)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3);
		}

		@Override
		public boolean canBreatheUnderwater() {
			return true;
		}

		@Override
		public boolean isNotColliding(IWorldReader worldreader) {
			return worldreader.checkNoEntityCollision(this, VoxelShapes.create(this.getBoundingBox()));
		}

		@Override
		public boolean isPushedByWater() {
			return false;
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}
		private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS);
		@Override
		public void addTrackingPlayer(ServerPlayerEntity player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(ServerPlayerEntity player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void updateAITasks() {
			super.updateAITasks();
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		}
	}

	// Made with Blockbench
	// Paste this code into your mod.
	public static class minikraken extends EntityModel<Entity> {
		private final ModelRenderer Tentacle1;
		private final ModelRenderer Body;
		public minikraken() {
			textureWidth = 64;
			textureHeight = 64;
			Tentacle1 = new ModelRenderer(this);
			Tentacle1.setRotationPoint(0.0F, 24.0F, 0.0F);
			addBoxHelper(Tentacle1, 52, 54, -13.0F, -30.0F, -8.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -15.0F, -26.0F, -9.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -17.0F, -22.0F, -11.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -19.0F, -17.0F, -12.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -21.0F, -11.0F, -13.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 8.0F, -34.0F, 4.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 10.0F, -30.0F, 5.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 12.0F, -26.0F, 6.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 14.0F, -22.0F, 7.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -12.0F, -17.0F, -19.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 18.0F, -11.0F, 9.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -11.0F, -34.0F, -7.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -11.0F, -34.0F, 4.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -13.0F, -30.0F, 5.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -15.0F, -26.0F, 6.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -17.0F, -22.0F, 8.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -19.0F, -17.0F, 9.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -21.0F, -11.0F, 10.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 8.0F, -34.0F, -7.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 10.0F, -30.0F, -8.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 12.0F, -26.0F, -9.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 14.0F, -22.0F, -11.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 16.0F, -17.0F, -12.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 18.0F, -11.0F, -13.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -7.0F, -34.0F, -11.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -8.0F, -30.0F, -13.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -9.0F, -26.0F, -15.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -11.0F, -22.0F, -17.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 16.0F, -17.0F, 8.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -13.0F, -11.0F, -21.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 4.0F, -34.0F, -11.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 5.0F, -30.0F, 10.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 6.0F, -26.0F, -15.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 8.0F, -22.0F, -17.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 9.0F, -17.0F, -19.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 10.0F, -11.0F, -21.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -7.0F, -34.0F, 8.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -8.0F, -30.0F, 10.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -9.0F, -26.0F, 12.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -11.0F, -22.0F, 14.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -12.0F, -17.0F, 16.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, -13.0F, -11.0F, 18.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 4.0F, -34.0F, 8.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 5.0F, -30.0F, -13.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 6.0F, -26.0F, 12.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 7.0F, -22.0F, 14.0F, 3, 7, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 8.0F, -17.0F, 16.0F, 3, 9, 3, 0.0F, false);
			addBoxHelper(Tentacle1, 52, 54, 9.0F, -11.0F, 18.0F, 3, 9, 3, 0.0F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 24.0F, 0.0F);
			addBoxHelper(Body, 0, 0, -8.0F, -44.0F, -8.0F, 16, 22, 16, 0.0F, false);
		}

		@Override
		public void render(MatrixStack ms, IVertexBuilder vb, int i1, int i2, float f1, float f2, float f3, float f4) {
			Tentacle1.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Body.render(ms, vb, i1, i2, f1, f2, f3, f4);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
		}
	}
	@OnlyIn(Dist.CLIENT)
	public static void addBoxHelper(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta) {
		addBoxHelper(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
	}

	@OnlyIn(Dist.CLIENT)
	public static void addBoxHelper(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta,
			boolean mirror) {
		renderer.mirror = mirror;
		renderer.addBox("", x, y, z, dx, dy, dz, delta, texU, texV);
	}
}
