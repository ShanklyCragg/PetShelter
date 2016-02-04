package customPackage;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.InputMismatchException;

/**
 * 
 * To model a Kennel - a collection of Animals
 * 
 * @author Chris Loftus and Shankly Cragg
 * @version 1.0 (16th March 2015)
 *
 */
public class Kennel {
	private String name;
	private ArrayList<Animal> animals;
	private int nextFreeLocation;
	private int capacity;

	/**
	 * Creates a kennel with a default size 20
	 * 
	 * @param maxNoDogs
	 *            The capacity of the kennel
	 */
	public Kennel(){
		this(20);
	}
	
	/**
	 * Create a kennel
	 * 
	 * @param maxNoDogs
	 *            The capacity of the kennel
	 */
	public Kennel(int maxNoDogs) {
		nextFreeLocation = 0; // no Dogs in collection at start
		capacity = maxNoDogs;
		animals = new ArrayList<Animal>(capacity); // set up default. This can
												// actually be exceeded
												// when using ArrayList but we
												// won't allow that
												// to happen.
	}

	/**
	 * This method sets the value for the name attribute. The purpose of the
	 * attribute is: The name of the kennel e.g. "DogsRUs"
	 * 
	 * @param theName
	 */
	public void setName(String theName) {
		name = theName;
		System.out.println("Name of Kennel is " + name);
	}
	
	/**
	 * Set the size of the kennel
	 * @param capacity The max dogs we can house
	 */
	public void setCapacity(int capacity){
		if (capacity >= nextFreeLocation){	//Ensures we don't make the kennel smaller than the number of animals in it
			this.capacity = capacity;
			System.out.println("Capacity of Kennel is " + capacity);
		}
		else {
			System.out.println("The kennal would be too small! The change has not been made.");
		}
	}
	
	/**
	 * Maximum capacity of the kennels
	 * @return The max size of the kennel
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * This method gets the value for the name attribute. The purpose of the
	 * attribute is: The name of the Kennel e.g. "DogsRUs"
	 * 
	 * @return String The name of the kennel
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the number of dogs in a kennel
	 * 
	 * @return int Current number of dogs in the kennel
	 */
	public int getNumOfAnimals() {
		return nextFreeLocation;
	}

	/**
	 * Enables a user to add a Animal to the Kennel
	 * 
	 * @param theAnimal new Animal to home
	 */
	public void addAnimal(Animal theAnimal) {
		if (nextFreeLocation >= capacity) {		//Checks if there is space in the Kennel
			System.out.println("Sorry kennel is full - cannot add team");
			return;
		}
		// we add in the position indexed by nextFreeLocation
		// This starts at zero
		animals.add(theAnimal);

		// now increment index ready for next one
		nextFreeLocation ++;
	}

	/**
	 * Enables a user to delete a Dog from the Kennel.
	 * 
	 * @param theDog
	 * The dog to remove
	 */
	public void removeDog(int who) {
		if (animals.size() != 0) { //Only runs if there may be a dog in the Kennel
			Dog which = null;
			for (Animal d : animals) {
				if(d instanceof Dog) {
					if (who == d.getID()) {
						which = (Dog) d;
					}
				}
			}
			if (which != null) {
				animals.remove(which); // Requires that Dog has an equals method
				System.out.println("removed " + who);
				nextFreeLocation = nextFreeLocation - 1;
			} else
				System.err.println("cannot remove - not in kennel");
			}
		else {
			System.out.print("There are no animals to remove!");
		}
	}

	/**
	 * Enables a user to delete a Cat from the Kennel.
	 * 
	 * @param theCat
	 * The cat to remove
	 */
	public void removeCat(int who) {
		if (animals.size() != 0) {	//Only runs if there may be a cat in the Kennel
			Cat which = null;
			for (Animal c : animals) {
				if(c instanceof Cat) {
					if (who == c.getID()) {
						which = (Cat) c;
					}
				}
			}
			if (which != null) {
				animals.remove(which); // Requires that Cat has an equals method
				System.out.println("removed " + who);
				nextFreeLocation = nextFreeLocation - 1;
			} else
				System.err.println("cannot remove - not in kennel");
		}
		else {
			System.out.print("There are no animals to remove!");
		}
	}
	
	/**
	 * @return String showing all the information in the kennel
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append("Data in Kennel ").append(name).append(System.lineSeparator());
		for (Animal a : animals) {
			sb.append(a.toString()).append(System.lineSeparator());
		}
		return sb.toString();
	}

	/**
	 * Returns an array of the animals in the kennels
	 * @return An array of the correct size
	 */
	public Animal[] obtainAllAnimals() {
		Animal[] result = new Animal[animals.size()];
		result = animals.toArray(result);
		return result;
	}

	/**
	 * Prints dogs who like bones
	 */
	public void obtainDogsWhoLikeBones() {
		if(animals.size() != 0) {
			for (Animal i : animals) {
				if (i instanceof Dog) {
					if (((Dog) i).getLikesBones() == true) {
						System.out.println(i.toString());
					}
				}
			}
		}
	 	else {
	 		System.out.println("No dogs to print!");
	 	}
	}
	
	/**
	 * Prints dogs who need walks
	 */
	public void obtainDogsWhoNeedWalks() {
		if(animals.size() != 0) {
			for (Animal i : animals) {
				if (i instanceof Dog) {
					if (((Dog) i).getNeedsWalks() == true) {
						System.out.println(i.toString());
					}
				}
			}
		}
	 	else {
	 		System.out.println("No dogs to print!");
	 	}
	}
	
	/**
	 * Prints cats who share runs
	 */
	public void obtainCatsWhoShareRuns() {
		if(animals.size() != 0) {
			for (Animal i : animals) {
				if (i instanceof Cat) {
					if (((Cat) i).getShareRuns() == true) {
						System.out.println(i.toString());
					}
				}
			}
		}
	 	else {
	 		System.out.println("No cats to print!");
	 	}
	}

	/**
	 * Prints the dog with an ID that matches the one entered
	 */
	public Dog searchDog(int ID) {
	 	if(animals.size() != 0) {
	 		for (Animal i : animals) {
				if (ID == i.getID()) {
					if(i instanceof Dog) {
							Dog result = (Dog) i;
							return result;
					}
				}
	 		}
	 	}	
	 	else {
	 		System.out.println("No animals to search!");
	 	}
	 	Dog result = null;
	 	return result;
	}
	
	/**
	 * Prints the cat with an ID that matches the one entered
	 */
	public Cat searchCat(int ID) {
	 	if(animals.size() != 0) {
	 		for (Animal i : animals) {
				if (ID == i.getID()) {
					if(i instanceof Cat) {
							Cat result = (Cat) i;
							return result;
					}
				}
	 		}
	 	}	
	 	else {
	 		System.out.println("No animals to search!");
	 	}
	 	Cat result = null;
	 	return result;
	}
	
	/**
	 * Prints the Animals in alphabetical order
	 */
	public void sortAnimals() {
	 	if(animals.size() != 0) {
	 		Collections.sort(animals, Animal.AnimalNameComparator);
	 		for(Animal i : animals) {
	 			System.out.println(i);
	 		}
	 	}
	 	else {
	 		System.out.println("No animals to print!");
	 	}
	}
}