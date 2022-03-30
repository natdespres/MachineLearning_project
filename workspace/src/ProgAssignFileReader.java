/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * ProgAssignFileReader is the class that describes how files are read
 * and also the associated methods.
 */

import java.util.*;
import java.io.*;

public class ProgAssignFileReader 
{
	private ArrayList<Attribute> attributes;
	private ArrayList<Sample>trainingSamples;
	private ArrayList<Sample>testSamples;
	private ArrayList<String> classValues;
	
	public ProgAssignFileReader(){

	}
	
	/**
	 * This method reads the data set files and creates
	 * the attribute, training and testing sets.
	 * @param attributeFile is the name of the file that 
	 * contains the name and allowable values of the attributes
	 * @param trainingFile is the name of the file that 
	 * contains the training sample set 
	 * @param testFile is the name of the file that 
	 * contains the testing sample set
	 */
	public void read(String attributeFile, String trainingFile, String testFile){
		
		int classIndex = 0;
		attributes = this.createAttributes(attributeFile);
		
		//Determine the index for the output feature value
		for(int j = 0; j < attributes.size(); j++){
			if(attributes.get(j).getAttributeType().equalsIgnoreCase("class"));
			{
				classIndex = j;
				classValues = attributes.get(j).getPossibleValues();
			}
		}
		
		//create list of training examples
		trainingSamples = this.createSampleSet(trainingFile, classIndex);
		
		//create list of test examples
		testSamples = this.createSampleSet(testFile, classIndex);
	}

	/**
	 * This method reads the attribute file and creates an ArrayList of
	 * type Attribute; which is a list of all the attributes.
	 * @param attributeFile is the name of the file that 
	 * contains the name and allowable values of the attributes
	 * @return the arraylist of attributes for the data set
	 */
	public ArrayList<Attribute> createAttributes(String attributeFile){

		final int ATTRIBUTE_FIELDS = 3;
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(attributeFile));
			String line;
		
			int classIndex = 0;
			while((line = in.readLine())  != null) {

				 if(!line.startsWith("//") && (line.length() != 0)){
					 Scanner scanner = new Scanner(line);
					
					 //Each attribute needs to have three fields: (name, type, values)
					 String[] temporary = new String[ATTRIBUTE_FIELDS];
					 
			         for(int i = 0; i < ATTRIBUTE_FIELDS; i++){
			        	 temporary[i] = scanner.next();
					 }
			         
			         Attribute current = new Attribute(temporary[0], temporary[1], temporary[2]);
			         current.setIndex(classIndex);
					 attributes.add(current); 
					 classIndex++;
				 }
				
			}
			in.close();
		}

		catch(FileNotFoundException e){
			System.out.println("File " + attributeFile + " not found");
			System.exit(0);
		}
		
		catch(IOException e){
			System.out.println("IO Exception!");
			System.exit(0);
		}
		return attributes;
	}
	
	/**
	 * This method reads from the sample file (either the training or test file)
	 * and creates an ArrayList of the sample set.
	 * @param sampleFile is the name of the sample file
	 * @param classIndex is the index at which the class attribute can be found
	 * @return the ArrayList of the sample set
	 */
	public ArrayList<Sample> createSampleSet(String sampleFile, int classIndex){
		
		ArrayList<Sample> sampleList = new ArrayList<Sample>();
		
		try{
			
			BufferedReader in = new BufferedReader(new FileReader(sampleFile));
			String line;
			
			while((line = in.readLine())  != null ){
				 if(!line.startsWith("//") && (line.length() != 0)){
					 sampleList.add(new Sample(line, classIndex));
				 }
			}	 
			in.close();
		}
		
		catch(FileNotFoundException e){
			System.out.println("File " + sampleFile + " not found");
			System.exit(0);
		}
		
		catch(IOException e){
			System.out.println("IO Exception!");
			System.exit(0);
		}
		return sampleList;			 
	}
	
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public ArrayList<Sample> getTrainingSamples() {
		return trainingSamples;
	}

	public ArrayList<Sample> getTestSamples() {
		return testSamples;
	}

	public ArrayList<String> getclassValues() {
		return classValues;
	}
}