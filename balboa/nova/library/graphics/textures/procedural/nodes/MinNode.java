package nova.library.graphics.textures.procedural.nodes;

public class MinNode
	implements DoubleNode
{
	private DoubleNode node1,node2;
	public MinNode(DoubleNode node1,DoubleNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}
	public double calculate(double x,double y)
	{
		return Math.min(node1.calculate(x,y),node2.calculate(x,y));
	}
	
}
