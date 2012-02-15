package nova.balboa.examples;

import java.awt.Color;
import java.util.HashMap;

import nova.balboa.ProceduralTextureEquation;
import nova.balboa.nodes.*;
import nova.balboa.textures.*;
import nova.balboa.xml.*;

import org.w3c.dom.Node;

public class Grass
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BalboaTexture texture = new BalboaTexture();
		texture.game = game;
		texture.name = name;
		texture.type = TEXTURE_TYPE_PROCEDURAL;
		
		HashMap<String,Color> colors = new HashMap<String,Color>();
		for(Node xmlColor: XMLElementList.createListWithTagSelector("color",root))
		{
			String values[] = xmlColor.getTextContent().split(",");
			colors.put(xmlColor.getAttributes().getNamedItem("name").getNodeValue(),new Color(Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2])));
		}
		
		texture.eq_diffuse = new ProceduralTextureEquation(XMLElementList.createListWithTagSelector("diffuse",root).get(0),colors);
		texture.compile();
		
		return texture;
	}

}
