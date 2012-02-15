package nova.balboa.nodes;

public class LthanNode
	implements DoubleNode
{
	private DoubleNode node1,node2;
	public LthanNode(DoubleNode node1,DoubleNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}
	public double calculate(double x,double y)
	{
		return node1.calculate(x,y)<node2.calculate(x,y)?1.0:0.0;
	}
	
}
