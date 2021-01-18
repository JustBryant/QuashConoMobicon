// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelflower_wolf extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer RightEar;
	private final ModelRenderer bone;
	private final ModelRenderer partThree_r1;
	private final ModelRenderer bone2;
	private final ModelRenderer partThree_r2;
	private final ModelRenderer bone3;
	private final ModelRenderer partThree_r3;
	private final ModelRenderer bone4;
	private final ModelRenderer partThree_r4;
	private final ModelRenderer earpoint7;
	private final ModelRenderer earpoint8;
	private final ModelRenderer NeckFlower;
	private final ModelRenderer SideNeckFlowers;
	private final ModelRenderer NeckFlowers;
	private final ModelRenderer SideNeckFlowers2;
	private final ModelRenderer SideNeckFlowers3;
	private final ModelRenderer SideNeckFlowers6;
	private final ModelRenderer SideNeckFlowers7;
	private final ModelRenderer SideNeckFlowers5;
	private final ModelRenderer SideNeckFlowers4;
	private final ModelRenderer Thing;
	private final ModelRenderer AnotherThing;
	private final ModelRenderer head_r1;
	private final ModelRenderer Thing3;
	private final ModelRenderer head_r2;
	private final ModelRenderer LeftEar;
	private final ModelRenderer bone5;
	private final ModelRenderer partThree_r5;
	private final ModelRenderer bone6;
	private final ModelRenderer partThree_r6;
	private final ModelRenderer bone7;
	private final ModelRenderer partThree_r7;
	private final ModelRenderer bone8;
	private final ModelRenderer partThree_r8;
	private final ModelRenderer earpoint3;
	private final ModelRenderer earpoint4;
	private final ModelRenderer earpoint5;
	private final ModelRenderer tail;
	private final ModelRenderer TailFlowersleft;
	private final ModelRenderer lefttailflower4_r1;
	private final ModelRenderer TailFlowersRight2;
	private final ModelRenderer righttailflower4_r1;
	private final ModelRenderer bigbackflowerright;
	private final ModelRenderer bigbackflowerleft;
	private final ModelRenderer body;
	private final ModelRenderer back;
	private final ModelRenderer body_rotation;
	private final ModelRenderer body_sub_1;
	private final ModelRenderer front;
	private final ModelRenderer mane_rotation;
	private final ModelRenderer mane_sub_1;
	private final ModelRenderer BackLeftLeg;
	private final ModelRenderer BackRightLeg;
	private final ModelRenderer FrontLeftLEg;
	private final ModelRenderer FrontRightLeg;
	private final ModelRenderer LowerBackFlowers;
	private final ModelRenderer backleftbackflowers;
	private final ModelRenderer backrightbackflowers;
	private final ModelRenderer backmiddleBackFlowers;
	private final ModelRenderer UpperBackFlowers;
	private final ModelRenderer frontmiddleBackFlowers;
	private final ModelRenderer frontrightbackflowers;
	private final ModelRenderer frontleftbackflowers;

	public Modelflower_wolf() {
		textureWidth = 128;
		textureHeight = 128;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.25F, 9.235F, -7.275F);
		head.setTextureOffset(34, 34).addBox(-3.75F, -3.735F, -4.425F, 7.0F, 7.0F, 6.0F, 0.0F, false);
		head.setTextureOffset(0, 38).addBox(-2.25F, 0.145F, -8.225F, 4.0F, 3.0F, 8.0F, 0.0F, false);

		RightEar = new ModelRenderer(this);
		RightEar.setRotationPoint(-0.25F, 12.965F, 7.275F);
		head.addChild(RightEar);
		RightEar.setTextureOffset(0, 38).addBox(1.0F, -16.9F, -8.7F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		RightEar.addChild(bone);

		partThree_r1 = new ModelRenderer(this);
		partThree_r1.setRotationPoint(2.2612F, -18.265F, -7.7F);
		bone.addChild(partThree_r1);
		setRotationAngle(partThree_r1, 0.0F, 0.0F, -0.2269F);
		partThree_r1.setTextureOffset(9, 49).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(1.5997F, -18.1747F, -7.7F);
		RightEar.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, -0.0873F);

		partThree_r2 = new ModelRenderer(this);
		partThree_r2.setRotationPoint(-0.0304F, -0.0537F, 0.0F);
		bone2.addChild(partThree_r2);
		setRotationAngle(partThree_r2, 0.0F, 0.0F, 0.2269F);
		partThree_r2.setTextureOffset(9, 49).addBox(-0.3747F, 0.3912F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
		RightEar.addChild(bone3);

		partThree_r3 = new ModelRenderer(this);
		partThree_r3.setRotationPoint(1.7388F, -18.265F, -7.7F);
		bone3.addChild(partThree_r3);
		setRotationAngle(partThree_r3, 0.0F, 0.0F, 0.2269F);
		partThree_r3.setTextureOffset(9, 49).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(7.7693F, -18.2284F, -7.7F);
		RightEar.addChild(bone4);
		setRotationAngle(bone4, 0.0F, 0.0F, 0.0873F);

		partThree_r4 = new ModelRenderer(this);
		partThree_r4.setRotationPoint(-5.3135F, 0.4677F, 0.0F);
		bone4.addChild(partThree_r4);
		setRotationAngle(partThree_r4, 0.0F, 0.0F, -0.2269F);
		partThree_r4.setTextureOffset(9, 49).addBox(-0.6253F, 0.3912F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		earpoint7 = new ModelRenderer(this);
		earpoint7.setRotationPoint(2.6928F, -18.5893F, -7.7F);
		RightEar.addChild(earpoint7);
		setRotationAngle(earpoint7, 0.0F, 0.0F, 0.1745F);
		earpoint7.setTextureOffset(9, 49).addBox(-1.3563F, -1.0404F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		earpoint8 = new ModelRenderer(this);
		earpoint8.setRotationPoint(2.6928F, -18.5893F, -7.7F);
		RightEar.addChild(earpoint8);
		setRotationAngle(earpoint8, 0.0F, 0.0F, -0.1745F);
		earpoint8.setTextureOffset(9, 49).addBox(-1.0083F, -1.281F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		NeckFlower = new ModelRenderer(this);
		NeckFlower.setRotationPoint(-0.25F, 14.765F, 7.175F);
		head.addChild(NeckFlower);

		SideNeckFlowers = new ModelRenderer(this);
		SideNeckFlowers.setRotationPoint(2.45F, -19.2F, -8.0F);
		NeckFlower.addChild(SideNeckFlowers);
		setRotationAngle(SideNeckFlowers, 0.0F, 0.0F, 0.2618F);
		SideNeckFlowers.setTextureOffset(52, 10).addBox(-0.8106F, -2.6591F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		NeckFlowers = new ModelRenderer(this);
		NeckFlowers.setRotationPoint(-0.5F, -17.9F, 0.5F);
		NeckFlower.addChild(NeckFlowers);
		NeckFlowers.setTextureOffset(52, 13).addBox(-0.9F, -4.1F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(0, 19).addBox(0.85F, -4.1F, -7.2F, 1.0F, 4.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(32, 52).addBox(1.65F, -3.8F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(42, 52).addBox(0.25F, -3.5F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(34, 52).addBox(-0.35F, -3.0F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(0, 49).addBox(-1.35F, -3.6F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		NeckFlowers.setTextureOffset(46, 47).addBox(-2.35F, -4.1F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers2 = new ModelRenderer(this);
		SideNeckFlowers2.setRotationPoint(3.1783F, -17.8065F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers2);
		setRotationAngle(SideNeckFlowers2, 0.0F, 0.0F, 0.925F);
		SideNeckFlowers2.setTextureOffset(52, 0).addBox(-1.4584F, -2.2222F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		SideNeckFlowers2.setTextureOffset(51, 30).addBox(-2.698F, -2.405F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		SideNeckFlowers2.setTextureOffset(24, 50).addBox(-1.8287F, -2.063F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers3 = new ModelRenderer(this);
		SideNeckFlowers3.setRotationPoint(-3.0921F, -18.0474F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers3);
		setRotationAngle(SideNeckFlowers3, 0.0F, 0.0F, -0.576F);
		SideNeckFlowers3.setTextureOffset(22, 50).addBox(0.1536F, -2.5064F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		SideNeckFlowers3.setTextureOffset(44, 47).addBox(1.0337F, -3.1272F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers6 = new ModelRenderer(this);
		SideNeckFlowers6.setRotationPoint(-3.3961F, -17.2221F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers6);
		setRotationAngle(SideNeckFlowers6, 0.0F, 0.0F, -0.9599F);
		SideNeckFlowers6.setTextureOffset(43, 19).addBox(0.483F, -2.1883F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers7 = new ModelRenderer(this);
		SideNeckFlowers7.setRotationPoint(-3.3961F, -16.4221F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers7);
		setRotationAngle(SideNeckFlowers7, 0.0F, 0.0F, 1.85F);
		SideNeckFlowers7.setTextureOffset(32, 43).addBox(-1.6535F, -1.1692F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers5 = new ModelRenderer(this);
		SideNeckFlowers5.setRotationPoint(3.3783F, -16.6065F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers5);
		setRotationAngle(SideNeckFlowers5, 0.0F, 0.0F, 1.6057F);
		SideNeckFlowers5.setTextureOffset(45, 19).addBox(-1.6993F, -1.4581F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		SideNeckFlowers4 = new ModelRenderer(this);
		SideNeckFlowers4.setRotationPoint(3.1783F, -17.2065F, -8.2F);
		NeckFlower.addChild(SideNeckFlowers4);
		setRotationAngle(SideNeckFlowers4, 0.0F, 0.0F, 1.2741F);
		SideNeckFlowers4.setTextureOffset(32, 46).addBox(-1.6476F, -1.8508F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		Thing = new ModelRenderer(this);
		Thing.setRotationPoint(-0.25F, -0.455F, -2.725F);
		head.addChild(Thing);
		setRotationAngle(Thing, -0.6109F, 0.0F, 0.0F);
		Thing.setTextureOffset(30, 19).addBox(-2.0F, 0.8276F, -1.4958F, 4.0F, 1.0F, 5.0F, 0.0F, false);

		AnotherThing = new ModelRenderer(this);
		AnotherThing.setRotationPoint(0.75F, 2.465F, 0.275F);
		head.addChild(AnotherThing);

		head_r1 = new ModelRenderer(this);
		head_r1.setRotationPoint(-2.9598F, -0.3126F, -4.6507F);
		AnotherThing.addChild(head_r1);
		setRotationAngle(head_r1, 0.8552F, 0.0F, -1.5882F);
		head_r1.setTextureOffset(28, 38).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		Thing3 = new ModelRenderer(this);
		Thing3.setRotationPoint(0.75F, 2.465F, 0.275F);
		head.addChild(Thing3);

		head_r2 = new ModelRenderer(this);
		head_r2.setRotationPoint(0.9773F, -1.3125F, -4.6493F);
		Thing3.addChild(head_r2);
		setRotationAngle(head_r2, -0.8552F, 0.0F, 1.5882F);
		head_r2.setTextureOffset(28, 38).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		LeftEar = new ModelRenderer(this);
		LeftEar.setRotationPoint(-4.15F, 12.965F, 7.275F);
		head.addChild(LeftEar);
		LeftEar.setTextureOffset(0, 38).addBox(1.0F, -16.9F, -8.7F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
		LeftEar.addChild(bone5);

		partThree_r5 = new ModelRenderer(this);
		partThree_r5.setRotationPoint(2.2612F, -18.265F, -7.7F);
		bone5.addChild(partThree_r5);
		setRotationAngle(partThree_r5, 0.0F, 0.0F, -0.2269F);
		partThree_r5.setTextureOffset(9, 49).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(1.5997F, -18.1747F, -7.7F);
		LeftEar.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.0F, -0.0873F);

		partThree_r6 = new ModelRenderer(this);
		partThree_r6.setRotationPoint(-0.0304F, -0.0537F, 0.0F);
		bone6.addChild(partThree_r6);
		setRotationAngle(partThree_r6, 0.0F, 0.0F, 0.2269F);
		partThree_r6.setTextureOffset(9, 49).addBox(-0.3747F, 0.3912F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0.0F, 0.0F, 0.0F);
		LeftEar.addChild(bone7);

		partThree_r7 = new ModelRenderer(this);
		partThree_r7.setRotationPoint(1.7388F, -18.265F, -7.7F);
		bone7.addChild(partThree_r7);
		setRotationAngle(partThree_r7, 0.0F, 0.0F, 0.2269F);
		partThree_r7.setTextureOffset(9, 49).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(7.7693F, -18.2284F, -7.7F);
		LeftEar.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, 0.0873F);

		partThree_r8 = new ModelRenderer(this);
		partThree_r8.setRotationPoint(-5.3135F, 0.4677F, 0.0F);
		bone8.addChild(partThree_r8);
		setRotationAngle(partThree_r8, 0.0F, 0.0F, -0.2269F);
		partThree_r8.setTextureOffset(9, 49).addBox(-0.6253F, 0.3912F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		earpoint3 = new ModelRenderer(this);
		earpoint3.setRotationPoint(2.6928F, -18.5893F, -7.7F);
		LeftEar.addChild(earpoint3);
		setRotationAngle(earpoint3, 0.0F, 0.0F, 0.1745F);
		earpoint3.setTextureOffset(9, 49).addBox(-1.3563F, -1.0404F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		earpoint4 = new ModelRenderer(this);
		earpoint4.setRotationPoint(2.6928F, -18.5893F, -7.7F);
		LeftEar.addChild(earpoint4);
		setRotationAngle(earpoint4, 0.0F, 0.0F, -0.1745F);
		earpoint4.setTextureOffset(9, 49).addBox(-1.0083F, -1.281F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		earpoint5 = new ModelRenderer(this);
		earpoint5.setRotationPoint(0.3389F, -19.888F, -7.7F);
		LeftEar.addChild(earpoint5);
		setRotationAngle(earpoint5, 0.0F, 0.0F, 0.733F);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 10.35F, 7.3F);
		setRotationAngle(tail, 1.2741F, 0.0F, 0.0F);
		tail.setTextureOffset(12, 55).addBox(-1.0F, -0.1249F, -0.7524F, 2.0F, 7.0F, 2.0F, 0.0F, false);

		TailFlowersleft = new ModelRenderer(this);
		TailFlowersleft.setRotationPoint(-0.4F, 2.8043F, -2.332F);
		tail.addChild(TailFlowersleft);
		setRotationAngle(TailFlowersleft, -1.3439F, -0.2443F, 0.0873F);

		lefttailflower4_r1 = new ModelRenderer(this);
		lefttailflower4_r1.setRotationPoint(0.5831F, -2.2701F, -0.0942F);
		TailFlowersleft.addChild(lefttailflower4_r1);
		setRotationAngle(lefttailflower4_r1, -1.1868F, -0.1745F, 0.0F);
		lefttailflower4_r1.setTextureOffset(18, 38).addBox(0.5601F, -4.2396F, 0.2554F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		lefttailflower4_r1.setTextureOffset(40, 5).addBox(0.294F, -3.3121F, -0.4718F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		lefttailflower4_r1.setTextureOffset(40, 30).addBox(0.0037F, -2.2901F, -1.2209F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		lefttailflower4_r1.setTextureOffset(42, 0).addBox(-0.3592F, -0.9394F, -1.8407F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		TailFlowersRight2 = new ModelRenderer(this);
		TailFlowersRight2.setRotationPoint(0.1F, 2.8043F, -2.332F);
		tail.addChild(TailFlowersRight2);
		setRotationAngle(TailFlowersRight2, 0.0F, 0.0F, -0.1047F);

		righttailflower4_r1 = new ModelRenderer(this);
		righttailflower4_r1.setRotationPoint(-0.5643F, -0.4786F, 1.7497F);
		TailFlowersRight2.addChild(righttailflower4_r1);
		setRotationAngle(righttailflower4_r1, -2.5656F, 0.0F, -0.2967F);
		righttailflower4_r1.setTextureOffset(32, 40).addBox(-1.0297F, -4.7588F, -0.1955F, 1.0F, 3.0F, 0.0F, 0.0F,
				false);
		righttailflower4_r1.setTextureOffset(40, 0).addBox(-0.6176F, -3.8775F, -0.9122F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		righttailflower4_r1.setTextureOffset(41, 25).addBox(0.0745F, -2.5585F, -1.6272F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		righttailflower4_r1.setTextureOffset(42, 5).addBox(0.5615F, -1.474F, -2.2881F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		bigbackflowerright = new ModelRenderer(this);
		bigbackflowerright.setRotationPoint(-5.7F, 5.1F, -3.0F);
		setRotationAngle(bigbackflowerright, -0.576F, 0.0F, -0.8203F);
		bigbackflowerright.setTextureOffset(0, 49).addBox(-0.5768F, -3.9037F, -0.0368F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(52, 24).addBox(-3.0681F, -3.8072F, -0.0345F, 4.0F, 1.0F, 3.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(52, 0).addBox(1.3289F, -3.8264F, -0.0469F, 4.0F, 1.0F, 3.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(32, 52).addBox(-0.6112F, -3.8743F, 1.5913F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(0, 19).addBox(-0.6202F, -3.7241F, -2.8883F, 3.0F, 0.0F, 4.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(44, 48).addBox(0.6518F, -3.7413F, -2.7802F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(32, 47).addBox(-2.5413F, -3.7462F, -2.7834F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(20, 45).addBox(-2.6688F, -3.8557F, 1.3649F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerright.setTextureOffset(43, 19).addBox(0.6657F, -3.8466F, 1.3707F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		bigbackflowerleft = new ModelRenderer(this);
		bigbackflowerleft.setRotationPoint(3.0456F, 8.3907F, -2.8813F);
		setRotationAngle(bigbackflowerleft, -0.576F, 0.0F, 0.8203F);
		bigbackflowerleft.setTextureOffset(0, 0).addBox(-3.2542F, -7.4285F, -2.3054F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(40, 30).addBox(-1.5475F, -7.3491F, -2.3071F, 4.0F, 1.0F, 3.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(30, 25).addBox(-6.2165F, -7.331F, -2.2953F, 4.0F, 1.0F, 3.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(22, 51).addBox(-3.2675F, -7.4174F, -0.8014F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(12, 50).addBox(-3.2733F, -7.2961F, -5.0151F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(40, 25).addBox(-5.9528F, -7.229F, -1.2752F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(40, 5).addBox(-5.8757F, -7.3036F, -4.543F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(40, 0).addBox(-1.9133F, -7.3009F, -4.5413F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bigbackflowerleft.setTextureOffset(16, 38).addBox(-1.9223F, -7.3966F, -1.1456F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);

		back = new ModelRenderer(this);
		back.setRotationPoint(0.0F, -10.0F, 2.0F);
		body.addChild(back);

		body_rotation = new ModelRenderer(this);
		body_rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
		back.addChild(body_rotation);
		setRotationAngle(body_rotation, 1.5708F, 0.0F, 0.0F);

		body_sub_1 = new ModelRenderer(this);
		body_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		body_rotation.addChild(body_sub_1);
		body_sub_1.setTextureOffset(0, 19).addBox(-5.1F, -2.7F, -1.8F, 10.0F, 9.0F, 10.0F, 0.0F, false);

		front = new ModelRenderer(this);
		front.setRotationPoint(1.0F, -10.0F, 2.0F);
		body.addChild(front);

		mane_rotation = new ModelRenderer(this);
		mane_rotation.setRotationPoint(-1.0F, 2.5F, -2.5F);
		front.addChild(mane_rotation);
		setRotationAngle(mane_rotation, 1.5708F, 0.0F, 0.0F);

		mane_sub_1 = new ModelRenderer(this);
		mane_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		mane_rotation.addChild(mane_sub_1);
		mane_sub_1.setTextureOffset(0, 0).addBox(-7.1F, -6.2F, 0.7F, 14.0F, 7.0F, 12.0F, 0.0F, false);

		BackLeftLeg = new ModelRenderer(this);
		BackLeftLeg.setRotationPoint(3.25F, 16.0F, 6.25F);
		BackLeftLeg.setTextureOffset(54, 28).addBox(-1.0F, -1.0F, -0.95F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		BackLeftLeg.setTextureOffset(52, 5).addBox(-1.0F, 7.0F, -1.95F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		BackRightLeg = new ModelRenderer(this);
		BackRightLeg.setRotationPoint(-3.5F, 16.0F, 6.5F);
		BackRightLeg.setTextureOffset(54, 8).addBox(-1.05F, -1.0F, -1.2F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		BackRightLeg.setTextureOffset(16, 43).addBox(-1.05F, 7.0F, -2.2F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		FrontLeftLEg = new ModelRenderer(this);
		FrontLeftLEg.setRotationPoint(5.5F, 16.0F, -5.25F);
		FrontLeftLEg.setTextureOffset(54, 54).addBox(-1.4F, -1.0F, -0.85F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		FrontLeftLEg.setTextureOffset(0, 43).addBox(-1.4F, 7.0F, -1.75F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		FrontRightLeg = new ModelRenderer(this);
		FrontRightLeg.setRotationPoint(-5.5F, 16.0F, -5.5F);
		FrontRightLeg.setTextureOffset(46, 53).addBox(-0.65F, -1.0F, -0.8F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		FrontRightLeg.setTextureOffset(0, 9).addBox(-0.65F, 7.0F, -1.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		LowerBackFlowers = new ModelRenderer(this);
		LowerBackFlowers.setRotationPoint(0.0F, 24.0F, 0.0F);

		backleftbackflowers = new ModelRenderer(this);
		backleftbackflowers.setRotationPoint(0.963F, -21.6845F, -6.1391F);
		LowerBackFlowers.addChild(backleftbackflowers);
		setRotationAngle(backleftbackflowers, -1.1519F, 0.8552F, 0.0F);
		backleftbackflowers.setTextureOffset(28, 40).addBox(-6.1406F, -3.6798F, 4.2618F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backleftbackflowers.setTextureOffset(38, 38).addBox(-6.9708F, -4.1764F, 4.9207F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backleftbackflowers.setTextureOffset(36, 38).addBox(-7.8764F, -4.8956F, 5.2409F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backleftbackflowers.setTextureOffset(6, 38).addBox(-8.7821F, -5.6148F, 5.5611F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backleftbackflowers.setTextureOffset(0, 38).addBox(-9.8387F, -6.4539F, 5.9347F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backleftbackflowers.setTextureOffset(0, 9).addBox(-10.7443F, -7.1731F, 6.2549F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		backrightbackflowers = new ModelRenderer(this);
		backrightbackflowers.setRotationPoint(-0.437F, -18.9845F, -6.4391F);
		LowerBackFlowers.addChild(backrightbackflowers);
		setRotationAngle(backrightbackflowers, -1.1519F, -0.8552F, 0.0F);
		backrightbackflowers.setTextureOffset(6, 43).addBox(4.783F, -5.492F, 2.3247F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backrightbackflowers.setTextureOffset(0, 43).addBox(5.6131F, -6.0293F, 2.8923F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backrightbackflowers.setTextureOffset(42, 10).addBox(6.5188F, -6.7485F, 3.2125F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backrightbackflowers.setTextureOffset(40, 10).addBox(7.4245F, -7.4677F, 3.5327F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backrightbackflowers.setTextureOffset(30, 40).addBox(8.481F, -8.3068F, 3.9063F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		backrightbackflowers.setTextureOffset(6, 9).addBox(9.3867F, -9.026F, 4.2265F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		backmiddleBackFlowers = new ModelRenderer(this);
		backmiddleBackFlowers.setRotationPoint(-0.237F, -19.3845F, -6.3391F);
		LowerBackFlowers.addChild(backmiddleBackFlowers);
		setRotationAngle(backmiddleBackFlowers, -1.0821F, 0.0F, 0.0F);
		backmiddleBackFlowers.setTextureOffset(30, 19).addBox(-0.5F, -7.4459F, 3.7278F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		backmiddleBackFlowers.setTextureOffset(8, 26).addBox(-0.5F, -8.2294F, 4.5974F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		backmiddleBackFlowers.setTextureOffset(8, 23).addBox(-0.5F, -9.2889F, 5.1607F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		backmiddleBackFlowers.setTextureOffset(2, 19).addBox(-0.5F, -10.3484F, 5.7241F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		backmiddleBackFlowers.setTextureOffset(10, 9).addBox(-0.5F, -11.5846F, 6.3814F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		backmiddleBackFlowers.setTextureOffset(0, 0).addBox(-0.5F, -12.4675F, 6.8508F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		UpperBackFlowers = new ModelRenderer(this);
		UpperBackFlowers.setRotationPoint(0.0F, 24.0F, 0.4F);

		frontmiddleBackFlowers = new ModelRenderer(this);
		frontmiddleBackFlowers.setRotationPoint(-0.237F, -19.3845F, -6.3391F);
		UpperBackFlowers.addChild(frontmiddleBackFlowers);
		setRotationAngle(frontmiddleBackFlowers, -1.0821F, 0.0F, 0.0F);
		frontmiddleBackFlowers.setTextureOffset(16, 38).addBox(-0.5F, -3.6527F, -0.2145F, 1.0F, 3.0F, 0.0F, 0.0F,
				false);
		frontmiddleBackFlowers.setTextureOffset(9, 0).addBox(-0.5F, -2.5048F, -0.8248F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		frontmiddleBackFlowers.setTextureOffset(32, 19).addBox(-0.5F, -4.7122F, 0.3489F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		frontmiddleBackFlowers.setTextureOffset(30, 25).addBox(-0.5F, -5.7717F, 0.9122F, 1.0F, 3.0F, 0.0F, 0.0F, false);
		frontmiddleBackFlowers.setTextureOffset(8, 9).addBox(-0.5F, -6.6547F, 1.3817F, 1.0F, 3.0F, 0.0F, 0.0F, false);

		frontrightbackflowers = new ModelRenderer(this);
		frontrightbackflowers.setRotationPoint(-0.437F, -18.9845F, -6.4391F);
		UpperBackFlowers.addChild(frontrightbackflowers);
		setRotationAngle(frontrightbackflowers, -1.1519F, -0.8552F, 0.0F);
		frontrightbackflowers.setTextureOffset(30, 43).addBox(0.7075F, -2.947F, -0.6693F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontrightbackflowers.setTextureOffset(32, 22).addBox(-0.1981F, -2.2278F, -0.9895F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontrightbackflowers.setTextureOffset(28, 43).addBox(1.6132F, -3.6662F, -0.3491F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontrightbackflowers.setTextureOffset(26, 43).addBox(2.5188F, -4.3854F, -0.0289F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontrightbackflowers.setTextureOffset(6, 23).addBox(3.5F, -5.1646F, 0.318F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		frontleftbackflowers = new ModelRenderer(this);
		frontleftbackflowers.setRotationPoint(0.963F, -21.6845F, -6.1391F);
		UpperBackFlowers.addChild(frontleftbackflowers);
		setRotationAngle(frontleftbackflowers, -1.1519F, 0.8552F, 0.0F);
		frontleftbackflowers.setTextureOffset(24, 43).addBox(-2.1406F, -1.1948F, 1.2945F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontleftbackflowers.setTextureOffset(30, 22).addBox(-1.3105F, -0.5355F, 1.001F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		frontleftbackflowers.setTextureOffset(22, 43).addBox(-3.0463F, -1.914F, 1.6147F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		frontleftbackflowers.setTextureOffset(16, 43).addBox(-4.0274F, -2.6931F, 1.9616F, 1.0F, 2.0F, 0.0F, 0.0F,
				false);
		frontleftbackflowers.setTextureOffset(0, 23).addBox(-4.9331F, -3.4123F, 2.2818F, 1.0F, 2.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		bigbackflowerright.render(matrixStack, buffer, packedLight, packedOverlay);
		bigbackflowerleft.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		BackLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		BackRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		FrontLeftLEg.render(matrixStack, buffer, packedLight, packedOverlay);
		FrontRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		LowerBackFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
		UpperBackFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.FrontRightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.BackRightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.BackLeftLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
		this.FrontLeftLEg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
	}
}