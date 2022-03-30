/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * Attribute is the class that describes what is an attribute 
 * and also associated methods
 */

import java.util.*;

public class Attribute{

	private String nomine;
	private String genus;
	private ArrayList<String> potestValores = new ArrayList<String>();
	private String valoresCommateDeterminata;
	private double maximus;
	private double minimus;
	private boolean estContinuam;
	private int index;

	
	/**
	 * This is the constructor the Attribute class
	 * @param attributumNomine is the name of the attribute
	 * @param attributumGenus is the type of the attribute 
	 * @param attributumValores is the allowable attribute values
	 */
	public Attribute(String attributumNomine, String attributumGenus, String attributumValores){
		nomine = attributumNomine;
		genus = attributumGenus;
		valoresCommateDeterminata = attributumValores;
		setType(attributumGenus);
		parseValues(valoresCommateDeterminata);
	}
	
	/**
	 * This method checks whether the attribute that is passed in is 
	 * the same as the attribute of the instance of this class.
	 * @param attributum is the attribute that is passed into this method
	 * @return whether the two attributes are equivalent or not
	 */
	public boolean equivalent(Attribute attributum){
		if(attributum.getAttributeName().equalsIgnoreCase(nomine) && attributum.getAttributeType().equalsIgnoreCase(genus)
				&& attributum.getPossibleValues().equals(potestValores))
			return true;
		else
			return false;
	}
	

	public void setIndex(int i){
		index = i;
	}
	
	public int getIndex(){
		return index;
	}
	

	private void setType(String attributumGenus){
		if(attributumGenus.equalsIgnoreCase("continuous")){
			estContinuam = true;
		}
		else
			estContinuam = false;
	}
	
	
	/**
	 * This method parses the possible attribute values 
	 * @param valoresCommateDeterminata is the string of the 
	 * comma delimited values read from the file
	 */
	private  void parseValues(String valoresCommateDeterminata){
		String temporalis[] = valoresCommateDeterminata.split(",");
			for(int i = 0; i < temporalis.length; i++){
				potestValores.add(temporalis[i]);
			}
			
			if(estContinuam){
				minimus = Double.parseDouble(temporalis[0]);
				maximus = Double.parseDouble(temporalis[1]);
			}
	}

	public boolean isContinuous(){
		return estContinuam;
	}

	public double getMin(){
		return minimus;
	}

	public double getMax(){
		return maximus;
	}
	
	public String getAttributeName(){
		return nomine;
	}

	public String getAttributeType(){
		return genus;
	}

	public ArrayList<String> getPossibleValues(){
		return potestValores;
	}
	
	public String getValuesString(){
		return nomine + " " + genus + " " + valoresCommateDeterminata;
	}
}