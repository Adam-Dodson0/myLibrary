/*   File Name: GC_Esports_GUI
     Purpose: graphical interface for GC_Esports430
     Author: Adam Dodson
     Date: 23/8/2023
     Version: 1.0
     Notes: functionalities
1.Application Launched
display read-in competition results into JTable
display read-in team names in 2 JComboBoxes
display team info for first team name in Update Panel

2.Add new Competition Data
add a new validated competition result
to arraylist<competiton> competitions
and display JTable

3. Add new team
Add a new validated team
to arraylist<Team> teams
and add to the 2 JComboBoxes

4. Update existing team
update details for a selected existing team
validate changes to person, phone, email
and change values in arralist<Team> for the specific team.
*/
package gc_esports_gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class GC_Esports_GUI extends javax.swing.JFrame {

    
    //private data
    //for storing comp results
    private ArrayList<Competition> competitions;
    //for storing team data
    private ArrayList<Team> teams;
    //boolean to track the item selection changes to the JComboBoxes
    boolean comboBoxStatus = false;
    //for customising the JTable(Which displays comp results)
    private DefaultTableModel compResultsTableModel;
    
    //Constructor method  
    /**
     * Creates new form GC_Esports_GUI
     */
    public GC_Esports_GUI() {
        //1 initialise private data fields
        competitions = new ArrayList<Competition>();
        teams = new ArrayList<Team>();
        comboBoxStatus = false;
        compResultsTableModel = new DefaultTableModel();
        //2 customise table model
        //customised column names for JTable(competition results)
        String[] columnNames_Results = new String[]
                            {"Date", "Location","Game","Team", "Points"};
        
        //setup customisation
        compResultsTableModel.setColumnIdentifiers(columnNames_Results);
        
        //3 initialise all swing controls
        initComponents();
        
        //4 customise table columns in JTable
        resizeTableColumns();
        //5 read in external csv files
        readCompetitionData();
        readTeamData();
        //6 display competition data in JTable
        displayJTable();
        //7 display team names in combo boxes
        displayTeams();
        //8 Display Team details in panel with team combo boxes
        displayTeamDetails();
        
        comboBoxStatus = true;
    }

    private void resizeTableColumns()
    {
        //columns: Date, Location, Competition, Team, Points
        //(Total numeric values must = 1)
        double[] columnWidthPercentage = {0.2f, 0.2f, 0.3f, 0.2f, 0.1f};
        int tableWidth = teamCompresults_JTable.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = teamCompresults_JTable.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++)
        {
            column = tableColumnModel.getColumn(i);
            float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth((int)pWidth);
        }
    }
    
    
    private void readCompetitionData()
    {
        try
        {
            //1 Create reader and designate external file to read from
            FileReader reader = new FileReader("Competitions.csv");
            //2 Create BufferedReader which uses the reader object
            BufferedReader bufferedReader = new BufferedReader(reader);
            //3 Declare line String (used to read and store each line read from 
            // the external file
            String line;
            //4 Loop through each line in the external file
            //  Until the End of file
            while((line = bufferedReader.readLine()) != null)
            {
                //System.out.println(line);
                //check in line is not empty and contains something
                if (line.length() > 0)
                {
                    //split the line by its delimiter comma
                    //and set up each value in the lines Array
                    //Leage of Legends, 14-Jan-2022,TAFE Coomera,BioHazards,5
                    String [] lineArray = line.split(",");
                    //set up individual variables for each split line component
                    String game = lineArray[0];
                    String location = lineArray[1];
                    String compDate = lineArray[2];
                    String team = lineArray[3];
                    int points = Integer.parseInt(lineArray[4]);
                    //create competition instance
                    Competition comp = new Competition(game, compDate, location, team, points);
                    //add instance to ArrayList
                    competitions.add(comp);
                }
            }
            //5 close the reader object
            reader.close();
        }
        catch (FileNotFoundException fnfe)
        {
            //catch any file not found exception
            System.out.println("ERROR: Competitions.csv file not found!");
        }
        catch (IOException ioe)
        {
            //catch any file IO-related Exception
            System.out.println("ERROR: Competitions.csv file found, but there is a read problem!");
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("ERROR: Number format exception - trying to convert a string");
        }
    }
    
    private void readTeamData()
    {
     
        try
        {
            //1 Create reader and designate external file to read from
            FileReader reader = new FileReader("Teams.csv");
            //2 Create BufferedReader which uses the reader object
            BufferedReader bufferedReader = new BufferedReader(reader);
            //3 Declare line String (used to read and store each line read from 
            // the external file
            String line;
            //4 Loop through each line in the external file
            //  Until the End of file
            while((line = bufferedReader.readLine()) != null)
            {
                //System.out.println(line);
                //check in line is not empty and contains something
                if (line.length() > 0)
                {
                    //split the line by its delimiter comma
                    //and set up each value in the lines Array
                    //Leage of Legends, 14-Jan-2022,TAFE Coomera,BioHazards,5
                    String [] lineArray = line.split(",");
                    //set up individual variables for each split line component
                    String teamName = lineArray[0];
                    String contactName = lineArray[1];
                    String contactPhone = lineArray[2];
                    String contactEmail = lineArray[3];
                    //create competition instance
                    Team team = new Team(teamName, contactName, contactPhone, contactEmail);
                    //add instance to ArrayList
                    teams.add(team);
                }
            }
        
            //5 close the reader object
            reader.close();
        }
        catch (FileNotFoundException fnfe)
        {
            //catch any file not found exception
            System.out.println("ERROR: Competitions.csv file not found!");
        }
        catch (IOException ioe)
        {
            //catch any file IO-related Exception
            System.out.println("ERROR: Competitions.csv file found, but there is a read problem!");
        }        
	catch (ArrayIndexOutOfBoundsException ae)
	{
	    System.out.println("Error: Array index out of bounds!");
	}
    }
    
   /* public void writeCompetitionData()
    {   
       try
       {
       FileOutputStream writer = new FileOutputStream("Competitions.csv");
        //write data to be recorded into competition csv   
       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(writer);  
       }
        catch(IOException e);
               {
                   System.out.println("ERROR: Competitions.csv cant be written to");               }
	       
	 catch(FileNotFoundException fnfe)
        {
            //catch any file not found exception
            System.out.println("ERROR: Competitions.csv file not found!");
        }
       }
    }
    
   /* public void writeTeamData()
    {
        //write data to be saved into Teams csv
        
    }
    */
    private void displayJTable()
    {
        //populate competiton data into JTable
        if (competitions.size() > 0)
        {
            //create Object[] 2D array for JTable
            Object[][] competitions2DArray = new Object[competitions.size()][];
            //populate 2D array from competitons ArrayList
            for (int i = 0; i < competitions.size(); i++)
            {
                //create Object[] for single row of data containing 6 components
                Object[] competition = new Object[5];
                //date
                competition[0] = competitions.get(i).getCompetitionDate();
                //location
                competition[1] = competitions.get(i).getLocation();
                //game
                competition[2] = competitions.get(i).getGame();
                //team
                competition[3] = competitions.get(i).getTeam();
                //points
                competition[4] = competitions.get(i).getPoints();
                //append to 2D array
                competitions2DArray[i] = competition;
            }
            
            //1 remove all existing rows if there are any
            if (compResultsTableModel.getRowCount() > 0)
            {
                for (int i = compResultsTableModel.getRowCount() - 1; i > -1; i--)
                {
                    compResultsTableModel.removeRow(i);
                }
            }
            
            //put new set of row data
            if(competitions2DArray.length > 0)
            {
		//add data to tablemodel
		for (Object[] competitions2DArray1 : competitions2DArray)
		{
		    compResultsTableModel.addRow(competitions2DArray1);
		}
                //update
                compResultsTableModel.fireTableDataChanged();
            }
            
        }
    }
    
    private void displayTeams()
    {
        
    }
    private void displayTeamDetails()
    {
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        header_JPanel = new javax.swing.JPanel();
        header_jLabel1 = new javax.swing.JLabel();
        main_JPanel = new javax.swing.JPanel();
        ajTabbedPane1 = new javax.swing.JTabbedPane();
        team_comp_results_JPanel = new javax.swing.JPanel();
        teamCompResults_JLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        teamCompresults_JTable = new javax.swing.JTable();
        displayTopTeams_JButton = new javax.swing.JButton();
        addNewCompResult_JPanel = new javax.swing.JPanel();
        newCompResults_JPanel = new javax.swing.JPanel();
        newCompResultTitle_JLabel = new javax.swing.JLabel();
        date_jLabel2 = new javax.swing.JLabel();
        date_jTextField1 = new javax.swing.JTextField();
        location_jLabel3 = new javax.swing.JLabel();
        location_jTextField = new javax.swing.JTextField();
        game_jLabel = new javax.swing.JLabel();
        game_jTextField = new javax.swing.JTextField();
        newCompResultTeam_jLabel = new javax.swing.JLabel();
        team_jComboBox = new javax.swing.JComboBox<>();
        Points_JLabel = new javax.swing.JLabel();
        points_jTextField = new javax.swing.JTextField();
        newCompResult_JButton = new javax.swing.JButton();
        addNewTeam_JPanel = new javax.swing.JPanel();
        newTeamTitle_JLabel = new javax.swing.JLabel();
        teamName_JLabel = new javax.swing.JLabel();
        teamName_JTextField = new javax.swing.JTextField();
        contactPerson_JLabel = new javax.swing.JLabel();
        contactPerson_JTextField = new javax.swing.JTextField();
        contactPhone_JLabel = new javax.swing.JLabel();
        contactPhone_JTextField = new javax.swing.JTextField();
        contactEmail_JLabel = new javax.swing.JLabel();
        contactEmail_JTextField = new javax.swing.JTextField();
        newTeamButton_JButton = new javax.swing.JButton();
        updateExistTeam_JPanel = new javax.swing.JPanel();
        existingTeaamTitle_JLabel = new javax.swing.JLabel();
        team_JLabel = new javax.swing.JLabel();
        teamSelection_JComboBox = new javax.swing.JComboBox<>();
        contactPerson_jLabel3 = new javax.swing.JLabel();
        contactPerson_jTextField1 = new javax.swing.JTextField();
        contactPhone_JLabel1 = new javax.swing.JLabel();
        ContactPhone_JTextField1 = new javax.swing.JTextField();
        contact_EmailjLabel1 = new javax.swing.JLabel();
        contactEmail_jTextField1 = new javax.swing.JTextField();
        existingTeamjButton1 = new javax.swing.JButton();

        jOptionPane1.setBackground(new java.awt.Color(255, 255, 255));
        jOptionPane1.setMessage(team_comp_results_JPanel.getContainerListeners());
        jOptionPane1.setToolTipText("TOP TEAMS RESULTS");
        jOptionPane1.setValue(team_comp_results_JPanel);
        jOptionPane1.setDebugGraphicsOptions(teamCompResults_JLabel.getComponentCount());
        jOptionPane1.getAccessibleContext().setAccessibleParent(displayTopTeams_JButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gold Coast Esports");

        header_JPanel.setBackground(new java.awt.Color(255, 255, 255));

        header_jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GC_Esports_header/GoldCoastESports_Header.jpg"))); // NOI18N

        javax.swing.GroupLayout header_JPanelLayout = new javax.swing.GroupLayout(header_JPanel);
        header_JPanel.setLayout(header_JPanelLayout);
        header_JPanelLayout.setHorizontalGroup(
            header_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header_jLabel1)
        );
        header_JPanelLayout.setVerticalGroup(
            header_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header_jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        main_JPanel.setBackground(new java.awt.Color(255, 255, 255));
        main_JPanel.setPreferredSize(new java.awt.Dimension(800, 484));

        ajTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        teamCompResults_JLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        teamCompResults_JLabel.setText("Team Competition Results");

        teamCompresults_JTable.setModel(compResultsTableModel);
        jScrollPane1.setViewportView(teamCompresults_JTable);

        displayTopTeams_JButton.setText("Display Top Teams");
        displayTopTeams_JButton.setActionCommand("OptionPaneUI");
        displayTopTeams_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayTopTeams_JButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout team_comp_results_JPanelLayout = new javax.swing.GroupLayout(team_comp_results_JPanel);
        team_comp_results_JPanel.setLayout(team_comp_results_JPanelLayout);
        team_comp_results_JPanelLayout.setHorizontalGroup(
            team_comp_results_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(team_comp_results_JPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(team_comp_results_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(displayTopTeams_JButton)
                    .addGroup(team_comp_results_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(teamCompResults_JLabel)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        team_comp_results_JPanelLayout.setVerticalGroup(
            team_comp_results_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(team_comp_results_JPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(teamCompResults_JLabel)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(displayTopTeams_JButton)
                .addGap(30, 30, 30))
        );

        displayTopTeams_JButton.getAccessibleContext().setAccessibleDescription("");
        displayTopTeams_JButton.getAccessibleContext().setAccessibleParent(team_comp_results_JPanel);

        ajTabbedPane1.addTab("Team Competition result", team_comp_results_JPanel);

        newCompResultTitle_JLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        newCompResultTitle_JLabel.setText("Add new Competition result");

        date_jLabel2.setText("Date :");

        location_jLabel3.setText("Location :");

        game_jLabel.setText("Game :");

        newCompResultTeam_jLabel.setText("Team :");

        team_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Coomera Bombers", "BioHazards", "Buttercups", "Nerang Necros", "Keyboard Rangers" }));

        Points_JLabel.setText("Points");

        points_jTextField.setText("0");

        newCompResult_JButton.setText("Add new competition result");
        newCompResult_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCompResult_JButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout newCompResults_JPanelLayout = new javax.swing.GroupLayout(newCompResults_JPanel);
        newCompResults_JPanel.setLayout(newCompResults_JPanelLayout);
        newCompResults_JPanelLayout.setHorizontalGroup(
            newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(newCompResultTitle_JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(newCompResult_JButton)
                        .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                            .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                                    .addGap(71, 71, 71)
                                    .addComponent(date_jLabel2))
                                .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                                    .addGap(54, 54, 54)
                                    .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(location_jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(newCompResultTeam_jLabel)
                                            .addComponent(game_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Points_JLabel)))))
                            .addGap(30, 30, 30)
                            .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(date_jTextField1)
                                .addComponent(location_jTextField)
                                .addComponent(game_jTextField)
                                .addComponent(team_jComboBox, 0, 282, Short.MAX_VALUE)
                                .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                                    .addComponent(points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))))
                .addGap(372, 372, 372))
        );
        newCompResults_JPanelLayout.setVerticalGroup(
            newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCompResults_JPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(newCompResultTitle_JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date_jLabel2)
                    .addComponent(date_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(location_jLabel3)
                    .addComponent(location_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(game_jLabel)
                    .addComponent(game_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newCompResultTeam_jLabel)
                    .addComponent(team_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(newCompResults_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Points_JLabel)
                    .addComponent(points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(newCompResult_JButton)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout addNewCompResult_JPanelLayout = new javax.swing.GroupLayout(addNewCompResult_JPanel);
        addNewCompResult_JPanel.setLayout(addNewCompResult_JPanelLayout);
        addNewCompResult_JPanelLayout.setHorizontalGroup(
            addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newCompResults_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addNewCompResult_JPanelLayout.setVerticalGroup(
            addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newCompResults_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ajTabbedPane1.addTab("Add new Competition Result", addNewCompResult_JPanel);

        newTeamTitle_JLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        newTeamTitle_JLabel.setText("Add new team");
        newTeamTitle_JLabel.setPreferredSize(new java.awt.Dimension(141, 22));

        teamName_JLabel.setBackground(new java.awt.Color(255, 255, 255));
        teamName_JLabel.setText("Team Name :");

        contactPerson_JLabel.setBackground(new java.awt.Color(255, 255, 255));
        contactPerson_JLabel.setText("Contact Person :");

        contactPhone_JLabel.setText("Contact Phone :");

        contactEmail_JLabel.setText("Contact Email :");

        newTeamButton_JButton.setText("Add New Team");
        newTeamButton_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTeamButton_JButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewTeam_JPanelLayout = new javax.swing.GroupLayout(addNewTeam_JPanel);
        addNewTeam_JPanel.setLayout(addNewTeam_JPanelLayout);
        addNewTeam_JPanelLayout.setHorizontalGroup(
            addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newTeamButton_JButton)
                    .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                        .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(contactEmail_JLabel)
                            .addComponent(contactPhone_JLabel)
                            .addComponent(contactPerson_JLabel)
                            .addComponent(teamName_JLabel)
                            .addComponent(newTeamTitle_JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(teamName_JTextField)
                            .addComponent(contactPerson_JTextField)
                            .addComponent(contactPhone_JTextField)
                            .addComponent(contactEmail_JTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        addNewTeam_JPanelLayout.setVerticalGroup(
            addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(newTeamTitle_JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamName_JLabel)
                    .addComponent(teamName_JTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactPerson_JLabel)
                    .addComponent(contactPerson_JTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactPhone_JLabel)
                    .addComponent(contactPhone_JTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactEmail_JLabel)
                    .addComponent(contactEmail_JTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(newTeamButton_JButton)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        ajTabbedPane1.addTab("Add new Teams", addNewTeam_JPanel);

        existingTeaamTitle_JLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        existingTeaamTitle_JLabel.setText("Update an Existing Team");

        team_JLabel.setText("Team :");

        teamSelection_JComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Coomera Bombers", "BioHazards", "Buttercups", "Nerang Necros", "Keyboard Rangers" }));
        teamSelection_JComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teamSelection_JComboBoxActionPerformed(evt);
            }
        });

        contactPerson_jLabel3.setText("Contact Person :");

        contactPhone_JLabel1.setText("Contact Phone :");

        contact_EmailjLabel1.setText("Contact Email :");

        existingTeamjButton1.setText("Update Existing Team");
        existingTeamjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingTeamjButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updateExistTeam_JPanelLayout = new javax.swing.GroupLayout(updateExistTeam_JPanel);
        updateExistTeam_JPanel.setLayout(updateExistTeam_JPanelLayout);
        updateExistTeam_JPanelLayout.setHorizontalGroup(
            updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateExistTeam_JPanelLayout.createSequentialGroup()
                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateExistTeam_JPanelLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(existingTeamjButton1)
                            .addGroup(updateExistTeam_JPanelLayout.createSequentialGroup()
                                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(contactPerson_jLabel3)
                                    .addComponent(team_JLabel)
                                    .addComponent(contactPhone_JLabel1)
                                    .addComponent(contact_EmailjLabel1))
                                .addGap(33, 33, 33)
                                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(teamSelection_JComboBox, 0, 300, Short.MAX_VALUE)
                                    .addComponent(contactPerson_jTextField1)
                                    .addComponent(ContactPhone_JTextField1)
                                    .addComponent(contactEmail_jTextField1)))))
                    .addGroup(updateExistTeam_JPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(existingTeaamTitle_JLabel)))
                .addContainerGap(282, Short.MAX_VALUE))
        );
        updateExistTeam_JPanelLayout.setVerticalGroup(
            updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateExistTeam_JPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(existingTeaamTitle_JLabel)
                .addGap(37, 37, 37)
                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(team_JLabel)
                    .addComponent(teamSelection_JComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactPerson_jLabel3)
                    .addComponent(contactPerson_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactPhone_JLabel1)
                    .addComponent(ContactPhone_JTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateExistTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_EmailjLabel1)
                    .addComponent(contactEmail_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(existingTeamjButton1)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        ajTabbedPane1.addTab("Update Existing Team", updateExistTeam_JPanel);

        javax.swing.GroupLayout main_JPanelLayout = new javax.swing.GroupLayout(main_JPanel);
        main_JPanel.setLayout(main_JPanelLayout);
        main_JPanelLayout.setHorizontalGroup(
            main_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ajTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        main_JPanelLayout.setVerticalGroup(
            main_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ajTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
            .addComponent(header_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header_JPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayTopTeams_JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayTopTeams_JButtonActionPerformed
        //for each team ... add to a total points
        //display information in a JOptionPane pop-up window
           // Method to show the results in a JOptionPane
         // Replace these values with your actual data
        int teamPoints = (0); // Replace with the actual points
        String teamName = ("Team"); // Replace with the actual team name

        // Create a message to display
        String message = "Team: \n" + teamName + "\n " + "\nPoints: \n " + "\n" + teamPoints;

        // Show the message in a JOptionPane
        JOptionPane.showMessageDialog(this, message, "TOP TEAM RESULTS", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_displayTopTeams_JButtonActionPerformed

    private void newCompResult_JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCompResult_JButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newCompResult_JButtonActionPerformed

    private void newTeamButton_JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTeamButton_JButtonActionPerformed
        // TODO add your handling code here 
    }//GEN-LAST:event_newTeamButton_JButtonActionPerformed

    private void existingTeamjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingTeamjButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingTeamjButton1ActionPerformed

    private void teamSelection_JComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teamSelection_JComboBoxActionPerformed
          
    }//GEN-LAST:event_teamSelection_JComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GC_Esports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GC_Esports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GC_Esports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GC_Esports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GC_Esports_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ContactPhone_JTextField1;
    private javax.swing.JLabel Points_JLabel;
    private javax.swing.JPanel addNewCompResult_JPanel;
    private javax.swing.JPanel addNewTeam_JPanel;
    private javax.swing.JTabbedPane ajTabbedPane1;
    private javax.swing.JLabel contactEmail_JLabel;
    private javax.swing.JTextField contactEmail_JTextField;
    private javax.swing.JTextField contactEmail_jTextField1;
    private javax.swing.JLabel contactPerson_JLabel;
    private javax.swing.JTextField contactPerson_JTextField;
    private javax.swing.JLabel contactPerson_jLabel3;
    private javax.swing.JTextField contactPerson_jTextField1;
    private javax.swing.JLabel contactPhone_JLabel;
    private javax.swing.JLabel contactPhone_JLabel1;
    private javax.swing.JTextField contactPhone_JTextField;
    private javax.swing.JLabel contact_EmailjLabel1;
    private javax.swing.JLabel date_jLabel2;
    private javax.swing.JTextField date_jTextField1;
    private javax.swing.JButton displayTopTeams_JButton;
    private javax.swing.JLabel existingTeaamTitle_JLabel;
    private javax.swing.JButton existingTeamjButton1;
    private javax.swing.JLabel game_jLabel;
    private javax.swing.JTextField game_jTextField;
    private javax.swing.JPanel header_JPanel;
    private javax.swing.JLabel header_jLabel1;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel location_jLabel3;
    private javax.swing.JTextField location_jTextField;
    private javax.swing.JPanel main_JPanel;
    private javax.swing.JLabel newCompResultTeam_jLabel;
    private javax.swing.JLabel newCompResultTitle_JLabel;
    private javax.swing.JButton newCompResult_JButton;
    private javax.swing.JPanel newCompResults_JPanel;
    private javax.swing.JButton newTeamButton_JButton;
    private javax.swing.JLabel newTeamTitle_JLabel;
    private javax.swing.JTextField points_jTextField;
    private javax.swing.JLabel teamCompResults_JLabel;
    private javax.swing.JTable teamCompresults_JTable;
    private javax.swing.JLabel teamName_JLabel;
    private javax.swing.JTextField teamName_JTextField;
    private javax.swing.JComboBox<String> teamSelection_JComboBox;
    private javax.swing.JLabel team_JLabel;
    private javax.swing.JPanel team_comp_results_JPanel;
    private javax.swing.JComboBox<String> team_jComboBox;
    private javax.swing.JPanel updateExistTeam_JPanel;
    // End of variables declaration//GEN-END:variables
}
