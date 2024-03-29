package nova.library.graphics.textures.procedural.nodes;

public class OrNode
	implements DoubleNode
{
	private DoubleNode node1,node2;
	public OrNode(DoubleNode node1,DoubleNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}
	public double calculate(double x,double y)
	{
		return (node1.calculate(x,y)==1)||(node2.calculate(x,y)==1)?1.0:0.0;
	}
	
}
