package customPackage;
import java.util.ArrayList;

/**
 * To support an individual cat
 * @author Shankly Cragg
 * @version 1.0 (16th March 2015)
 */
public class Cat extends Animal {
	
	private boolean shareRuns;

	/**
	 * Constructor for the cat
	 * @param ID The Cat's ID
	 * @param newName The cat's name
	 * @param owners A list of original owners: a copy is made
	 * @param shareRuns Does the cat share a run with other cats?
	 * @param food The kind of food it eats
	 * @param mealsPerDay Number of feeds per day 
	 */
	public Cat(int ID, String newName, ArrayList<Owner> owners, boolean shareRuns, String food, int mealsPerDay) {
		super (ID, newName, owners, food, mealsPerDay);
		this.shareRuns = shareRuns;
	}

	/**
	 * Does the cat share a run with other cats?
	 * @return true if he/she does
	 */
	public boolean getShareRuns() {
		return shareRuns;
	}

	/**
	 * A basic implementation to just return all the data in string form
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append("Type: Cat").append(System.lineSeparator());
		sb.append(super.toString());
		sb.append("Shares runs: ").append(shareRuns).append(System.lineSeparator());
		return sb.toString();
	}

	@Override
	public int compareTo(Animal o) {		//This is here to allow the Animal class to implement "Comparable".
		// TODO Auto-generated method stub
		return 0;
	}
}
