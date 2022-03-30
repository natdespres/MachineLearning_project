/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * Sample is the class that describes the what is an individual sample 
 * and also associated methods
 */

import java.util.*;

public class Sample 
{

	private ArrayList<String> values = new ArrayList<String>();
	private double distance;
	private String sampleValuesString;
	private String classification;
	
	/**
	 * This is the constructor for the Sample class
	 * @param line is the string of the attributes of the current sample
	 * @param classIndex is the index at which the class attribute can be found
	 */
	public Sample(String line, int classIndex){
		sampleValuesString = line;
		parseValues(line, classIndex);
	}
	
	/**
	 * This method parses the string of the current sample attributes
	 * @param sampleValues is the string of the attributes of the current sample
	 * @param classIndex is the index at which the class attribute can be found
	 */
	private  void parseValues(String sampleValues, int classIndex){
		String temporary[] = sampleValues.split(",");
		for(int i = 0; i < temporary.length; i++){
			values.add(temporary[i].trim());
		}
		classification = values.get(classIndex).trim();
	}
	
	/**
	 * Calculates the distance between two data points
	 * @param sample is the current sample
	 * @param attributes is the list of attributes
	 */
	public void setDistance(Sample sample, ArrayList<Attribute> attributes)
	{
		double  sumOfSquares = 0.0;
		
		//Calculate the distance between each attribute of the two samples
		for(int i = 0; i < attributes.size(); i++)
		{
			String sampleValue = sample.getValues().get(i);
			Attribute currentAttribute = attributes.get(i);
			
			//For continuous attributes sum the square of the difference
			if(currentAttribute.getAttributeType().equalsIgnoreCase("continuous"))
			{
				sumOfSquares += Math.pow(((Double.parseDouble(sampleValue) 
						- Double.parseDouble(values.get(i)))/currentAttribute.getMax()), 2);
			}
			
			//For discrete attributes turn into boolean result
			else if(currentAttribute.getAttributeType().equalsIgnoreCase("discrete"))
			{
				if(!sampleValue.equalsIgnoreCase(values.get(i)))
				{
					sumOfSquares += 1;
				}
			}
		}
		
		distance = Math.sqrt(sumOfSquares);
		
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public String getClassification()
	{
		return classification;
	}
	
	public void printSample()
	{
		System.out.println(sampleValuesString);
	}
	
	public ArrayList<String> getValues()
	{
		return values;
	}
}