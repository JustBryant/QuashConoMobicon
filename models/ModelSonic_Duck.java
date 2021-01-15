// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class ModelSonic_Duck extends EntityModel<Entity> {
	private final ModelRenderer scarf;
	private final ModelRenderer cube_r1;
	private final ModelRenderer wing2;
	private final ModelRenderer neck;
	private final ModelRenderer head;
	private final ModelRenderer bucket;
	private final ModelRenderer tail;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;
	private final ModelRenderer body;
	private final ModelRenderer wing1;

	public ModelSonic_Duck() {
		textureWidth = 128;
		textureHeight = 128;

		scarf = new ModelRenderer(this);
		scarf.setRotationPoint(0.0F, -0.4F, -14.575F);
		setRotationAngle(scarf, 0.624F, 0.0F, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		scarf.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.2356F, 0.0F, 0.0087F);
		cube_r1.setTextureOffset(0, 0).addBox(-2.9998F, -3.8903F, -2.0067F, 1.0F, 4.0F, 10.0F, 0.0F, false);
		cube_r1.setTextureOffset(0, 33).addBox(3.0F, -3.9412F, -2.019F, 1.0F, 4.0F, 34.0F, 0.0F, false);
		cube_r1.setTextureOffset(53, 16).addBox(-3.0F, -3.9412F, -3.019F, 7.0F, 4.0F, 1.0F, 0.0F, false);

		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(7.5F, 7.725F, -4.75F);
		setRotationAngle(wing2, -0.2923F, 0.0F, 0.0F);
		wing2.setTextureOffset(55, 61).addBox(-0.5F, -4.0F, -7.5F, 1.0F, 8.0F, 15.0F, 0.0F, false);

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, -4.0F, -16.0F);
		setRotationAngle(neck, 0.3927F, 0.0F, 0.0F);
		neck.setTextureOffset(17, 33).addBox(-1.675F, -5.51F, -1.5535F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.925F, 4.0F, 3.0F);
		neck.addChild(head);
		head.setTextureOffset(0, 71).addBox(-4.0F, -10.9739F, -8.1809F, 7.0F, 7.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(36, 50).addBox(-4.0F, -5.9739F, -11.1809F, 7.0F, 2.0F, 3.0F, 0.0F, false);

		bucket = new ModelRenderer(this);
		bucket.setRotationPoint(-0.4208F, -11.7284F, -6.4658F);
		head.addChild(bucket);
		setRotationAngle(bucket, -0.0218F, 0.0F, 0.0F);
		bucket.setTextureOffset(53, 8).addBox(-2.2042F, -3.9804F, 0.8533F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		bucket.setTextureOffset(36, 44).addBox(-2.5042F, 0.1367F, 0.5845F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		bucket.setTextureOffset(53, 0).addBox(-3.7292F, 0.1367F, 1.8595F, 7.0F, 7.0F, 1.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 13.0F, 9.425F);
		setRotationAngle(tail, 0.1484F, 0.0F, 0.0F);
		tail.setTextureOffset(0, 33).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 1.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(3.9875F, 18.75F, -0.5F);
		left_leg.setTextureOffset(36, 36).addBox(-1.9875F, 4.25F, -6.5F, 4.0F, 1.0F, 7.0F, 0.0F, false);
		left_leg.setTextureOffset(30, 56).addBox(-0.5125F, -5.75F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-3.9844F, 18.75F, -0.5F);
		right_leg.setTextureOffset(0, 14).addBox(-2.0156F, 4.25F, -6.5F, 4.0F, 1.0F, 7.0F, 0.0F, false);
		right_leg.setTextureOffset(26, 56).addBox(-0.5156F, -5.75F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 7.0F, -6.5F);
		setRotationAngle(body, -0.2923F, 0.0F, 0.0F);
		body.setTextureOffset(53, 0).addBox(-6.0F, 4.0F, -8.5F, 12.0F, 1.0F, 24.0F, 0.0F, false);
		body.setTextureOffset(0, 0).addBox(-7.0F, -4.0F, -9.5F, 14.0F, 8.0F, 25.0F, 0.0F, false);
		body.setTextureOffset(0, 56).addBox(-6.0F, -3.0F, -10.5F, 12.0F, 6.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(36, 36).addBox(-6.0F, -5.0F, -8.5F, 12.0F, 1.0F, 24.0F, 0.0F, false);

		wing1 = new ModelRenderer(this);
		wing1.setRotationPoint(0.0F, 25.0F, -9.0F);
		setRotationAngle(wing1, -0.3054F, 0.0F, 0.0F);
		wing1.setTextureOffset(0, 33).addBox(-8.0F, -21.8339F, -10.1708F, 1.0F, 8.0F, 15.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		scarf.render(matrixStack, buffer, packedLight, packedOverlay);
		wing2.render(matrixStack, buffer, packedLight, packedOverlay);
		neck.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		wing1.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.neck.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.neck.rotateAngleX = f4 / (180F / (float) Math.PI);
	}
}