package nova.library.graphics.textures.procedural.nodes;

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
