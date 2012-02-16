package nova.library.graphics.textures.procedural.nodes;

public class ValNode
	implements DoubleNode
{
	private double val;
	public ValNode(String arg)
	{
		val = Double.parseDouble(arg);
	}
	public double calculate(double x,double y)
	{
		return val;
	}
}
