package nova.balboa.nodes;

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
