package nova.balboa.nodes;

import java.awt.Color;

public class MulColorNode
	implements ColorNode
{
	private ColorNode node1;
	private DoubleNode node2;
	public MulColorNode(ColorNode node1,DoubleNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}
	public Color calculate(double x,double y)
	{
		float channels[] = node1.calculate(x,y).getRGBColorComponents(null);
		double scalar = node2.calculate(x,y);
		return new Color((float)(scalar*channels[0]),(float)(scalar*channels[1]),(float)(scalar*channels[2]));
	}
}
