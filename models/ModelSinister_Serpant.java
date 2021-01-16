// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class ModelSinister_Serpant extends EntityModel<Entity> {
	private final ModelRenderer left_wing;
	private final ModelRenderer cube_r1;
	private final ModelRenderer right_wing;
	private final ModelRenderer tail;
	private final ModelRenderer body;
	private final ModelRenderer cube_r2;
	private final ModelRenderer sneknek;
	private final ModelRenderer head;

	public ModelSinister_Serpant() {
		textureWidth = 64;
		textureHeight = 64;

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(0.5F, 13.0F, 2.95F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(9.5F, -4.475F, -5.95F);
		left_wing.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.6981F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 28).addBox(0.0F, -2.0F, 4.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		cube_r1.setTextureOffset(27, 14).addBox(-3.0F, -2.0F, 4.0F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		cube_r1.setTextureOffset(16, 22).addBox(-10.0F, -2.0F, 4.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
		cube_r1.setTextureOffset(0, 7).addBox(-9.0F, -1.0F, 4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(-2.5F, 13.0F, 2.95F);
		setRotationAngle(right_wing, -0.6981F, 0.0F, 0.0F);
		right_wing.setTextureOffset(8, 8).addBox(-12.5F, -1.5601F, -3.3747F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		right_wing.setTextureOffset(0, 14).addBox(-9.5F, -1.5601F, -3.3747F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		right_wing.setTextureOffset(27, 7).addBox(-1.5F, -0.5601F, -3.3747F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		right_wing.setTextureOffset(27, 0).addBox(-6.5F, -1.5601F, -3.3747F, 7.0F, 1.0F, 6.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-1.0F, 23.2F, 14.0F);
		tail.setTextureOffset(0, 22).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 12.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(-1.25F, 16.5F, 6.25F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(1.25F, -4.5F, -8.25F);
		body.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.6981F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, 0.0F, 4.0F, 3.0F, 19.0F, 0.0F, false);

		sneknek = new ModelRenderer(this);
		sneknek.setRotationPoint(-0.75F, 11.0F, -0.75F);
		sneknek.setTextureOffset(0, 0).addBox(-2.25F, -1.25F, -3.25F, 4.0F, 3.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.75F, 0.75F, -2.25F);
		sneknek.addChild(head);
		head.setTextureOffset(0, 22).addBox(-2.0F, -1.55F, -5.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		sneknek.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.sneknek.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.sneknek.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.right_wing.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.left_wing.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.tail.rotateAngleX = f3 / (180F / (float) Math.PI);
	}
}