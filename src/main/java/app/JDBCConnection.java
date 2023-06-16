

package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs(int year) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA_PROCESSED WHERE year = " + year + ";";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code    = results.getInt("code");
                String name  = results.getString("lgaName");

                // Create a LGA Object
                LGA lga = new LGA(code, name);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    public ArrayList<SEO> getOutcomes() {
        ArrayList<SEO> outcomes = new ArrayList<SEO>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM SEO";
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id     = results.getInt("SEO_id");
                String title  = results.getString("SEO_name");
                String description = results.getString("description");
                SEO outcome = new SEO(id, title, description);
                outcomes.add(outcome);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return outcomes;
    }

    public ArrayList<personas> getPersonas(){
        ArrayList<personas> personas = new ArrayList<personas>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM persona";
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String name     = results.getString("name");
                String imageFilePath  = results.getString("imageFilePath");
                personas persona = new personas(name, imageFilePath);
                personas.add(persona);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return personas;
    }

    public ArrayList<personaAttributes> getPersonaAttributes(String name){
        ArrayList<personaAttributes> personaAttributes = new ArrayList<personaAttributes>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM personaAttributes WHERE LOWER(name) = '" + name.toLowerCase() + "'";
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("id");
                String attributeType = results.getString("attributeType");
                String desc = results.getString("desc");

                personaAttributes personaAttribute = new personaAttributes(id, attributeType, desc);
                personaAttributes.add(personaAttribute);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return personaAttributes;
    }

    public ArrayList<outcomesData> getOutcomeData(String outcomeName, int year, int limit, int offset, String sortBy, String order){
        ArrayList<outcomesData> outcomesData = new ArrayList<outcomesData>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT LGA_PROCESSED.lgaName, "  + outcomeName + ".*" + " FROM " + outcomeName;
            query = query + " JOIN LGA_PROCESSED ON ";
            query = query + outcomeName + ".code = LGA_PROCESSED.code ";
            query = query + "AND LGA_PROCESSED.year=";
            query = query + outcomeName + ".year";
            query = query + " WHERE LGA_PROCESSED.year=" + year + " ORDER BY " + sortBy + " " + order + " LIMIT " + limit + " OFFSET " + offset + ";";
            System.out.println(query);
            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int code = results.getInt("code");
                String name = results.getString("lgaName");
                String indigenousStatus = results.getString("indigenousStatus");
                String sex = "random";
                if(!outcomeName.equals("weeklyIncome")){
                    sex = results.getString("sex");
                }
                // if outcome is outcome 1 then get condition if not get school year
                String uniqueAttribute = "someString";
                if(outcomeName.equals("LTHC")){
                     uniqueAttribute = results.getString("condition");
                }else if(outcomeName.equals("populationStatistics")){
                     uniqueAttribute = results.getString("ageCategory");
                }else if(outcomeName.equals("schoolCompletion")){
                     uniqueAttribute = results.getString("schoolYear");
                }else{
                    uniqueAttribute = results.getString("incomeBracket");
                }

                
                int count = results.getInt("count");

                outcomesData myOutcomesData;
                if(outcomeName.equals("weeklyIncome")){
                      myOutcomesData = new outcomesData(code, name, indigenousStatus, uniqueAttribute, count);
                }else{
                      myOutcomesData = new outcomesData(code, name, indigenousStatus, sex, uniqueAttribute, count);
                }
                outcomesData.add(myOutcomesData);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return outcomesData;
    }        



    public int getOutcomeLength(String outcomeName, int year){
        Connection connection = null;
        int numRows = 0;
        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            if(year != 0){
                 query = "SELECT COUNT(*) AS rows FROM " + outcomeName + " WHERE year = " + year;
            }else{
                query = "SELECT COUNT(*) AS rows FROM " + outcomeName;
            }
            
            ResultSet results = statement.executeQuery(query);
            numRows = results.getInt("rows");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return numRows;
    }


    public ArrayList<outcomesProportion> getProportionData(String outcomeName, int year, int limit, int offset){
        ArrayList<outcomesProportion> proportionData = new ArrayList<outcomesProportion>();
        HashMap<String, String> outcomeNameMapToView = new HashMap<String, String>();
        outcomeNameMapToView.put("schoolCompletion", "proportionSchoolCompletion2021");
        outcomeNameMapToView.put("weeklyIncome", "proportionWeeklyIncome2021");
        outcomeNameMapToView.put("populationStatistics", "proportionPopulation2021");
        outcomeNameMapToView.put("LTHC", "proportionLTHC2021");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM " + outcomeNameMapToView.get(outcomeName) + " LIMIT " + limit + " OFFSET " + offset;
            System.out.println(query);
            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int code = results.getInt("code");
                String name = results.getString("lgaName");
                String indigenousStatus = results.getString("indigenousStatus");
                String sex = "random";
                if(!outcomeName.equals("weeklyIncome")){
                    sex = results.getString("sex");
                }
                // if outcome is outcome 1 then get condition if not get school year
                String uniqueAttribute = "someString";
                if(outcomeName.equals("LTHC")){
                     uniqueAttribute = results.getString("condition");
                }else if(outcomeName.equals("populationStatistics")){
                     uniqueAttribute = results.getString("ageCategory");
                }else if(outcomeName.equals("schoolCompletion")){
                     uniqueAttribute = results.getString("schoolYear");
                }else{
                    uniqueAttribute = results.getString("incomeBracket");
                }

                
                int count = results.getInt("count");
                int total = results.getInt("total");
                double proportion = results.getDouble("proportion(%)");

                outcomesProportion myOutcomesProportion;
                if(outcomeName.equals("weeklyIncome")){
                      myOutcomesProportion = new outcomesProportion(code, name, indigenousStatus, uniqueAttribute, count, total, proportion);
                }else{
                    myOutcomesProportion = new outcomesProportion(code, name, indigenousStatus, sex, uniqueAttribute, count, total, proportion);
                }
                proportionData.add(myOutcomesProportion);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return proportionData;
    }


    public ArrayList<outcomesGap> getGap2A(String outcomeName, int year, int limit, int offset){
        ArrayList<outcomesGap> gapData = new ArrayList<outcomesGap>();
        HashMap<String, String> outcomeNameMapToView = new HashMap<String, String>();
        outcomeNameMapToView.put("schoolCompletion", "gapSchoolCompletion2021");
        outcomeNameMapToView.put("weeklyIncome", "gapWeeklyIncome2021");
        outcomeNameMapToView.put("LTHC", "gapLTHC2021");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM " + outcomeNameMapToView.get(outcomeName) + " LIMIT " + limit + " OFFSET " + offset;
            System.out.println(query);
            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int code = results.getInt("code");
                String name = results.getString("lgaName");
                String sex = "random";
                if(!outcomeName.equals("weeklyIncome")){
                    sex = results.getString("sex");
                }
                // if outcome is outcome 1 then get condition if not get school year
                String uniqueAttribute = "someString";
                if(outcomeName.equals("LTHC")){
                     uniqueAttribute = results.getString("condition");
                }else if(outcomeName.equals("populationStatistics")){
                     uniqueAttribute = results.getString("ageCategory");
                }else if(outcomeName.equals("schoolCompletion")){
                     uniqueAttribute = results.getString("schoolYear");
                }else{
                    uniqueAttribute = results.getString("incomeBracket");
                }

                
                double indigenousProportion = results.getDouble("Indigenous Proportion");
                double nonIndigenousProportion = results.getDouble("Non-Indigenous Proportion");
                double gap = results.getDouble("gap");

                outcomesGap row;
                if(outcomeName.equals("weeklyIncome")){
                      row = new outcomesGap(code, name, uniqueAttribute, indigenousProportion, nonIndigenousProportion, gap);
                }else{
                    row = new outcomesGap(code, name, sex, uniqueAttribute, indigenousProportion, nonIndigenousProportion, gap);
                }
                gapData.add(row);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return gapData;
    }

    public ArrayList<similarity> getSimilarity(List<String> indigenousCategory, List<String> otherRange, int lgaCode, int year, String outcome){
        Connection connection = null;
        ArrayList<similarity> similarLgas = new ArrayList<similarity>();
        HashMap<String, String> outcomeNameMapToUniqueAttr = new HashMap<String, String>();
        outcomeNameMapToUniqueAttr.put("schoolCompletion", "schoolYear");
        outcomeNameMapToUniqueAttr.put("weeklyIncome", "incomeBracket");
        outcomeNameMapToUniqueAttr.put("LTHC", "condition");
        outcomeNameMapToUniqueAttr.put("populationStatistics", "ageCategory");

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                select 
                *, 
                ROUND(ABS(
                  1 - ind / cast(total as Real)
                ), 3) as prop 
              from 
                (
                  select 
                    code, 
                    year, 
                    sum(count) as ind, 
                    (
                      select 
                        sum(count) 
                      from 
                """;


                query = query + outcome + " where (";
                if(otherRange.size() > 0){
                    if(otherRange.size() == 1){
                        query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(0) + "') AND"; 
                    }else{
                        for(int i = 0; i<otherRange.size(); i++){
                            if(i != (otherRange.size()-1)){
                                query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(i) + "' OR ";
                            }else{
                                query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(i) + "') AND";

                            }
                        }
                    }
                }
                
                if(indigenousCategory.size() > 0){
                    if(indigenousCategory.size() == 1){
                        query = query + " ( indigenousStatus = '" + indigenousCategory.get(0) + "') AND"; 
                    }else{
                        for(int i = 0; i<indigenousCategory.size(); i++){
                            if(i != (indigenousCategory.size()-1)){
                                query = query + " ( indigenousStatus = '" + indigenousCategory.get(i) + "' OR ";
                            }else{
                                query = query + " indigenousStatus = '" + indigenousCategory.get(i) + "') AND";

                            }
                        }
                    }
                }

                query = query + " year = " + year + " AND ";
                query = query + " code = " + lgaCode;
                query = query + " group by code ) as total";
                query = query + " from " + outcome + " where (";
                if(otherRange.size() > 0){
                    if(otherRange.size() == 1){
                        query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(0) + "' ) AND"; 
                    }else{
                        for(int i = 0; i<otherRange.size(); i++){
                            if(i != (otherRange.size()-1)){
                                query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(i) + "' OR ";
                            }else{
                                query = query + outcomeNameMapToUniqueAttr.get(outcome) + " = '" + otherRange.get(i) + "') AND";

                            }
                        }
                    }
                }          
                if(indigenousCategory.size() > 0){
                    if(indigenousCategory.size() == 1){
                        query = query + " ( indigenousStatus = '" + indigenousCategory.get(0) + "' ) AND "; 
                    }else{
                        for(int i = 0; i<indigenousCategory.size(); i++){
                            if(i != (indigenousCategory.size()-1)){
                                query = query + " ( indigenousStatus = '" + indigenousCategory.get(i) + "' OR ";
                            }else{
                                query = query + " indigenousStatus = '" + indigenousCategory.get(i) + "') AND " ;

                            }
                        }
                    }
                }    
                query = query + "year = " + year;
                query = query + " group by code ) WHERE";  
                query = query + " prop <> 0 ";
                query = query + "order by prop asc LIMIT 5";

            System.out.println(query);
            
            
            ResultSet results = statement.executeQuery(query);
            

            while (results.next()) {
                similarity lga;
                int code = results.getInt("code");
                int parsedYear = results.getInt("year");
                int ind = results.getInt("ind");
                int targetPopulation =  results.getInt("total");
                double difference = results.getDouble("prop");
                lga = new similarity(code, parsedYear, ind, targetPopulation, difference);
                similarLgas.add(lga);
                
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return similarLgas;
    }

    public LGAdetails getCertainLGADetails(int lgaCode, int year){
        Connection connection = null;
        LGAdetails detail = new LGAdetails();

        

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
             String query = "SELECT * FROM LGA_PROCESSED WHERE code = " +  lgaCode + " AND year = " + year + ";";
            ResultSet results = statement.executeQuery(query);
            System.out.println(query);
            while(results.next()){
                int code = results.getInt("code");
                int yearParsed = results.getInt("year");
                String stateAbbr = results.getString("stateAbbr");
                String lgaName = results.getString("lgaName");
                String lgaType = results.getString("lgaType");
                double area = results.getDouble("areaSqkm");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");
                detail = new LGAdetails(code, yearParsed, stateAbbr, lgaType, lgaName, area, latitude, longitude);
            }
            
            
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return detail;

    }
}





