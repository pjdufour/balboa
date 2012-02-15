package nova.balboa.nodes;

public class VarNode
	implements DoubleNode
{
	private String var;
	public VarNode(String arg)
	{
		var = arg;
	}
	public double calculate(double x,double y)
	{
		if(var.equalsIgnoreCase("x"))
			return x;
		else if(var.equalsIgnoreCase("y"))
			return y;
		else return 0;
	}
}
