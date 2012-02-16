package nova.library.graphics.textures.procedural.nodes;

import java.awt.Color;

public interface ColorNode extends OpNode
{
	public Color calculate(double x,double y);
}
