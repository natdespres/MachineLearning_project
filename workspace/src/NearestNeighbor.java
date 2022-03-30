/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * NearestNeighbor is the class that describes the k-Nearest Neighbor
 * algorithm and also associated methods
 */

import java.util.ArrayList;

public class NearestNeighbor{
	
	private ArrayList<Attribute> attributes;
	private ArrayList<Sample> trainingSamples;
	private ArrayList<Sample> testSamples;
	private ArrayList<String> classValues;
	private double accuracy;
	
	/**
	 * Constructor for the NearestNeighbor class
	 * @param a is the attributes of the data set
	 * @param train is the training data set
	 * @param test is the test data set
	 * @param clVals is a list of the allowed class values
	 */
	public NearestNeighbor(ArrayList<Attribute> a, ArrayList<Sample> train, 
							ArrayList<Sample> test, ArrayList<String> clVals){
		
		this.attributes = a;
		this.trainingSamples = train;
		this.testSamples = test;
		this.classValues = clVals;
	}
	
	
	/**
	 * This method creates a list of the nearest k neighbors
	 * @param currentSample is the sample around which the neighbors need to be found
	 * @param k is the number of nearest neighbors that need to be found
	 * @return the nearest neighbors
	 */
	private  ArrayList<Sample> getNearestNeighbors(Sample currentSample, int k){
		
		ArrayList<Sample> nearestNeighbors = new ArrayList<Sample>();
		Sample neighbor = null;
		
		while(nearestNeighbors.size() < k){
			
			double nearestDistance = Double.MAX_VALUE;
			
			for(Sample i: trainingSamples){
					
					//Ensures the current sample is not considered as a neighbor
					if(!i.equals(currentSample) && !nearestNeighbors.contains(i) 
														&& i.getDistance() < nearestDistance ){
						nearestDistance = i.getDistance();
						neighbor = i;
					}
			}
			nearestNeighbors.add(neighbor);
		}
		return nearestNeighbors;
	}
	
	/**
	 * This method verifies the classification of the current sample.
	 * @param currentSample the sample that needs verification
	 * @param nearestNeighbors list of the nearest neighbors
	 * @param k is the number of nearest neighbors that are used for the classification
	 * @return 1 when the current sample is classified correctly, 0 when it is classified incorrectly
	 */
	private double verifyClassification(Sample currentSample, ArrayList<Sample> nearestNeighbors, int k){
		
		int[] classification = new int[classValues.size()];
		String predictedClass;
		
		for(int i = 0; i < k; i++){
			
			for(int j = 0; j < classValues.size(); j++){
				if(nearestNeighbors.get(i).getClassification().equalsIgnoreCase(classValues.get(j)))
					classification[j]++;
			}
		}
		
		int largestClassificationIndex = 0;
		for(int j = 0; j < classValues.size(); j++){
			if(classification[j] > classification[largestClassificationIndex])
				largestClassificationIndex = j;
		}
		predictedClass = classValues.get(largestClassificationIndex);
		
		if(predictedClass.equalsIgnoreCase(currentSample.getClassification())){
			return 1.0;
		}
		else
			return 0.0;
	}
	
	/**
	 * This method performs the kNN algorithm and finds the accuracy
	 * @param k the number of nearest neighbors included in the classification
	 */
	public void run(int k){

		//	Tune the value of k using leave-one-out testing
		double classifiedCorrectly = 0;
		for(int i = 0; i < testSamples.size(); i++){
		
			//calculate the distance between the
			//current sample and the training set
			for(Sample current: trainingSamples){
				current.setDistance(testSamples.get(i), attributes);
			}
			ArrayList<Sample> nearestNeighbors = getNearestNeighbors(testSamples.get(i), k);
			classifiedCorrectly += this.verifyClassification(testSamples.get(i), 
																nearestNeighbors, k);
		}
		accuracy = classifiedCorrectly/(double) testSamples.size();
	}

	public double getAccuracy(){
		return accuracy;
	}	
}