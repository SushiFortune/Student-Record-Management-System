import java.io.*;
import java.util.Scanner;

/**
 * CleverSIDCDriver is the main driver class for the NASTA CleverSIDC project.
 * It allows users to interact with the CleverSIDC data structure by providing a command-line interface for various operations.
 * The driver prompts users to set the number of students to track, reads data from a file, and performs operations based on user choices.
 * @author Rania Maoukout(40249281) & Barbara Eguche(40245327)
 */
public class CleverSIDCDriver {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
            //variables
            int counter = 0, sizeOfStudents; String fileName; Scanner myReader; long studentID; String studentName;

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~ Welcome to Rania and Barbara's NASTA CleverSIDC Project ~~~~~~~~~~~~~~~~~~~~~~~~");
            CleverSIDC cleverSIDC = new CleverSIDC();

            //prompt the user to set number of students to keep a track of
            System.out.print("\n-> Enter the number of students would you like us to track: ");
            sizeOfStudents = scan.nextInt();

            //set the cleverSIDC threshold
            cleverSIDC.setSIDCThreshold(sizeOfStudents);

            //read the file to get the key, generate values for each key
            try {
                System.out.print("-> Please enter the name of the file you would like to read from (add .txt extension): ");
                fileName = scan.next();

                myReader = new Scanner(new File("test_files/"+fileName));
                while(myReader.hasNextLine() && counter != sizeOfStudents) {
                    studentID = myReader.nextLong();
                    studentName = cleverSIDC.generateValues();

                    cleverSIDC.add(cleverSIDC, studentID, studentName);
                    counter++;
                }
            } catch (FileNotFoundException exception) {
                System.out.print("File does not exist. Exiting...");
                System.exit(1);
            }

            //initial display of NASTA CleverSIDC
            cleverSIDC.printCircularArray();
            while(true) {
                System.out.println("""
                    \n------------------------------------------------------------------------------------------------------------
                    Tell me what you want to do? Let's chat since this project is in demand! Here are the option:
                    \t\t1) Ask me to randomly generate a new non-existing key of 8 digits;
                    \t\t2) Ask me to return all keys in CleverSIDC as a sorted sequence;
                    \t\t3) Ask me to add an entry for a given key and value;
                    \t\t4) Ask me to remove an entry for a given key;
                    \t\t5) Ask me to return the value of a given key;
                    \t\t6) Ask me to return the key for the successor of a given key;
                    \t\t7) Ask me to return the key for the predecessor of a given key;
                    \t\t8) Ask me to return the number of keys that are within a specified range of two keys;
                    \t\t9) Ask me to exit NASTA CleverSIDC
                    """);

                System.out.print("Enter your selection: ");
                String userChoice = scan.next();

                // ------------------------------------------------------------------------------------------------------------------------------ //
                if(userChoice.equals("1")) {
                    studentID = cleverSIDC.generate();
                    studentName = cleverSIDC.generateValues();

                    System.out.println("Do you want to (a). add the generated key to NASTA CleverSIDC or (b). print the key?");
                    System.out.print("Type A - add, P - print: ");
                    char addOrPrint = scan.next().charAt(0);

                    if(addOrPrint == 'A') {
                        cleverSIDC.add(cleverSIDC, studentID, studentName);
                    } else if(addOrPrint == 'P') {
                        System.out.printf("The generated 8 digit key is: %d%n", studentID);
                    } else {
                        System.out.println("Not a valid response, continuing..");
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("2")) {
                    System.out.println("~~~ Displaying all the keys in a sorted sequence, along with their corresponding values; ~~~");
                    cleverSIDC.allKeys(cleverSIDC);
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("3")) {
                    System.out.print("~~~ Enter the key you would like to add to NASTA CleverSIDC: ");
                    studentID = scan.nextLong();
                    scan.nextLine();  //consume the newline character
                    System.out.print("~~~ Enter the value you would like to add to NASTA CleverSIDC: ");
                    studentName = scan.nextLine();

                    cleverSIDC.add(cleverSIDC, studentID, studentName);
                    System.out.println("Key(Student-ID) and Value(Student Name) have been added to NASTA CleverSIDC.");
                    cleverSIDC.printCircularArray();
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("4")) {
                    System.out.print("~~~ Enter the key you would like to remove from NASTA CleverSIDC: ");
                    studentID = scan.nextLong();

                    long removedKey = cleverSIDC.remove(cleverSIDC, studentID);
                    if(removedKey == -1 || removedKey == 0) {
                        System.out.println("Key does not exist in NASTA CleverSIDC.");
                    } else {
                        System.out.printf("Successfully removed the key: %d from NASTA CleverSIDC.%n", removedKey);
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("5")) {
                    System.out.print("~~~ Enter the key you would like to get the value of: ");
                    studentID = scan.nextLong();

                    String keyValue = cleverSIDC.getValues(cleverSIDC, studentID);
                    if(keyValue.isEmpty()) {
                        System.out.println("Key doesn't exist in NASTA CleverSIDC, therefore has no value.");
                    } else {
                        System.out.printf("The value of the given key is: %s%n", keyValue);
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("6")) {
                    System.out.print("~~~ Enter the key you would like to get the successor of: ");
                    studentID = scan.nextLong();

                    long successorKey = cleverSIDC.nextKey(cleverSIDC, studentID);
                    if(successorKey != 0) {
                        System.out.printf("The successor of the given key is: %d%n", successorKey);
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("7")) {
                    System.out.print("~~~ Enter the key you would like to get the predecessor of: ");
                    studentID = scan.nextLong();

                    long predecessorKey = cleverSIDC.prevKey(cleverSIDC, studentID);
                    if(predecessorKey != 0) {
                        System.out.printf("The predecessor of the given key is: %d%n", predecessorKey);
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("8")) {
                    System.out.print("~~~ Enter the first key for the starting range(exclusive): ");
                    long key1 = scan.nextLong();
                    System.out.print("~~~ Enter the the second key for the ending range(exclusive): ");
                    long key2 = scan.nextLong();

                    int rangeOfKeys = cleverSIDC.rangeKey(key1, key2);
                    if(rangeOfKeys != -1) {
                        System.out.printf("The number of elements between key1: %d, and key2: %d is: %d.%n", key1, key2, rangeOfKeys);
                    }
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else if(userChoice.equals("9")) {
                    System.out.print("Exiting NASTA CleverSIDC... See you later :)");
                    break;
                }

                // ------------------------------------------------------------------------------------------------------------------------------ //
                else {
                    System.out.println("Sorry that is an invalid choice. Try again.");
                }
            }
        scan.close();
    }
}
