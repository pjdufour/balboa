package nova.balboa.nodes;

public class RandNode
	implements DoubleNode
{
	public RandNode()
	{
	}
	public double calculate(double x,double y)
	{
		return Math.random();
	}
}
