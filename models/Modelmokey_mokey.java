// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelmokey_mokey extends EntityModel<Entity> {
	private final ModelRenderer body;
	private final ModelRenderer body_r1;
	private final ModelRenderer armsandlegs;
	private final ModelRenderer armsandlegs_r1;
	private final ModelRenderer question_mark;
	private final ModelRenderer wingsandmark_r1;
	private final ModelRenderer right_wing;
	private final ModelRenderer wingsandmark_r2;
	private final ModelRenderer left_wing;
	private final ModelRenderer wingsandmark_r3;

	public Modelmokey_mokey() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 4.0F, 0.0F);

		body_r1 = new ModelRenderer(this);
		body_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, 0.0F, -1.5708F, 0.0F);
		body_r1.setTextureOffset(0, 0).addBox(-3.0F, -29.0F, -7.0F, 7.0F, 17.0F, 16.0F, 0.0F, false);

		armsandlegs = new ModelRenderer(this);
		armsandlegs.setRotationPoint(-4.0F, 24.0F, 0.0F);

		armsandlegs_r1 = new ModelRenderer(this);
		armsandlegs_r1.setRotationPoint(4.0F, 0.0F, 0.0F);
		armsandlegs.addChild(armsandlegs_r1);
		setRotationAngle(armsandlegs_r1, 0.0F, -1.5708F, 0.0F);
		armsandlegs_r1.setTextureOffset(59, 0).addBox(-1.0F, -13.0F, 3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		armsandlegs_r1.setTextureOffset(60, 24).addBox(-1.0F, -13.0F, -4.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		armsandlegs_r1.setTextureOffset(27, 61).addBox(-1.0F, -25.0F, -9.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		armsandlegs_r1.setTextureOffset(41, 61).addBox(-1.0F, -25.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		question_mark = new ModelRenderer(this);
		question_mark.setRotationPoint(0.0F, 24.0F, 0.0F);

		wingsandmark_r1 = new ModelRenderer(this);
		wingsandmark_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		question_mark.addChild(wingsandmark_r1);
		setRotationAngle(wingsandmark_r1, 0.0F, -1.5708F, 0.0F);
		wingsandmark_r1.setTextureOffset(11, 56).addBox(-1.0F, -34.0F, -1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(41, 41).addBox(-8.0F, -42.0F, 2.0F, 5.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(22, 35).addBox(-8.0F, -42.0F, -7.0F, 7.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(31, 0).addBox(-11.0F, -42.0F, -4.0F, 3.0F, 4.0F, 7.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(0, 48).addBox(-10.0F, -42.0F, 0.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(48, 8).addBox(-10.0F, -42.0F, -5.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(0, 0).addBox(-9.0F, -42.0F, 1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(48, 18).addBox(-9.0F, -42.0F, -6.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(29, 51).addBox(-3.0F, -42.0F, -6.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(45, 51).addBox(-2.0F, -42.0F, -5.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(0, 35).addBox(-1.0F, -42.0F, -4.0F, 3.0F, 4.0F, 7.0F, 0.0F, false);
		wingsandmark_r1.setTextureOffset(56, 56).addBox(-1.0F, -39.0F, -1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(-7.5F, -7.0F, 5.875F);
		setRotationAngle(right_wing, 0.0F, -0.0436F, 0.0F);

		wingsandmark_r2 = new ModelRenderer(this);
		wingsandmark_r2.setRotationPoint(7.5F, 31.0F, -5.875F);
		right_wing.addChild(wingsandmark_r2);
		setRotationAngle(wingsandmark_r2, 0.0F, -1.5708F, 0.0F);
		wingsandmark_r2.setTextureOffset(56, 46).addBox(3.0F, -34.0F, 7.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r2.setTextureOffset(16, 45).addBox(3.0F, -32.0F, 4.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
		wingsandmark_r2.setTextureOffset(45, 0).addBox(3.0F, -31.0F, 8.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
		wingsandmark_r2.setTextureOffset(61, 15).addBox(3.0F, -33.0F, 6.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(5.5F, -7.0F, 4.125F);

		wingsandmark_r3 = new ModelRenderer(this);
		wingsandmark_r3.setRotationPoint(-5.5F, 31.0F, -4.125F);
		left_wing.addChild(wingsandmark_r3);
		setRotationAngle(wingsandmark_r3, 0.0F, -1.5708F, 0.0F);
		wingsandmark_r3.setTextureOffset(56, 35).addBox(3.0F, -34.0F, -9.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		wingsandmark_r3.setTextureOffset(42, 29).addBox(3.0F, -32.0F, -7.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
		wingsandmark_r3.setTextureOffset(0, 10).addBox(3.0F, -31.0F, -8.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
		wingsandmark_r3.setTextureOffset(28, 45).addBox(3.0F, -33.0F, -6.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		armsandlegs.render(matrixStack, buffer, packedLight, packedOverlay);
		question_mark.render(matrixStack, buffer, packedLight, packedOverlay);
		right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.right_wing.rotateAngleX = f3 / (180F / (float) Math.PI);
		this.left_wing.rotateAngleX = f3 / (180F / (float) Math.PI);
	}
}