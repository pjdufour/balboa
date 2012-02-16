package nova.library.graphics.textures.procedural.nodes;

public class SinNode
	implements DoubleNode
{
	private DoubleNode node;
	public SinNode(DoubleNode node)
	{
		this.node = node;
	}
	public double calculate(double x,double y)
	{
		return Math.sin(node.calculate(x,y));
	}
	
}
