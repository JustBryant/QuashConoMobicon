// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class ModelFlower_wolf extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer body_rotation;
	private final ModelRenderer body_sub_1;
	private final ModelRenderer mane;
	private final ModelRenderer mane_rotation;
	private final ModelRenderer mane_sub_1;
	private final ModelRenderer left_leg_2;
	private final ModelRenderer right_leg_2;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;
	private final ModelRenderer tail;
	private final ModelRenderer bone;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer bb_main;

	public ModelFlower_wolf() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(-0.5469F, 13.9844F, -9.0F);
		head.setTextureOffset(24, 25).addBox(-3.4531F, -2.4844F, -2.0F, 7.0F, 5.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(32, 41).addBox(1.5469F, -4.4844F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(40, 34).addBox(-3.4531F, -4.4844F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(28, 34).addBox(-1.9531F, -0.5044F, -5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 2.0F);

		body_rotation = new ModelRenderer(this);
		body_rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(body_rotation);
		setRotationAngle(body_rotation, 1.5708F, 0.0F, 0.0F);

		body_sub_1 = new ModelRenderer(this);
		body_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		body_rotation.addChild(body_sub_1);
		body_sub_1.setTextureOffset(0, 15).addBox(-4.5F, -1.0F, -3.0F, 8.0F, 8.0F, 6.0F, 0.0F, false);

		mane = new ModelRenderer(this);
		mane.setRotationPoint(1.0F, 14.0F, 2.0F);

		mane_rotation = new ModelRenderer(this);
		mane_rotation.setRotationPoint(-1.0F, 2.5F, -2.5F);
		mane.addChild(mane_rotation);
		setRotationAngle(mane_rotation, 1.5708F, 0.0F, 0.0F);

		mane_sub_1 = new ModelRenderer(this);
		mane_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		mane_rotation.addChild(mane_sub_1);
		mane_sub_1.setTextureOffset(14, 29).addBox(-6.0F, -4.5F, -0.5F, 1.0F, 6.0F, 6.0F, 0.0F, false);
		mane_sub_1.setTextureOffset(32, 7).addBox(-4.0F, -4.5F, 6.5F, 7.0F, 6.0F, 1.0F, 0.0F, false);
		mane_sub_1.setTextureOffset(25, 0).addBox(-5.0F, -4.5F, -1.5F, 9.0F, 7.0F, 1.0F, 0.0F, false);
		mane_sub_1.setTextureOffset(0, 29).addBox(4.0F, -4.5F, -0.5F, 1.0F, 6.0F, 6.0F, 0.0F, false);
		mane_sub_1.setTextureOffset(0, 0).addBox(-5.0F, -5.5F, -0.5F, 9.0F, 8.0F, 7.0F, 0.0F, false);

		left_leg_2 = new ModelRenderer(this);
		left_leg_2.setRotationPoint(1.5F, 17.0F, 7.0F);
		left_leg_2.setTextureOffset(24, 41).addBox(-0.725F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

		right_leg_2 = new ModelRenderer(this);
		right_leg_2.setRotationPoint(-2.5F, 17.0F, 7.0F);
		right_leg_2.setTextureOffset(16, 41).addBox(-1.225F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(2.5F, 18.0F, -3.0F);
		left_leg.setTextureOffset(8, 41).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-3.5F, 18.0F, -3.0F);
		right_leg.setTextureOffset(0, 41).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-0.5F, 12.625F, 9.85F);
		tail.setTextureOffset(8, 29).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(5.0F, 8.0F, -4.5F);
		setRotationAngle(bone, -0.0873F, 0.0F, 0.0F);
		bone.setTextureOffset(16, 31).addBox(-6.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(6, 29).addBox(-6.0F, 5.9166F, 15.684F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(12, 29).addBox(-6.0F, 5.3416F, 15.684F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(14, 29).addBox(-6.0F, 6.5166F, 15.684F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(16, 29).addBox(-6.0F, 4.6916F, 15.684F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(20, 29).addBox(-6.0F, 3.9166F, 15.684F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(0, 0).addBox(-7.0F, 0.0F, 13.1F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(0, 3).addBox(-7.0F, 0.0F, 12.6F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(0, 15).addBox(-7.0F, 0.0F, 12.075F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(0, 18).addBox(-7.0F, 0.0F, 11.475F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(22, 15).addBox(-7.0F, 0.0F, 10.8F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(22, 18).addBox(-7.0F, 0.0F, 10.175F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(28, 22).addBox(-7.0F, 0.0F, 9.625F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(0, 29).addBox(-7.0F, 0.0F, 9.1F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(0, 32).addBox(-7.0F, 0.0F, 8.575F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(34, 22).addBox(-7.0F, 0.0F, 8.05F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(40, 22).addBox(-7.0F, 0.0F, 7.475F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(40, 41).addBox(-7.0F, 0.0F, 6.95F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(42, 25).addBox(-7.0F, -1.0F, 6.4F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(40, 44).addBox(-7.0F, -1.0F, 5.8F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(44, 38).addBox(-7.0F, -2.0F, 5.25F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(32, 45).addBox(-7.0F, -2.0F, 4.575F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(45, 0).addBox(-7.0F, -2.0F, 3.975F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(45, 3).addBox(-7.0F, -2.0F, 3.4F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(46, 46).addBox(-7.0F, -2.0F, 2.85F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(46, 22).addBox(-7.0F, -2.0F, 2.35F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(46, 28).addBox(-7.0F, -2.0F, 1.85F, 3.0F, 3.0F, 0.0F, 0.0F, false);
		bone.setTextureOffset(46, 31).addBox(-7.0F, -2.0F, 1.325F, 3.0F, 3.0F, 0.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 4.0F, 13.775F);
		bone.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.1309F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(40, 34).addBox(-6.0F, -1.974F, 1.6181F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		cube_r1.setTextureOffset(40, 34).addBox(-6.0F, -1.999F, 1.0436F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 4.0F, 13.1F);
		bone.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0873F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 41).addBox(-6.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(28, 15).addBox(-5.0F, -13.0F, -7.0F, 9.0F, 6.0F, 1.0F, 0.0F, false);
		bb_main.setTextureOffset(6, 41).addBox(-7.0F, -9.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(38, 14).addBox(-8.0F, -10.0F, -6.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 37).addBox(-7.0F, -11.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 34).addBox(-8.0F, -12.0F, -6.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 36).addBox(-7.0F, -13.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(8, 42).addBox(-6.0F, -14.0F, -6.5F, 1.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(30, 41).addBox(-5.0F, -15.0F, -6.5F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(24, 41).addBox(-3.0F, -15.0F, -6.5F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(46, 41).addBox(-4.0F, -16.0F, -6.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(38, 45).addBox(-2.0F, -16.0F, -6.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 41).addBox(-1.0F, -15.0F, -6.5F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 31).addBox(0.0F, -16.0F, -6.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(16, 41).addBox(1.0F, -15.0F, -6.5F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(18, 31).addBox(2.0F, -16.0F, -6.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(14, 41).addBox(3.0F, -15.0F, -6.5F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(6, 42).addBox(4.0F, -14.0F, -6.5F, 1.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 35).addBox(4.0F, -13.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(32, 14).addBox(4.0F, -12.0F, -6.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 34).addBox(4.0F, -11.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 6).addBox(4.0F, -10.0F, -6.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(16, 34).addBox(4.0F, -9.0F, -6.5F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(38, 41).addBox(4.0F, -8.0F, -6.5F, 1.0F, 1.0F, 0.0F, 0.0F, false);
		bb_main.setTextureOffset(32, 41).addBox(-6.0F, -8.0F, -6.5F, 1.0F, 1.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		mane.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.left_leg_2.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.right_leg_2.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
	}
}