package customPackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class runs a Kennel
 * @author Lynda Thomas and Chris Loftus and Shankly Cragg
 * @version 1.1 (16th March 2015)
 */
public class KennelDemo {
	private String filename; // holds the name of the file
	private Kennel kennel; // holds the kennel
	private Scanner scan; // so we can read from keyboard
	private int nextFreeID = 1; //To assign an ID to an animal

	// /////////////////////////////////////////////////
	public static void main(String args[]) {
		System.out.println("**********HELLO***********");
		KennelDemo demo = new KennelDemo();
		demo.initialise();
		demo.runMenu();
		demo.printAll();
		demo.save();
		System.out.println("***********GOODBYE**********");
	}
	
	/**
	 * Notice how we can make this private, since we only call from main which
	 * is in this class. We don't want this class to be used by any other class.
	 */
	private KennelDemo() {
		scan = new Scanner(System.in);
		System.out.print("Please enter the filename of kennel information: ");
		filename = scan.next();
		kennel = new Kennel();
	}

	/**
	 * initialise() method runs from the main and reads from a file
	 */
	private void initialise() {
		System.out.println("Using file " + filename);
		
		try(FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		Scanner infile = new Scanner(br)){
			String kennelName = infile.nextLine();
			int kennelSize = infile.nextInt();
			infile.nextLine();
			kennel.setCapacity(kennelSize);
			int numAnimals = infile.nextInt();
			infile.nextLine();
			kennel.setName(kennelName);
			for(int i=0; i < numAnimals; i++){
				String type = infile.nextLine(); //This will read either "dog" or "cat", depending on what it reads the code knows which type of animal object to create
				int ID = infile.nextInt();
				if (ID >= nextFreeID) {
					nextFreeID = ID + 1;
				}
				infile.nextLine();
				String animalName = infile.nextLine();
				int numOwners = infile.nextInt();
				infile.nextLine();
				ArrayList<Owner> owners = new ArrayList<>();
				for(int oCount=0; oCount < numOwners; oCount++){
					String name = infile.nextLine();
					String phone = infile.nextLine();
					Owner owner = new Owner(name, phone);
					owners.add(owner);
				}
				boolean likesBones = infile.nextBoolean(); 
				boolean shareRuns = likesBones;		//This is done because at this point we don't know if this boolean refers too a cat or a dog yet, so we apply it to both
				infile.nextLine();
				int feedsPerDay = infile.nextInt();
				infile.nextLine(); 
				String favFood = infile.nextLine();
				if (type.equals("dog")) {		//Creates a dog
					createDog(ID, animalName, owners, likesBones, favFood, feedsPerDay, infile);
				}
				else if(type.equals("cat")) {	//Creates a cat
					createCat(ID, animalName, owners, shareRuns, favFood, feedsPerDay);
				}
			}
					
		} catch (FileNotFoundException e) {
			System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
		                       " If this is not the first use then have you accidentally deleted the file?");
		} catch (IOException e) {
			System.err.println("An unexpected error occurred when trying to open the file " + filename);
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Creates a dog using information read in from initialise()
	 */
	private void createDog(int ID, String animalName, ArrayList<Owner> owners, boolean likesBones, String favFood, int feedsPerDay, Scanner infile) {
		boolean needsWalks = infile.nextBoolean();
		infile.nextLine();
		Dog dog = new Dog(ID, animalName, owners, likesBones, favFood, feedsPerDay, needsWalks);
		kennel.addAnimal(dog);
	}
	
	/**
	 * Creates a cat using information read in from initialise()
	 */
	private void createCat(int ID, String animalName, ArrayList<Owner> owners, boolean shareRuns, String favFood, int feedsPerDay) {
		Cat cat = new Cat(ID, animalName, owners, shareRuns, favFood, feedsPerDay);
		kennel.addAnimal(cat);
	}
	
	/**
	 * runMenu() method runs from the main and allows entry of data etc
	 */
	private void runMenu() {
		String response;
		do {
			printMenu();
			System.out.println("What would you like to do:");
			scan = new Scanner(System.in);
			response = scan.nextLine().toUpperCase();
			switch (response) {
			case "1":
				admitDog();
				break;
			case "2":
				admitCat();
				break;
			case "3":
				changeKennelName();
				break;
			case "4":			
				printDogsWithBones();
				break;
			case "5":			
				printDogsWhoWalk();
				break;
			case "6":			
				printCatsWhoShare();
				break;
			case "7":
				searchForDog();
				break;
			case "8":
				searchForCat();
				break;
			case "9":
				removeDog();
				break;
			case "10":
				removeCat();
				break;
			case "11":
				setKennelCapacity();
			    break;
			case "12":
				sortAnimals();
			    break;
			case "Q":
				break;
			default:
				System.out.println("Try again");
			}
		} while (!(response.equals("Q")));
	}

	/**
	 * Sets the max capacity of the Kennel
	 */
	private void setKennelCapacity() {
		int max = getInt("Enter max number of animals: ");
		//scan.nextLine();
		kennel.setCapacity(max);
	}

	/**
	 * Calls function which prints all dogs who like bones
	 */
	private void printDogsWithBones() {
		kennel.obtainDogsWhoLikeBones();
	}
	
	/**
	 * Calls function which prints all dogs who needs walks
	 */
	private void printDogsWhoWalk() {
		kennel.obtainDogsWhoNeedWalks();
	}
	
	/**
	 * Calls function which prints all cats who share runs
	 */
	private void printCatsWhoShare() {
		kennel.obtainCatsWhoShareRuns();
	}
	
	/**
	 * printAll() method runs from the main and prints status
	 */
	private void printAll() {
		System.out.println(kennel);
	}

	/**
	 * save() method runs from the main and writes back to file
	 */
	private void save() {
		try(FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter outfile = new PrintWriter(bw);){
			
			outfile.println(kennel.getName());
			outfile.println(kennel.getCapacity());
			outfile.println(kennel.getNumOfAnimals());
			Animal[] animals = kennel.obtainAllAnimals();
			for (Animal a: animals){
				if (a instanceof Dog) {
					outfile.println("dog"); //So when loading we know if the animal is a dog
					outfile.println(a.getID());
					outfile.println(a.getName());
					Owner[] owners = a.getOriginalOwners();
					outfile.println(owners.length);
					for(Owner o: owners){
						outfile.println(o.getName());
						outfile.println(o.getPhone());
					}
					outfile.println(((Dog) a).getLikesBones());
					outfile.println(a.getFeedsPerDay());
					outfile.println(a.getFavouriteFood());
					outfile.println(((Dog) a).getNeedsWalks());
				}
				else if (a instanceof Cat) {
					outfile.println("cat");	//So when loading we know if the animal is a cat
					outfile.println(a.getID());
					outfile.println(a.getName());
					Owner[] owners = a.getOriginalOwners();
					outfile.println(owners.length);
					for(Owner o: owners){
						outfile.println(o.getName());
						outfile.println(o.getPhone());
					}
					outfile.println(((Cat) a).getShareRuns());
					outfile.println(a.getFeedsPerDay());
					outfile.println(a.getFavouriteFood());
				}
			}
		} catch (IOException e) {
			System.err.println("Problem when trying to write to file: " + filename);
		}

	}

	/**
	 * User enters ID of dog to remove, and calls a function to do this
	 */
	private void removeDog() {
		int dogToBeRemoved = getInt("which dog do you want to remove");
		kennel.removeDog(dogToBeRemoved);
	}

	/**
	 * User enters ID of cat to remove, and calls a function to do this
	 */
	private void removeCat() {
		System.out.println("which cat do you want to remove");
		int catToBeRemoved = scan.nextInt();
		kennel.removeCat(catToBeRemoved);
	}
	
	/**
	 * User enters ID of dog to search, and prints the information of it if found
	 */
	private void searchForDog() {
		int ID = getInt("which dog do you want to search for");
		Dog dog = kennel.searchDog(ID);
		if (dog != null){
			System.out.println(dog.toString());
		} else {
			System.out.println("Could not find dog: " + ID);
		}
	}

	/**
	 * User enters ID of cat to search, and prints the information of it if found
	 */
	private void searchForCat() {
		int ID = getInt("which cat do you want to search for");
		Cat cat = kennel.searchCat(ID);
		if (cat != null){
			System.out.println(cat.toString());
		} else {
			System.out.println("Could not find cat: " + ID);
		}
	}
	
	/**
	 * changes the name of the kennel 
	 */
	private void changeKennelName() {
		String name = getStringOfChars("Please enter the name of the Kennel");
		kennel.setName(name);
	}

	/**
	 * Add a dog to the kennels
	 */
	private void admitDog() {
		String name = getStringOfChars("Please enter the name of your dog");
		ArrayList<Owner> owners = getOwners();
		boolean likesBones = getBoolean("Does he/she likes bones?(Y/N)");
		String fav = getStringOfChars("What is his/her favourite food?");
		int numTimes;
		do {
			numTimes = getInt("How many times is he/she fed a day? (as a number)");
		} while (numTimes <= 0);
		boolean needsWalks = getBoolean("Does he/she need walks?(Y/N)");
		int ID = nextFreeID;
		System.out.println("Your dogs ID is: " + ID);
		nextFreeID ++;
		Dog newDog = new Dog(ID, name, owners, likesBones, fav, numTimes, needsWalks);
		kennel.addAnimal(newDog);
	}

	/**
	 * Add a dog to the kennels
	 */
	private void admitCat() {
		String name = getStringOfChars("Please enter the name of your cat");
		ArrayList<Owner> owners = getOwners();
		boolean shareRuns = getBoolean("Does he/she share a run?");
		String fav = getStringOfChars("What is his/her favourite food?");
		int numTimes;
		do {
			numTimes = getInt("How many times is he/she fed a day? (as a number)");
		} while (numTimes <= 0);
		int ID = nextFreeID;
		System.out.println("Your cats ID is: " + ID);
		nextFreeID ++;
		Cat newCat = new Cat(ID, name, owners, shareRuns, fav, numTimes);
		kennel.addAnimal(newCat);
	}

	/**
	 * Get information on the owners of an animal
	 */
	private ArrayList<Owner> getOwners() {
		ArrayList<Owner> owners = new ArrayList<Owner>();
		String answer;
		do {
			System.out.println("Here you will enter the information regarding the owner(s) of the animal");
			String ownName = getStringOfChars("please enter your name");
			String ownPhone = null;
			System.out.println("Please enter the phone number (Must be 4 digits)");
			do {
				ownPhone = scan.nextLine();				 			// Read input from the console and store in ownPhone
				if (!ownPhone.matches("\\d\\d\\d\\d")) {			// Ensures phone number is exactly 4 inches
					System.err.println("Please enter 4 digits");	// Prints an error message
				}
			} while (!ownPhone.matches("\\d\\d\\d\\d"));	 		// While the string "id" does not equal a series of 4 digits, user must input new value for id
			Owner own = new Owner(ownName, ownPhone);	
			owners.add(own);
			System.out.println("Is there another owner (Y/N)?");
			do {
				answer = scan.nextLine().toUpperCase();				// makes the answer upper class to make it easier to do checks with
				if(!answer.matches("[YN]") && !answer.equals("YES") && !answer.equals("NO")) {	// The user is aloud to enter "y", "n", "yes" or "no"
					System.err.println("Please enter 'y' or 'n'");	// If they do not print an error message
				}
			} while (!answer.matches("[YN]") && !answer.equals("YES") && !answer.equals("NO"));		//Loops until user enters correct input
		} while (!answer.equals("N") && !answer.equals("NO"));		// Keeps getting new owners until the user selects "n" or "no"
		return owners;
	}
	
	/**
	 * A method for getting a word containing only letters of the alphabet
	 * @param message A message to be printed asking a question requiring an only alphabet answer
	 * @return String A string of Chars
	 */
	private String getStringOfChars(String message) {
		String string;
		System.out.println(message);
		do {
			string = scan.nextLine();
			if (!string.matches("[a-zA-Z]++")){		//If the string entered doesn't contain only a to z or A to Z, then read input again.  
				System.err.println("Please enter characters only from the alphabet (Spaces are not permitted)");
			}
 		} while (!string.matches("[a-zA-Z]++"));	//Keeps getting input until it is of the correct form
		return string;
	}
	
	/**
	 * A method for getting an int
	 * @param message A message to be printed asking a question requiring an int
	 * @return result An int
	 */
	private int getInt(String message) {
		boolean correct = false;
		int result = 0;
		do {
			System.out.println(message);
			try {
				result = scan.nextInt();
				scan.nextLine();
				correct = true;
			} catch (InputMismatchException ime) {
				System.err.println("Please enter a number");
				scan.nextLine();
			}
		} while (!correct);
		return result;
	}

	/**
	 * A method for getting a boolean value, in which the user may have multiple allowed inputs
	 * @param message A message to be printed asking for a yes no answer
	 * @return bool A boolean
	 */
	private boolean getBoolean(String message) {
		String letter;
		boolean bool = false;
		System.out.println(message);
		do {
			letter = scan.nextLine().toUpperCase();
			if (letter.equals("Y") || letter.equals("YES")) {
				bool = true;
			}
			if(!letter.matches("[YN]") && !letter.equals("YES") && !letter.equals("NO")) {
				System.err.println("Please enter \"y\" or \"n\"");
			}
		} while (!letter.matches("[YN]") && !letter.equals("YES") && !letter.equals("NO"));
		return bool;
	}
	
	/**
	 * Calls the method which sorts the animals
	 */
	private void sortAnimals() {
		kennel.sortAnimals();
	}
	
	private void printMenu() {
		System.out.println("1 -  add a new Dog");
		System.out.println("2 -  add a new Cat");
		System.out.println("3 -  set up Kennel name");
		System.out.println("4 -  print all dogs who like bones");
		System.out.println("5 -  print all dogs who need walks");
		System.out.println("6 -  print all cats who share runs");
		System.out.println("7 -  search for a dog");
		System.out.println("8 -  search for a cat");
		System.out.println("9 -  remove a dog");
		System.out.println("10 -  remove a cat");
		System.out.println("11 -  set kennel capacity");
		System.out.println("12 -  Print all Animals");
		System.out.println("q - Quit");
	}

}
