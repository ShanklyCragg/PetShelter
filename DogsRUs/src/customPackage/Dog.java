package customPackage;
import java.util.ArrayList;

/**
 * To support an individual dog
 * @author Chris Loftus and Shankly Cragg
 * @version 1.0 (16th March 2015)
 */
public class Dog extends Animal {

	private boolean likesBones;
	private boolean needsWalks;

	/**
	 * Constructor for the dog
	 * @param name The dog's name
	 * @param owners A list of original owners: a copy is made
	 * @param likeBones Does the dog like bones?
	 * @param food The kind of food it eats
	 * @param mealsPerDay Number of feeds per day 
	 * @param needsWalks Does the dog need walks?
	 */
	public Dog(int ID, String newName, ArrayList<Owner> owners, boolean likesBones, String food, int mealsPerDay, boolean needsWalks) {
		super (ID, newName, owners, food, mealsPerDay);
		this.likesBones = likesBones;
		this.needsWalks = needsWalks;
	}

	/**
	 * Does the dog like bones?
	 * @return true if he/she does
	 */
	public boolean getLikesBones() {
		return likesBones;
	}
	
	/**
	 * Does the dog need walks?
	 * @return true if he/she does
	 */
	public boolean getNeedsWalks() {
		return needsWalks;
	}

	/**
	 * A basic implementation to just return all the data in string form
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append("Type: Dog").append(System.lineSeparator());
		sb.append(super.toString());
		sb.append("Likes bones: ").append(likesBones).append(System.lineSeparator());
		sb.append("Needs walks: ").append(needsWalks).append(System.lineSeparator());
		return sb.toString();
	}

	@Override
	public int compareTo(Animal arg0) {		//This is here to allow the Animal class to implement "Comparable".
		// TODO Auto-generated method stub
		return 0;
	}
}
