package nova.balboa.textures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import org.python.core.Py;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import nova.library.core.Game;
import nova.library.core.Settings;
import nova.balboa.*;
import nova.balboa.nodes.*;
import nova.library.utilities.Parser;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class BalboaTexture
{
	public static int TEXTURE_TYPE_FILE = 1;
	public static int TEXTURE_TYPE_PROCEDURAL = 2;
	public static int TEXTURE_TYPE_PYTHON = 3;
	
	private Game game;
	public int type;
	public String name;
	public String filename;
	public String filename_python;
	
	public ProceduralTextureEquation eq_diffuse;
	
	//Compiled
	public Texture texture;
	public float bottom;
	public float left;
	public float right;
	public float top;
	
	public static BalboaTexture createFileTexture(Game game,String name,String filename)
	{
		BalboaTexture texture = new BalboaTexture();
		texture.game = game;
		texture.name = name;
		texture.type = TEXTURE_TYPE_FILE;
		texture.filename = filename;
		return texture;
	}
	public static BalboaTexture createProceduralTexture(Game game,String name,Node root)
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
		return texture;
	}
	public static BalboaTexture createPythonTexture(Game game,String name,Node root)
	{
		BalboaTexture texture = new BalboaTexture();
		texture.game = game;
		texture.name = name;
		texture.type = TEXTURE_TYPE_PYTHON;
		Node fileNode = XMLElementList.createListWithTagSelector("file",root).get(0);
		if(fileNode!=null)
		{
			texture.filename_python = fileNode.getTextContent();
		}
		else
		{
			
		}
		//texture.eq_diffuse = new ProceduralTextureEquation(XMLElementList.createListWithTagSelector("script",root).get(0),colors);		
		return texture;
	}
	public void compile() throws GLException, IOException, ScriptException
	{
		if(type==TEXTURE_TYPE_FILE)
		{
			texture = TextureIO.newTexture(new File(filename),true);
			//texture = TextureIO.newTexture(ImageIO.read(Parser.class.getResourceAsStream(filename)),true);
		}
		else if(type==TEXTURE_TYPE_PROCEDURAL)
		{
			int width = 400;
			BufferedImage image = new BufferedImage(width,width,BufferedImage.TYPE_INT_RGB);
			double pixel_width= 1.0/width;
			for(int y = 0; y < width; y++)
			{
				for(int x = 0; x < width; x++)
				{
					image.setRGB(x,width-y-1,eq_diffuse.calculate(pixel_width*(x+1),pixel_width*(y+1)).getRGB());
				}
			}
			//Following should be working, but isn'texture = AWTTextureIO.newTexture(image,true);//
			
			//Create New Texture In Memory
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image,"png",os); 
			texture = TextureIO.newTexture(new ByteArrayInputStream(os.toByteArray()),true,"png");
			
			/*Write To File (For Now Not Applicable, since it just read from memory)
			wd
			//ImageIO.write(image,"png",new File("C:/ProceduralTextures/"+name+".png"));
			String path = game.getSettings().getString(Settings.CACHE_TEXTURES);
			ImageIO.write(image,"png",new JarOutputStream(new FileOutputStream(path)));
			
			JarFile file =  new JarFile(new File(path));
			
			
			//JarEntry file = new JarEntry();
			
			texture = TextureIO.newTexture(image.,true,"png") */
			
			//texture = TextureIO.newTexture(new JarInputStream(path),true);//new File("C:/ProceduralTextures/"+name+".png"),true);
		}
		else if(type==TEXTURE_TYPE_PYTHON)
		{
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("python");
			engine.eval(new FileReader(filename_python));
			PyFunction calculate = (PyFunction) engine.getBindings(ScriptContext.ENGINE_SCOPE).get("calculate");
			
			int width = 400;
			BufferedImage image = new BufferedImage(width,width,BufferedImage.TYPE_INT_RGB);
			double pixel_width= 1.0/width;
			for(int y = 0; y < width; y++)
			{
				for(int x = 0; x < width; x++)
				{
					image.setRGB(x,width-y-1,Py.tojava(calculate.__call__(new PyFloat(pixel_width*(x+1)),new PyFloat(pixel_width*(y+1))),Color.class).getRGB());
				}
			}
			//Following should be working, but isn't	texture = AWTTextureIO.newTexture(image,true);//
			//ImageIO.write(image,"png",new File("C:/ProceduralTextures/"+name+".png"));
			//texture = TextureIO.newTexture(new File("C:/ProceduralTextures/"+name+".png"),true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image,"png",os); 
			texture = TextureIO.newTexture(new ByteArrayInputStream(os.toByteArray()),true,"png");
		}
		TextureCoords coords = texture.getImageTexCoords();
		bottom = coords.bottom();//0.0 or 1.0
		left = coords.left();//0.0 or 1.0
		right = coords.right();//0.0 or 1.0
		top = coords.top();//0.0 or 1.0
	}
	public void bind(GL2 gl)
	{
		if(type==TEXTURE_TYPE_FILE)
		{
			texture.bind(gl);
		}
		else if(type==TEXTURE_TYPE_PROCEDURAL)
		{
			texture.bind(gl);
		}
		else if(type==TEXTURE_TYPE_PYTHON)
		{
			texture.bind(gl);
		}
	}
}
