// Noah Liden

public class SmartCard {
    private int cardID;
    private char type;
    // can be "A" for adult, "C" for child, or "S" for senior
    private float balance;
    // the balance available on the card. Card should have a minimum balance of $5. If it’s less than that, the program should show a message and ask again.
    private Journey[] journeys = new Journey[3];



    public SmartCard(int cardID, char type, float balance) {
        this.cardID = cardID;
        this.type = type;
        this.balance = balance;
    }

    public void addJourney(Journey journey) {
        for (int i = 0; i < journeys.length; i++) {
            if (journeys[i] == null) {
                journeys[i] = journey;
                return;
            }
        }
        System.out.println("Cannot add more journeys. Maximum limit reached.");
    }

    public boolean removeJourney(int journeyID) {
        for (int i = 0; i < journeys.length; i++) {
            if (journeys[i] != null && journeys[i].getJourneyID() == journeyID) {
                journeys[i] = null;
                return true;
            }
        }
        return false;
    }

    public Journey getJourney(int journeyID) {
    for (Journey journey : journeys) {
        if (journey != null && journey.getJourneyID() == journeyID) {
            return journey;
        }
    }
    return null;
    }

    public void listJourneys() {
        boolean hasJourneys = false;
        for (Journey journey : journeys) {
            if (journey != null) {
                hasJourneys = true;
                System.out.println("Journey " + journey.getJourneyID() + " has transport mode " + journey.getTransportMode() + " starting from " + journey.getStartOfJourney() + " and ending at " + journey.getEndOfJourney() + " with journey distance of " + journey.getDistanceOfJourney() + " station(s)/stop(s)");
            }
        }
        if (!hasJourneys) {
            System.out.println("No journeys exist on this SmartCard.");
        }
    }

    public boolean journeyExists(int journeyID) {
        for (Journey journey : journeys) {
            if (journey != null && journey.getJourneyID() == journeyID) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfJourneys() {
        int count = 0;
        for (Journey journey : journeys) {
            if (journey != null) {
                count++;
            }
        }
        return count;
    }

    public boolean printJourneysByTransportMode(String transportMode) {
        boolean found = false;
        for (Journey journey : journeys) {
            if (journey != null && journey.getTransportMode().equals(transportMode)) {
                System.out.println("Journey " + journey.getJourneyID() + " has that transport mode, and it belongs to smartcard " + getCardID());
                found = true;
            }
        }
        return found;
    }
    
    public double calculateTotalFare() {
        double totalFare = 0;
        for (Journey journey : journeys) {
            if (journey != null) {
                totalFare += calculateFare(journey);
            }
        }
        return totalFare;
    }

    private double calculateFare(Journey journey) {
        double farePerUnitDistance;

        switch (type) {
            case 'A':
                farePerUnitDistance = 2.24;
                break;
            case 'C':
                farePerUnitDistance = 1.86;
                break;
            case 'S':
                farePerUnitDistance = 1.60;
                break;
            default:
                return 0;
        }

        return 1.5 + farePerUnitDistance * journey.getDistanceOfJourney();
    }
    
    public void printFareByTransportMode() {
        double totalTrainFare = 0;
        double totalBusFare = 0;
        double totalTramFare = 0;
    
        for (Journey journey : journeys) {
            if (journey != null) {
                switch (journey.getTransportMode()) {
                    case "train":
                        totalTrainFare += calculateFare(journey);
                        break;
                    case "bus":
                        totalBusFare += calculateFare(journey);
                        break;
                    case "tram":
                        totalTramFare += calculateFare(journey);
                        break;
                }
            }
        }
    
        System.out.println("Total train fare: " + totalTrainFare);
        System.out.println("Total bus fare: " + totalBusFare);
        System.out.println("Total tram fare: " + totalTramFare);
    }

    
    public int getCardID() {
        return this.cardID;
    }
    public char getType() {
        return this.type;
    }
    public Journey[] getJourneys() {
        return journeys;
    }
    public float getBalance() {
        return this.balance;
    }

}