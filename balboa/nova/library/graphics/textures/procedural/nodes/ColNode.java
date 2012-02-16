package nova.library.graphics.textures.procedural.nodes;

import java.awt.Color;

public class ColNode
	implements ColorNode
{
	private Color val;
	public ColNode(String arg)
	{
		String values[] = arg.split(",");
		val = new Color(Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2]));
	}
	public ColNode(Color color)
	{
		val = color;
	}
	public Color calculate(double x,double y)
	{
		return val;
	}
}
