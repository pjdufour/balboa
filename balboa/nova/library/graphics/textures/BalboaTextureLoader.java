package nova.library.graphics.textures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nova.game.Balboa;
import nova.library.logs.Logs;
import nova.library.xml.XMLElementList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BalboaTextureLoader
{
	public Balboa balboa;
	public BalboaTextureLoader(Balboa balboa)
	{
		this.balboa = balboa;
	}
	/**
	 * Also Load Procedural Textures Here (create InMemory Image)
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 */
	public LinkedHashMap<Integer,BalboaTexture> load(String filename)
	{
		balboa.getLogs().addToLog(Logs.LOG_SYSTEM_DEBUG,"Loading Textures");
		LinkedHashMap<Integer,BalboaTexture> textures = null;
		try{
			textures = new LinkedHashMap<>();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new FileInputStream(new File(filename)));
			for(Node xmlTexture: XMLElementList.createListWithTagSelector("texture",doc.getElementsByTagName("textures").item(0)))
			{
				NamedNodeMap attributes = xmlTexture.getAttributes();
				int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
				String name = attributes.getNamedItem("name").getNodeValue();
				if(attributes.getNamedItem("type").getNodeValue().equals("file"))
				{
					String xmlTextureFilename = "";
					NodeList nodes = xmlTexture.getChildNodes();
					for(int j = 0; j < nodes.getLength(); j++)
					{
						Node node = nodes.item(j);
						if(node.getNodeName().equals("file"))
							xmlTextureFilename = node.getTextContent();
					}
					textures.put(id,BalboaTexture.createFileTexture(balboa,name,xmlTextureFilename));
				}
				else if(attributes.getNamedItem("type").getNodeValue().equals("procedural"))
				{	
					textures.put(id,BalboaTexture.createProceduralTexture(balboa,name,xmlTexture));
				}
				else if(attributes.getNamedItem("type").getNodeValue().equals("python"))
				{	
					textures.put(id,BalboaTexture.createPythonTexture(balboa,name,xmlTexture));
				}
			}
		}catch(ParserConfigurationException|SAXException|IOException e)
		{
			System.err.println("Could not load textures ");
			balboa.getLogs().addToLogs((new int[]{Logs.LOG_SYSTEM_INFO,Logs.LOG_SYSTEM_DEBUG,Logs.LOG_SYSTEM_ERROR}),"Exception "+e.getClass()+": Could not load textures");
			balboa.getLogs().save();
			e.printStackTrace();
			System.exit(-1);
		}
		balboa.getLogs().addToLog(Logs.LOG_SYSTEM_DEBUG,"Done Loading Textures");
		return textures;
	}
}
