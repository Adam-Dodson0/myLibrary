/*   File Name: Competitions
     Purpose: Competition Data for GC_Esports430
     Author: Adam Dodson
     Date: 23/8/2023
     Version: 1.0
     Notes:
*/
package gc_esports_gui;


public class Competition {
    //private data
    //League of Legends,14-Jan-2023,Tafe Coomera,BioHazards,2
    private String game;
    private String competitionDate;
    private String location;
    private String team;
    private int points;
    
    //public constructor method (With parameters)
    public Competition(String game, String competitionDate, String location, String team, int points)
    {
        this.game = game;
        this.competitionDate = competitionDate;
        this.location = location;
        this.team   = team;
        this.points = points;
    }
    //Public Get methods (accessor)
    public String getGame()
    {
        return game;
    }
    public String getCompetitionDate()
    {
        return competitionDate;
    }
    public String getLocation()
    {
        return location;
    }
    public String getTeam()
    {
        return team;
    }
    public int getPoints()
    {
        return points;
    }
    //Public Set methods (Mutator)
    public void setGame(String game)
    {
        this.game = game;
    }
    public void setCompetitionDate(String competitionDate)
    {
        this.competitionDate = competitionDate;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public void setTeam(String team)
    {
        this.team = team;
    }
    public void setPoints(int points)
    {
        this.points = points;
    }
 
    //override toString() method from the object class
    @Override
    public String toString()
    {
        //return csv formatted String
        
        return game + "," + location + "," + competitionDate + "," + team + "," + points;
    }
    
}
