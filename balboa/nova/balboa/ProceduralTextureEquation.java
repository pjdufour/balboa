package balboa;

import java.awt.Color;
import java.util.HashMap;

import balboa.nodes.AbsNode;
import balboa.nodes.AddColorNode;
import balboa.nodes.AddNode;
import balboa.nodes.AndNode;
import balboa.nodes.ColNode;
import balboa.nodes.ColorNode;
import balboa.nodes.DoubleNode;
import balboa.nodes.GthanNode;
import balboa.nodes.GthanOrEqNode;
import balboa.nodes.IsNode;
import balboa.nodes.LthanNode;
import balboa.nodes.LthanOrEqNode;
import balboa.nodes.MaxNode;
import balboa.nodes.MinNode;
import balboa.nodes.ModNode;
import balboa.nodes.MulColorNode;
import balboa.nodes.MulNode;
import balboa.nodes.NoiseNode;
import balboa.nodes.OrNode;
import balboa.nodes.RandNode;
import balboa.nodes.SinNode;
import balboa.nodes.SubNode;
import balboa.nodes.ValNode;
import balboa.nodes.VarNode;
import balboa.xml.*;

import org.w3c.dom.Node;

public class ProceduralTextureEquation
{
	//////////////////////////////////
	private ColorNode top;
	private HashMap<String,Color> colors;
	
	public ProceduralTextureEquation(Node rootNode,HashMap<String,Color> colors)
	{
		this.colors = colors;
		this.top = createColorNode(XMLElementList.createListWithoutTextNodes(rootNode).get(0));
	}
	private ColorNode createColorNode(Node xmlNode)
	{
		String tag = xmlNode.getNodeName();
		if(tag.equalsIgnoreCase("Col"))
		{
			String str = xmlNode.getTextContent();
			if(colors.containsKey(str))
				return new ColNode(colors.get(str));
			else
				return new ColNode(str);
		}
		else
		{
			Node node1 = XMLElementList.createListWithoutTextNodes(xmlNode).get(0);
			Node node2 = XMLElementList.createListWithoutTextNodes(xmlNode).get(1);
			if(tag.equalsIgnoreCase("Add")) return new AddColorNode(createColorNode(node1),createColorNode(node2));  
			else if(tag.equalsIgnoreCase("Mul")) return new MulColorNode(createColorNode(node1),createDoubleNode(node2));
			else {System.err.println("Couldn't find node for "+tag);System.exit(-1);}
		}
		return null;
	}
	private DoubleNode createDoubleNode(Node xmlNode)
	{
		String tag = xmlNode.getNodeName();
		if(tag.equalsIgnoreCase("Val")) return new ValNode(xmlNode.getTextContent());
		else if(tag.equalsIgnoreCase("Var")) return new VarNode(xmlNode.getTextContent()); 
		else
		{
			Node node1 = XMLElementList.createListWithoutTextNodes(xmlNode).get(0);
			Node node2 = XMLElementList.createListWithoutTextNodes(xmlNode).get(1);
			if(tag.equalsIgnoreCase("Add")) return new AddNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Is")) return new IsNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Gthan")) return new GthanNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("GthanOrEq")) return new GthanOrEqNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Lthan")) return new LthanNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("LthanOrEq")) return new LthanOrEqNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Mod")) return new ModNode(createDoubleNode(node1),createDoubleNode(node2)); 
			else if(tag.equalsIgnoreCase("Mul")) return new MulNode(createDoubleNode(node1),createDoubleNode(node2)); 
			else if(tag.equalsIgnoreCase("Min")) return new MinNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Max")) return new MaxNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Sub")) return new SubNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Or")) return new OrNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("And")) return new AndNode(createDoubleNode(node1),createDoubleNode(node2));
			else if(tag.equalsIgnoreCase("Rand")) return new RandNode();
			else if(tag.equalsIgnoreCase("Sin")) return new SinNode(createDoubleNode(node1));
			else if(tag.equalsIgnoreCase("Abs")) return new AbsNode(createDoubleNode(node1));
			else if(tag.equalsIgnoreCase("Noise")) return new NoiseNode();
			else {System.err.println("Couldn't find node for "+tag);System.exit(-1);}	
		}
		return null;
	}
	
	public Color calculate(double x,double y)
	{
		
		return top.calculate(x,y);
	}
}
