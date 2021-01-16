// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class ModelVolcanic_Rat extends EntityModel<Entity> {
	private final ModelRenderer bone8;
	private final ModelRenderer tail;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;
	private final ModelRenderer leg4;

	public ModelVolcanic_Rat() {
		textureWidth = 32;
		textureHeight = 32;

		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(0.0F, 24.0F, 0.0F);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -3.0F, 0.3F);
		bone8.addChild(tail);
		tail.setTextureOffset(0, 11).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		tail.setTextureOffset(14, 0).addBox(-0.5F, 0.0F, 4.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.5F, -1.0F, -0.7F);
		bone8.addChild(body);
		body.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, -7.0F, 3.0F, 3.0F, 8.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -4.0F, -7.7F);
		bone8.addChild(head);
		head.setTextureOffset(0, 11).addBox(-2.5F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 14).addBox(0.5F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(8, 11).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		head.setTextureOffset(14, 0).addBox(-0.5F, 1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-1.5F, -3.0F, -0.7125F);
		bone8.addChild(leg1);
		leg1.setTextureOffset(0, 18).addBox(-2.0F, 0.0F, -0.9875F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		leg1.setTextureOffset(8, 19).addBox(-1.5F, 2.0F, -2.0125F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(1.5F, -3.0F, -0.625F);
		bone8.addChild(leg2);
		leg2.setTextureOffset(18, 15).addBox(0.5F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		leg2.setTextureOffset(12, 17).addBox(0.0F, 0.0F, -1.075F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(1.7F, -3.0F, -6.0F);
		bone8.addChild(leg3);
		leg3.setTextureOffset(17, 11).addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		leg3.setTextureOffset(0, 4).addBox(-0.7F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(-1.5125F, -3.0F, -6.0F);
		bone8.addChild(leg4);
		leg4.setTextureOffset(14, 5).addBox(-1.1375F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		leg4.setTextureOffset(0, 0).addBox(-1.4875F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		bone8.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.leg1.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.leg4.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.tail.rotateAngleY = f4 / (180F / (float) Math.PI);
		this.leg2.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
	}
}