/**
 * CS6735 Intro to Machine Learning
 * Programming Assignment:
 * 
 * @author nathanieldespres
 * Date: December 2017
 * 
 * ProgAssign is the main class of the Programming Assignment
 * project. It takes in the file names for the dataset through
 * the command line, and then does ID3, Random Forest and KNN
 * on the data set
 */

public class ProgAssign{
	
	public static void main(String[] args){
		
		/**
		 * Check to make certain that the correct number of
		 * arguments(file names) are entered in the command
		 * line.
		 */
		if(args.length != 3){
			System.out.println("Incorrect number of files entered" +
							" in command line.");
			System.out.println("Format required: x_names.data " +
					"x_training.data x_test.data");
			System.exit(0);
		}
		
		ProgAssignFileReader fileReader = new ProgAssignFileReader();
		fileReader.read(args[0], args[1], args[2]);
		
		NearestNeighbor kNN = new NearestNeighbor(fileReader.getAttributes(), fileReader.getTrainingSamples(),
													fileReader.getTestSamples(), fileReader.getclassValues());
		for(int k = 2; k <= 20; k++){
			kNN.run(k);
			System.out.println("Accuracy of " + k + "-Nearest Neighbor: " + kNN.getAccuracy());
			System.out.println("");
		}
	}
}