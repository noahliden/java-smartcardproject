// Noah Liden

public class Journey {
    private int journeyID;
    private String transportMode;
    //the public transport mode of the journey. It can be only “train”, “bus” or “tram”. If not, the program should show a message and ask again.
    private int startOfJourney;
    private int endOfJourney;
    private int distanceOfJourney;

    public Journey(int journeyID, String transportMode, int startOfJourney, int endOfJourney, int distanceOfJourney) {
        this.journeyID = journeyID;
        this.transportMode = transportMode;
        this.startOfJourney = startOfJourney;
        this.endOfJourney = endOfJourney;
        this.distanceOfJourney = distanceOfJourney;
    }

    public int getJourneyID() {
        return this.journeyID;
    }
    public String getTransportMode() {
        return this.transportMode;
    }
    public int getStartOfJourney() {
        return this.startOfJourney;
    }
    public int getEndOfJourney() {
        return this.endOfJourney;
    }
    public int getDistanceOfJourney() {
        return this.distanceOfJourney;
    }
}
