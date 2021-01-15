// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelmokey_mokey_king extends EntityModel<Entity> {
	private final ModelRenderer right_wing;
	private final ModelRenderer body_r1;
	private final ModelRenderer left_wing;
	private final ModelRenderer body_r2;
	private final ModelRenderer body;
	private final ModelRenderer body_r3;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r1_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r2_r1;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r3_r1;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r4_r1;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r5_r1;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r6_r1;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r7_r1;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r8_r1;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r9_r1;
	private final ModelRenderer bb_main;
	private final ModelRenderer bb_main_r1;

	public Modelmokey_mokey_king() {
		textureWidth = 512;
		textureHeight = 512;

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(19.0F, -169.0F, 50.0F);

		body_r1 = new ModelRenderer(this);
		body_r1.setRotationPoint(-19.0F, 193.0F, -50.0F);
		right_wing.addChild(body_r1);
		setRotationAngle(body_r1, 0.0F, 0.0F, 0.0F);
		body_r1.setTextureOffset(338, 136).addBox(13.0F, -190.0F, 50.0F, 13.0F, 15.0F, 13.0F, 0.0F, false);
		body_r1.setTextureOffset(147, 329).addBox(13.0F, -207.0F, 34.0F, 13.0F, 15.0F, 13.0F, 0.0F, false);
		body_r1.setTextureOffset(201, 68).addBox(13.0F, -200.0F, 23.0F, 13.0F, 35.0F, 33.0F, 0.0F, false);
		body_r1.setTextureOffset(201, 0).addBox(13.0F, -219.0F, 41.0F, 13.0F, 35.0F, 33.0F, 0.0F, false);

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(19.0F, -169.0F, -57.0F);

		body_r2 = new ModelRenderer(this);
		body_r2.setRotationPoint(-19.0F, 193.0F, 57.0F);
		left_wing.addChild(body_r2);
		setRotationAngle(body_r2, 0.0F, 0.0F, 0.0F);
		body_r2.setTextureOffset(205, 328).addBox(13.0F, -206.0F, -61.0F, 13.0F, 15.0F, 15.0F, 0.0F, false);
		body_r2.setTextureOffset(0, 68).addBox(13.0F, -217.0F, -88.0F, 13.0F, 33.0F, 35.0F, 0.0F, false);
		body_r2.setTextureOffset(261, 328).addBox(13.0F, -190.0F, -78.0F, 13.0F, 15.0F, 15.0F, 0.0F, false);
		body_r2.setTextureOffset(0, 0).addBox(13.0F, -199.0F, -69.0F, 13.0F, 35.0F, 35.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 4.0F, 0.0F);

		body_r3 = new ModelRenderer(this);
		body_r3.setRotationPoint(0.0F, 20.0F, 0.0F);
		body.addChild(body_r3);
		setRotationAngle(body_r3, 0.0F, 0.0F, 0.0F);
		body_r3.setTextureOffset(0, 294).addBox(-7.0F, -242.0F, -14.0F, 15.0F, 34.0F, 15.0F, 0.0F, false);
		body_r3.setTextureOffset(325, 279).addBox(-7.0F, -197.0F, -14.0F, 15.0F, 15.0F, 15.0F, 0.0F, false);
		body_r3.setTextureOffset(0, 0).addBox(-15.0F, -174.0F, -75.0F, 32.0F, 157.0F, 137.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(1.0F, -216.0F, 37.0F);
		body.addChild(cube_r1);
		setRotationAngle(cube_r1, 1.5708F, 0.0F, 0.0F);

		cube_r1_r1 = new ModelRenderer(this);
		cube_r1_r1.setRotationPoint(-1.0F, 236.0F, -37.0F);
		cube_r1.addChild(cube_r1_r1);
		setRotationAngle(cube_r1_r1, 0.0F, 0.0F, 0.0F);
		cube_r1_r1.setTextureOffset(61, 0).addBox(-31.0F, -272.0F, 28.0F, 18.0F, 16.0F, 15.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-4.0F, -216.0F, -18.0F);
		body.addChild(cube_r2);
		setRotationAngle(cube_r2, 1.5708F, 0.0F, 0.0F);

		cube_r2_r1 = new ModelRenderer(this);
		cube_r2_r1.setRotationPoint(4.0F, 236.0F, 18.0F);
		cube_r2.addChild(cube_r2_r1);
		setRotationAngle(cube_r2_r1, 0.0F, 0.0F, 0.0F);
		cube_r2_r1.setTextureOffset(260, 0).addBox(-36.0F, -272.0F, -27.0F, 32.0F, 16.0F, 15.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-25.0F, -216.0F, 27.0F);
		body.addChild(cube_r3);
		setRotationAngle(cube_r3, 1.5708F, 0.0F, 0.0F);

		cube_r3_r1 = new ModelRenderer(this);
		cube_r3_r1.setRotationPoint(25.0F, 236.0F, -27.0F);
		cube_r3.addChild(cube_r3_r1);
		setRotationAngle(cube_r3_r1, 0.0F, 0.0F, 0.0F);
		cube_r3_r1.setTextureOffset(220, 294).addBox(-40.0F, -270.0F, 18.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-25.0F, -216.0F, -16.0F);
		body.addChild(cube_r4);
		setRotationAngle(cube_r4, 1.5708F, 0.0F, 0.0F);

		cube_r4_r1 = new ModelRenderer(this);
		cube_r4_r1.setRotationPoint(25.0F, 236.0F, 16.0F);
		cube_r4.addChild(cube_r4_r1);
		setRotationAngle(cube_r4_r1, 0.0F, 0.0F, 0.0F);
		cube_r4_r1.setTextureOffset(325, 325).addBox(-15.0F, -270.0F, -25.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);
		cube_r4_r1.setTextureOffset(280, 294).addBox(-40.0F, -270.0F, -25.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-29.0F, -216.0F, 21.0F);
		body.addChild(cube_r5);
		setRotationAngle(cube_r5, 1.5708F, 0.0F, 0.0F);

		cube_r5_r1 = new ModelRenderer(this);
		cube_r5_r1.setRotationPoint(29.0F, 236.0F, -21.0F);
		cube_r5.addChild(cube_r5_r1);
		setRotationAngle(cube_r5_r1, 0.0F, 0.0F, 0.0F);
		cube_r5_r1.setTextureOffset(61, 68).addBox(-44.0F, -270.0F, 12.0F, 15.0F, 20.0F, 15.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(-29.0F, -216.0F, -10.0F);
		body.addChild(cube_r6);
		setRotationAngle(cube_r6, 1.5708F, 0.0F, 0.0F);

		cube_r6_r1 = new ModelRenderer(this);
		cube_r6_r1.setRotationPoint(29.0F, 236.0F, 10.0F);
		cube_r6.addChild(cube_r6_r1);
		setRotationAngle(cube_r6_r1, 0.0F, 0.0F, 0.0F);
		cube_r6_r1.setTextureOffset(160, 294).addBox(-44.0F, -270.0F, -19.0F, 15.0F, 20.0F, 15.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-34.0F, -216.0F, -5.0F);
		body.addChild(cube_r7);
		setRotationAngle(cube_r7, 1.5708F, 0.0F, 0.0F);

		cube_r7_r1 = new ModelRenderer(this);
		cube_r7_r1.setRotationPoint(34.0F, 236.0F, 5.0F);
		cube_r7.addChild(cube_r7_r1);
		setRotationAngle(cube_r7_r1, 0.0F, 0.0F, 0.0F);
		cube_r7_r1.setTextureOffset(293, 87).addBox(-7.0F, -270.0F, -14.0F, 15.0F, 34.0F, 15.0F, 0.0F, false);
		cube_r7_r1.setTextureOffset(293, 31).addBox(-49.0F, -270.0F, -14.0F, 15.0F, 41.0F, 15.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(1.0F, -148.243F, -63.6676F);
		body.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.5672F, 0.0F, 0.0F);

		cube_r8_r1 = new ModelRenderer(this);
		cube_r8_r1.setRotationPoint(-1.0F, 168.243F, 63.6676F);
		cube_r8.addChild(cube_r8_r1);
		setRotationAngle(cube_r8_r1, 0.0F, 0.0F, 0.0F);
		cube_r8_r1.setTextureOffset(60, 294).addBox(-6.0F, -146.913F, -63.6676F, 13.0F, 37.0F, 12.0F, 0.0F, false);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(1.0F, -79.913F, 77.1324F);
		body.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.5672F, 0.0F, 0.0F);

		cube_r9_r1 = new ModelRenderer(this);
		cube_r9_r1.setRotationPoint(-1.0F, 99.913F, -77.1324F);
		cube_r9.addChild(cube_r9_r1);
		setRotationAngle(cube_r9_r1, 0.0F, 0.0F, 0.0F);
		cube_r9_r1.setTextureOffset(110, 294).addBox(-6.0F, -146.913F, 77.1324F, 13.0F, 36.0F, 12.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);

		bb_main_r1 = new ModelRenderer(this);
		bb_main_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		bb_main.addChild(bb_main_r1);
		setRotationAngle(bb_main_r1, 0.0F, 0.0F, 0.0F);
		bb_main_r1.setTextureOffset(96, 31).addBox(-10.0F, -17.0F, -46.0F, 10.0F, 15.0F, 9.0F, 0.0F, false);
		bb_main_r1.setTextureOffset(96, 103).addBox(-10.0F, -17.0F, 24.0F, 10.0F, 15.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}