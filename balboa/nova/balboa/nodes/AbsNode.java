package nova.balboa.nodes;

public class AbsNode
	implements DoubleNode
{
	private DoubleNode node;
	public AbsNode(DoubleNode node)
	{
		this.node = node;
	}
	public double calculate(double x,double y)
	{
		return Math.abs(node.calculate(x,y));
	}
	
}
