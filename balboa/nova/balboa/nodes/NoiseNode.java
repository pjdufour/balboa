package nova.balboa.nodes;

public class NoiseNode
	implements DoubleNode
{
	private DoubleNode node;
	private int octaves = 4;
	private double amplitude = 1.0;
	
	public NoiseNode()
	{
	}
	public double calculate(double x,double y)
	{
		double s = 0.0;
		for(int i = 1; i <= 8; i++)
		{
			double f = Math.pow(2,i);
			double a = Math.pow(.25,i);
			//s+= Math.abs(Noise.noise(x*f,y*f));
			s+= Noise.noise(x*f,y*f)+1;
		}
		double n = interpolate(s);
		//System.err.println(n);
		return n;
	}
	public double smooth(double n)
	{
		return 6*Math.pow(n,5)-15*Math.pow(n,4)+10*Math.pow(n,3);
	}
	private double interpolate(double n)
	{
		return n-((int)n);
		//return n%1;
	}
}
