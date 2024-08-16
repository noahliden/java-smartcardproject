// Noah Liden

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class SystemInterface {

    static SmartCard[] smartcards = new SmartCard[10];
    static int cardCount = 0;

    // Add card method checks if a card with the same ID already exists and if the maximum limit of 3 cards has been reached.
    // If the card does not exist and the limit has not been reached, a new SmartCard object is created and added to the system.
    public void addCard(int cardID, char type, float balance) throws Exception {

        Random rand = new Random();
        for (int i = 0; i < cardCount; i++) {
        if (smartcards[i].getCardID() == cardID) {
            cardID = rand.nextInt(10000); // Generate a random ID between 0 and 9999
            System.out.println("A card with this ID already exists. Assigning a random ID: " + cardID);
            i = -1; // Reset the loop to check the new ID
            continue;
        }
    }

        if (cardCount >= 10) {
            throw new Exception("Cannot add more cards. Maximum limit reached.");
        }

        SmartCard newCard = new SmartCard(cardID, type, balance);
        smartcards[cardCount] = newCard;
        cardCount++;
        System.out.println("New Smartcard created.");
    }

    // Add journey method checks if a SmartCard exists and adds a journey to the smartCard.
    // The method also checks if the journey ID already exists.
    public void addJourney(Scanner scanner, int journeyID, String transportMode, int startOfJourney, int endOfJourney,
        int distanceOfJourney) {

    if (cardCount == 0) {
        System.out.println("No SmartCards have been created yet.");
        return;
    }

    Journey journey = new Journey(journeyID, transportMode, startOfJourney, endOfJourney, distanceOfJourney);

    // Add the journey to the last SmartCard that was added
    SmartCard currentCard = smartcards[cardCount - 1];
    currentCard.addJourney(journey);

    System.out.println("Journey added to the current SmartCard.");
    }

    // Journey method checks if a SmartCard exists and returns the journey with the given journey ID.
    // Used within the SystemInterface class to print the details of a journey.
    public Journey getJourney(int cardID, int journeyID) {
        for (SmartCard card : smartcards) {
            if (card != null && card.getCardID() == cardID) {
                return card.getJourney(journeyID);
            }
        }
        return null;
    }

    // Method printExistingCards prints the details of all SmartCards in the system.
    // This is used within the SystemInterface class to display the details of all SmartCards.
    public void printExistingCards() {
        boolean hasCards = false;
        for (SmartCard card : smartcards) {
            if (card != null) {
                hasCards = true;
                System.out.println("SmartCard " + card.getCardID() + " has type " + card.getType() + " and "
                        + card.getNumberOfJourneys() + " journey(s).");
                printJourneys(card);
            }
        }
        if (!hasCards) {
            System.out.println("");
            System.out.println("No SmartCards exist. Please create a SmartCard first.");
        }
    }

    // Method printJourneys prints the details of all journeys on a SmartCard. 
    //This is used within the SystemInterface class to display the details of all journeys on a given SmartCard.
    private void printJourneys(SmartCard smartcard) {
        Journey[] journeys = smartcard.getJourneys();
        for (Journey journey : journeys) {
            if (journey != null) {
                System.out.println(
                        "        Journey " + journey.getJourneyID() + " has transport mode " + journey.getTransportMode());
            }
        }
    }

    // Method cardExists checks if a SmartCard with the given card ID exists in the system.
    public boolean cardExists(int cardID) {
        for (SmartCard card : smartcards) {
            if (card != null && card.getCardID() == cardID) {
                return true;
            }
        }
        return false;
    }

    // Method canAddCard checks if a new SmartCard can be added to the system. Done by checking if there is space for a new SmartCard.
    public boolean canAddCard() {
        for (SmartCard card : smartcards) {
            if (card == null) {
                return true;
            }
        }
        return false;
    }

    // Method canAddJourney checks if a journey can be added to the system. Done by checking if the first SmartCard exists.
    public boolean canAddJourney() {
        for (SmartCard card : smartcards) {
            if (card != null) {
                return true;
            }
        }
        return false;
    }

    // Method deleteCard deletes a SmartCard with the given card ID.
    public void deleteCard(int deleteCardID) {
        for (int i = 0; i < smartcards.length; i++) {
            if (smartcards[i] != null && smartcards[i].getCardID() == deleteCardID) {
                smartcards[i] = null;
                System.out.println("SmartCard " + deleteCardID + " has been deleted.");
                return;
            }
        }
        System.out.println("No SmartCard found with the given ID.");
    }

    // Method removeJourney removes a journey with the given journey ID from a SmartCard with the given card ID.
    public void removeJourney(int cardID, int journeyID) {
        boolean journeyRemoved = false;
        for (SmartCard card : smartcards) {
            if (card != null && card.getCardID() == cardID) {
                journeyRemoved = card.removeJourney(journeyID);
                if (journeyRemoved) {
                    System.out.println("Journey with ID " + journeyID + " has been removed from SmartCard with ID "
                            + cardID + ".");
                    return;
                }
            }
        }
        if (!journeyRemoved) {
            System.out.println("No journey with ID " + journeyID + " exists on SmartCard with ID " + cardID + ".");
        }
    }

    // Method listJourneys lists all journeys on a SmartCard with the given card ID.
    public void listJourneys(int cardID) {
        for (SmartCard card : smartcards) {
            if (card != null && card.getCardID() == cardID) {
                card.listJourneys();
                return;
            }
        }
        System.out.println("No SmartCard with ID " + cardID + " exists.");
    }

    // Method listJourneysByTransportMode lists all journeys with the given transport mode.
    public void listJourneysByTransportMode(String transportMode) {
        boolean found = false;
        for (SmartCard card : smartcards) {
            if (card != null) {
                Journey[] journeys = card.getJourneys();
                for (Journey journey : journeys) {
                    if (journey != null && journey.getTransportMode().equals(transportMode)) {
                        System.out.println("Journey with ID " + journey.getJourneyID() + " on SmartCard with ID " 
                        + card.getCardID() + " has transport mode " + transportMode + ".");
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            System.out.println("No journeys with transport mode " + transportMode + " exist.");
        }
    }

    // Method journeyExists checks if a journey with the given journey ID exists in the system.
    public boolean journeyExists(int journeyID) {
        for (SmartCard card : smartcards) {
            if (card != null && card.journeyExists(journeyID)) {
                return true;
            }
        }
        return false;
    }

    // Method printFareSummary prints the total cost of all journeys based on transportmode and the breakdown of costs by SmartCard.
    public void printFareSummary() {
        System.out.println("Total transport mode journeys cost/fare:");
        System.out.println("---------------------------------------------------------");
        String[] modes = {"Train", "Bus", "Tram"};
        for (String mode : modes) {
            float totalCost = 0;
            for (SmartCard card : smartcards) {
                if (card != null) {
                    for (Journey journey : card.getJourneys()) {
                        if (journey != null && journey.getTransportMode().equals(mode)) {
                            float cost = calculateCost(journey, card);
                            totalCost += cost;
                        }
                    }
                }
            }
            System.out.println("Total cost of " + mode + " journeys is $" + totalCost);
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Breakdown by smartcard:");
        System.out.println("---------------------------------------------------------");
        for (SmartCard card : smartcards) {
            if (card != null) {
                System.out.println("SmartCard " + card.getCardID() + ":");
                for (String mode : modes) {
                    float totalCost = 0;
                    for (Journey journey : card.getJourneys()) {
                        if (journey != null && journey.getTransportMode().equals(mode)) {
                            float cost = calculateCost(journey, card);
                            totalCost += cost;
                        }
                    }
                    System.out.println("Total cost of " + mode + " journeys is $" + totalCost);
                }
            }
        }
    }
    
    private float calculateCost(Journey journey, SmartCard card) {
        float cost = 0;
        switch (card.getType()) {
            case 'S':
            case 's':
                cost = 1.5f + 1.60f * journey.getDistanceOfJourney();
                break;
            case 'C':
            case 'c':
                cost = 1.5f + 1.86f * journey.getDistanceOfJourney();
                break;
            case 'A':
            case 'a':
                cost = 1.5f + 2.24f * journey.getDistanceOfJourney();
                break;
        }
        return cost;
    }

    // Method hasDuplicateJourney checks if a journey with the same transport mode, start and end points already exists on a SmartCard.
    public boolean hasDuplicateJourney(SmartCard card, Journey journey) {
        Journey[] journeys = card.getJourneys();
        for (Journey existingJourney : journeys) {
            if (existingJourney != null && existingJourney.getTransportMode().equals(journey.getTransportMode())
                    && existingJourney.getStartOfJourney() == journey.getStartOfJourney()
                    && existingJourney.getEndOfJourney() == journey.getEndOfJourney()) {
                return true;
            }
        }
        return false;
    }

    // Method writeToFile writes the details of all SmartCards and their journeys to a file.
    public void writeToFile(String filename) {
    try {
        PrintWriter writer = new PrintWriter(filename);

        for (SmartCard smartcard : smartcards) {
            if (smartcard != null) {
                writer.println("SmartCard");
                writer.println("ID " + smartcard.getCardID());
                writer.println("Type " + smartcard.getType());
                writer.println("Balance " + smartcard.getBalance());
                writer.println();
                writer.println("Journeys");
                for (Journey journey : smartcard.getJourneys()) {
                    if (journey != null) {
                        writer.println();
                        writer.println("ID " + journey.getJourneyID());
                        writer.println("Mode " + journey.getTransportMode());
                        writer.println("Start " + journey.getStartOfJourney());
                        writer.println("End " + journey.getEndOfJourney());
                        writer.println("Distance " + journey.getDistanceOfJourney());
                    }
                }
            }
        }

        writer.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
}







    public static void main(String[] args) throws Exception {


        SystemInterface systemInterface = new SystemInterface();
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("");
            System.out.println("Menu:");
            System.out.println("");
            System.out.println("1. Create a new Smartcard and Journey(s)");
            System.out.println("2. Delete a Smartcard");
            System.out.println("3. Delete a journey");
            System.out.println("4. List Smartcards");
            System.out.println("5. List journeys on a Smartcard");
            System.out.println("6. List journeys by transport mode");
            System.out.println("7. View summary of total cost/fare");
            System.out.println("8. Input from file");
            System.out.println("9. Output to file");
            System.out.println("10. Exit");
            System.out.println("");
            System.out.print("Enter your choice: ");
            System.out.println("");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    if (cardCount >= 10) {
                        System.out.println("Cannot add more cards. Maximum limit reached.");
                        break;
                    }
                    System.out.println("");
                    System.out.println("Creating a new Smartcard...");
                    System.out.println("");

                    System.out.print("Enter card ID: ");
                    int cardID = scanner.nextInt();
                    while (systemInterface.cardExists(cardID)) {
                        System.out.println("A card with this ID already exists. Generating a new ID...");
                        cardID = new Random().nextInt(1000); // Generate a random ID between 0 and 999
                        System.out.println("New ID: " + cardID);
                    }
                    System.out.print("Enter card type (A for adult, C for child or S for Senior): ");
                    char type = scanner.next().charAt(0);
                    while (type != 'A' && type != 'C' && type != 'S' && type != 'a' && type != 'c' && type != 's') {
                        System.out.println("Invalid type. Please enter A, C or S.");
                        type = scanner.next().charAt(0);
                    }
                    int maxJourneys = 0;
                    switch (type) {
                        case 'C':
                        case 'c':
                            maxJourneys = 1;
                            break;
                        case 'A':
                        case 'a':
                            maxJourneys = 2;
                            break;
                        case 'S':
                        case 's':
                            maxJourneys = 3;
                            break;
                    }

                    // User inputs balance for the SmartCard and checks if it is greater than $5, otherwise asks for a valid amount.
                    System.out.print("Enter balance: ");
                    float balance = scanner.nextFloat();
                    while (balance < 5) {
                        System.out.println("Minimum balance is $5. Please enter a valid amount.");
                        balance = scanner.nextFloat();
                    }

                    for (int i = 0; i < maxJourneys; i++) {
                        System.out.print("Enter journey ID for journey " + (i + 1) + ": ");
                                                
                        
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter an integer.");
                            scanner.next(); // Discard the invalid input
                        }
                        int journeyID = scanner.nextInt();
                        
                        if (systemInterface.journeyExists(journeyID)) {
                            System.out.println("A journey with this ID already exists. Generating a new ID...");
                            journeyID = new Random().nextInt(1000); // Generate a random ID between 0 and 999
                            System.out.println("New ID: " + journeyID);
                        }

                        scanner.nextLine();
                        System.out.print("Enter the transport mode of your journey -'train', 'bus', or 'tram': ");
                        String transportMode = scanner.nextLine().toLowerCase();

                        while (!transportMode.equals("train") && !transportMode.equals("bus")
                                && !transportMode.equals("tram")) {
                            System.out.print("Invalid input. Please enter 'train', 'bus', or 'tram'.");
                            transportMode = scanner.nextLine().toLowerCase();
                        }

                        int startOfJourney, endOfJourney;
                        Journey journey;
                        int distanceOfJourney;

                        
                            System.out.print("Enter the start of your journey: ");
                            startOfJourney = scanner.nextInt();
                            while (startOfJourney < 1 || startOfJourney > 10) {
                                System.out.println("Invalid input. Please enter a number between 1 and 10.");
                                startOfJourney = scanner.nextInt();
                            }

                            System.out.print("Enter the end of your journey: ");
                            endOfJourney = scanner.nextInt();
                            while (endOfJourney < 1 || endOfJourney > 10 || endOfJourney == startOfJourney) {
                                System.out.println(
                                        "Invalid input. Please enter a number between 1 and 10 that is different from the starting point.");
                                endOfJourney = scanner.nextInt();
                            }
                            
                            distanceOfJourney = endOfJourney - startOfJourney;
                            if (distanceOfJourney < 0) {
                                distanceOfJourney = -distanceOfJourney;
                            }

                            journey = new Journey(journeyID, transportMode, startOfJourney, endOfJourney, distanceOfJourney);

                            // Create a new SmartCard only if it doesn't exist yet
                            if (smartcards[cardCount] == null) {
                                smartcards[cardCount] = new SmartCard(cardID, type, balance);
                            }

                            // Check for duplicate journeys
                            if (!systemInterface.hasDuplicateJourney(smartcards[cardCount], journey)) {
                                smartcards[cardCount].addJourney(journey);
                            } else {
                                System.out.println("This journey already exists on the smartcard. Please enter a different combination.");
                                i = i - 1;
                            }
                    }
                    cardCount++;
                    break;

                case 2: // Call method to delete a Smartcard
                    System.out.println("");
                    System.out.println("Deleting a Smartcard...");
                    System.out.println("");
                    System.out.print("Enter card ID: ");
                    int deleteCardID = scanner.nextInt();
                    systemInterface.deleteCard(deleteCardID);
                    break;


                case 3: // Call method to delete a journey
                    System.out.println("");
                    System.out.println("Deleting a journey...");
                    System.out.println("");
                    System.out.print("Enter card ID: ");
                    int cardIDForJourneyRemoval = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter journey ID: ");
                    int journeyIDForRemoval = scanner.nextInt();
                    scanner.nextLine();
                    systemInterface.removeJourney(cardIDForJourneyRemoval, journeyIDForRemoval);
                    break;


                case 4: // Call method to list Smartcards
                    systemInterface.printExistingCards();
                    break;


                case 5: // Call method to list journeys on a Smartcard
                    System.out.println("");
                    System.out.print("Enter card ID: ");
                    int cardIDForJourneyListing = scanner.nextInt();
                    systemInterface.listJourneys(cardIDForJourneyListing);
                    break;


                case 6: // Call method to list journeys by transport mode

                    System.out.println("");
                    scanner.nextLine();
                    System.out.print("Enter transport mode: ");
                    String transportModeJourney = scanner.nextLine().toLowerCase();
                    while (!transportModeJourney.equals("train") && !transportModeJourney.equals("bus")
                            && !transportModeJourney.equals("tram")) {
                        System.out.print("Invalid input. Please enter 'train', 'bus', or 'tram': ");
                        transportModeJourney = scanner.nextLine().toLowerCase();
                    }
                    systemInterface.listJourneysByTransportMode(transportModeJourney);
                    break;

                case 7: // Call method to view summary of total cost/fare
                    System.out.println("");
                    systemInterface.printFareSummary();
                    break;

                case 8: // Call method to input from file and create Smartcards and Journeys
                    System.out.println("");
                    System.out.println("Reading from file...");
                    System.out.println("");
                    System.out.println("Enter the name of the file: ");
                    scanner.nextLine(); // Consume newline left-over
                    String filename = scanner.nextLine();
                    try {
                        File file = new File(filename);
                        Scanner fileScanner = new Scanner(file);
                        String state = "";
                        int cardIDFromFile = -1;
                        char typeFromFile = ' ';
                        float balanceFromFile = -1;
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            if (line.equals("SmartCard")) {
                                state = "SmartCard";
                            } else if (line.equals("Journeys")) {
                                state = "Journeys";
                            } else if (state.equals("SmartCard")) {
                                String[] parts = line.split(" ");
                                if (parts[0].equals("ID")) {
                                    cardIDFromFile = Integer.parseInt(parts[1]);
                                } else if (parts[0].equals("Type")) {
                                    typeFromFile = parts[1].charAt(0);
                                } else if (parts[0].equals("Balance")) {
                                    balanceFromFile = Float.parseFloat(parts[1]);
                                    try {
                                        systemInterface.addCard(cardIDFromFile, typeFromFile, balanceFromFile);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } else if (state.equals("Journeys")) {
                                String[] parts = line.split(" ");
                                if (parts[0].equals("ID")) {
                                    int journeyIDFromFile = Integer.parseInt(parts[1]);
                                    String transportModeFromFile = fileScanner.nextLine().split(" ")[1];
                                    int startOfJourneyFromFile = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
                                    int endOfJourneyFromFile = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
                                    int distanceOfJourneyFromFile = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
                                    systemInterface.addJourney(scanner, journeyIDFromFile, transportModeFromFile, startOfJourneyFromFile, endOfJourneyFromFile, distanceOfJourneyFromFile);
                                }
                            }
                        }
                        fileScanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    break;

                case 9: // Call method to output to file
                    System.out.println("");
                    System.out.println("Writing to file...");
                    System.out.println("");
                    System.out.println("Enter the name of the file: ");
                    scanner.nextLine(); // Consume newline left-over
                    String outputFilename = scanner.nextLine();
                    systemInterface.writeToFile(outputFilename);
                    break;
                case 10: // Exit the program
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
    }
}
