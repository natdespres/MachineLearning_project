/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * ContAttributeList is the class that describes how to handle continuous
 * attributes and also associated methods
 */

import java.util.*;

public class ContAttributeList{
	
	private Attribute currentAttribute;
	private ArrayList<Sample> sortedSamplesList = new ArrayList<Sample>();
	private ArrayList<Double> limit = new ArrayList<Double>();
	private ArrayList<String> classValues;
	private double bestLimit;
	private int contAttributeIndex;
	
	/**
	 * This is the constructor for the ContAttributeList
	 * @param smpls is the list of samples
	 * @param ind is the index of the continuous attribute
	 * @param att is the current attribute
	 * @param cls is the list of classValues
	 */
	public ContAttributeList(ArrayList<Sample> smpls, int ind, Attribute att, ArrayList<String> cls){
		contAttributeIndex = ind;
		currentAttribute = att;
		classValues = cls;
		sortSamples(smpls);
		determineLimits();
		setBestLimits();
	}
	
	/**
	 * This method sorts the sample list by increasing values of the current continuous attribute
	 * @param unsortedSampleList original list of unsorted samples
	 */
	private void sortSamples(ArrayList<Sample> unsortedSampleList){
		double minValue = Double.parseDouble(unsortedSampleList.get(0).getValues().get(currentAttribute.getIndex()));
		
		for(Sample sample: unsortedSampleList){
			double temporary = Double.parseDouble(sample.getValues().get(currentAttribute.getIndex()));
			
			if(temporary <= minValue && !sortedSamplesList.contains(sample)){
				minValue = temporary;
				sortedSamplesList.add(sample);
			}
		}
	}
	
	/**
	 * This method finds the mid points between adjacent samples 
	 * where there is a change in classification and then adds these limits
	 * to the limit array list.
	 */
	private void determineLimits(){
		for(int i = 1; i < sortedSamplesList.size(); i++){
			Sample previous = sortedSamplesList.get(i - 1);
			Sample current = sortedSamplesList.get(i);
			
			if(!previous.getClassification().equalsIgnoreCase(current.getClassification())){
				
				double currentVal = Double.parseDouble(current.getValues().get(contAttributeIndex));
				double previousVal = Double.parseDouble(previous.getValues().get(contAttributeIndex));
				double lim = (currentVal + previousVal)/2.0;
				limit.add(lim);
			}
		}
	}
	
	/**
	 * This method searches through the limit array list to find the 
	 * best limit at which to split the continuous attribute.
	 */
	private void setBestLimits(){
		if(limit.size() == 0){
			double total = 0.0;
			for(Sample sample: sortedSamplesList){
				total += Double.parseDouble(sample.getValues().get(contAttributeIndex));
			}
			bestLimit = total/sortedSamplesList.size();
		}
		else{	
			double bestPerformance = limit.get(0);
			for(double lim: limit){
				double currentScore = getLimitPerformance(lim);
				if(currentScore > bestPerformance){
					bestPerformance = currentScore;
				}
			}
			 bestLimit = bestPerformance;
		}
	}
	

	private double getLimitPerformance(double limit){
		double section1 = 0;
		double section2 = 0;
		
		for(Sample sample: sortedSamplesList){
			double temporary = Double.parseDouble(sample.getValues().get(contAttributeIndex));
			if(temporary > limit){
				if(sample.getClassification().equals(classValues.get(0)))
					section1++;
				else
					section2++;
			}
		}
		
		double fraction1 = section1/sortedSamplesList.size();
		double fraction2 = section2/sortedSamplesList.size();
		
		double totalEntropy = -fraction1*(Math.log(fraction1)/Math.log(2)) - fraction2*(Math.log(fraction2)/Math.log(2));
		
		return totalEntropy;
	}


	public int getcontAttributeIndex(){
		return contAttributeIndex;
	}
	
	public ArrayList<Sample> getSortedList(){
		return sortedSamplesList;
	}
	
	public ArrayList<Double> getLimit(){
		return limit;
	}

	public Attribute getAttribute(){
		return currentAttribute;
	}
	
	public double getBestLimit(){
		return bestLimit;
	}
}