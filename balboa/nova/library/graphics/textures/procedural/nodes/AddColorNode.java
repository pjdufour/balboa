package nova.library.graphics.textures.procedural.nodes;

import java.awt.Color;

public class AddColorNode
	implements ColorNode
{
	private ColorNode node1;
	private ColorNode node2;
	public AddColorNode(ColorNode node1,ColorNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}
	public Color calculate(double x,double y)
	{
		float c1[] = node1.calculate(x,y).getRGBColorComponents(null);
		float c2[] = node2.calculate(x,y).getRGBColorComponents(null);
		return new Color(c1[0]+c2[0],c1[1]+c2[1],c1[2]+c2[2]);
	}
}
