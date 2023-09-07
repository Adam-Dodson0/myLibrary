/*   File Name: Team
     Purpose: Team Data for GC_Esports430
     Author: Adam Dodson
     Date: 23/8/2023
     Version: 1.0
     Notes:
*/
package gc_esports_gui;


public class Team {
    
    //private data fields
    
    private String teamName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    
    //public constructor method
    public Team (String teamName, String contactName, String contactPhone, String contactEmail)
    {
        this.teamName = teamName;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }
    
    //public get methods (Accessor)
    public String getTeamName()
    {
        return teamName;
    }
    public String getContactName()
    {
        return contactName;
    }
    public String getContactPhone()
    {
        return contactPhone;
    }
    public String getContactEmail()
    {
        return contactEmail;
    }
     
    //public set method (mutator)
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    //override toString() method
    @Override
    public String toString(){
        
    
           //outputs or returns a csv formatted string 
           return teamName + "," + contactName + "," + contactPhone + "," + contactEmail;
    }
}
