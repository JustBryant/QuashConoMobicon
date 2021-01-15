// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelfrog_the_jam extends EntityModel<Entity> {
	private final ModelRenderer Head;
	private final ModelRenderer bb_main;

	public Modelfrog_the_jam() {
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.5F, 19.0F, -0.5F);
		Head.setTextureOffset(25, 25).addBox(-3.5F, -3.0F, -2.5F, 7.0F, 5.0F, 5.0F, 0.0F, false);
		Head.setTextureOffset(0, 0).addBox(-3.5F, -4.0F, -2.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		Head.setTextureOffset(0, 3).addBox(1.5F, -4.0F, -2.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 11.0F, 1.0F, 9.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 18).addBox(-4.0F, -2.0F, -4.0F, 9.0F, 1.0F, 7.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 10).addBox(-4.0F, -3.0F, -4.0F, 9.0F, 1.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
	}
}