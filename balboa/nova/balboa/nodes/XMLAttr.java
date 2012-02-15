package nova.balboa.nodes;

import org.w3c.dom.Node;

public class XMLAttr
{
	public String getString(Node node,String attr)
	{
		return node.getAttributes().getNamedItem(attr).getNodeValue();
	}
}
