package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;


/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST32 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page6.html";

    @Override
    public void handle(Context context) throws Exception {
        String html = """
            <!DOCTYPE html>
            <html lang='en'>
            <head>
                <meta charset='UTF-8'>
                <meta http-equiv='X-UA-Compatible' content='IE=edge'>
                <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                <title>Document</title>
                <link rel='stylesheet' type='text/css' href='common.css' />
                <link rel='stylesheet' href='task3B.css'>

            </head>
            <body>
            
            
                <header id='top'>
                    <div class='left'>
                        <h1><a style="text-decoration:none" href='/'>CloseTheGap</a></h1>
                    </div>
                    <div class='rightLinks'>
                        <a href='/'>Homepage</a>
                        <a href='/mission.html'>Our Mission</a>
                        <a href='page3.html'>View Raw Data</a>
                        <a href='page4.html'>Focused View</a>
                        <a href='page5.html'>GAP score</a>
                        <a href='page6.html' class='linkActive'>Similar LGAs</a>

                       
            
                    </div>
                    
                </header>
                <main>
                    <form id='outcomeForm' action='/page6.html' method='POST'>
                        <h1>Select a dataset to find similarity for:</h1>
                        <input type='radio' id='outcome1' name='outcomes' value='LTHC' required> 
                        <label for='outcome1'>Long term health condition(1)</label>
                        <br>
                        <input type='radio' id='outcome5' name='outcomes' value='schoolCompletion'> 
                        <label for='outcome5'>School completion(5)</label>
                        <br>
                        <input type='radio' id='outcome8' name='outcomes' value='weeklyIncome'> 
                        <label for='outcome8'>Weekly Income(8)</label>
                        <br>
                        <input type='radio' id='population' name='outcomes' value='populationStatistics'> 
                        <label for='population'>Population Statistics</label>
                        <br>
                        <br>
                        <h1> Select year</h1>
                        <input type='radio' id='2016', name='year', value=2016 required>
                        <label for='2016'>2016</label>
                        <br>
                        <input type='radio' id='2021', name='year', value=2021>
                        <label for='2021'>2021</label>
                        <br>
                        <br>
                        <input type='submit'>
                    
                        
                    </form>
                    <br>
            """;
               
        if((context.formParam("outcomes") == null || context.formParam("year") == null) && context.queryParam("category") == null){
            html = html + "<h1>Please select from both options before you submit</h1>";
        }else{

            String[] schoolCompletion = {"Year 8 or Below", "Year 9 or Equivalent", "Year 10 or Equivalent", "Year 11 or Equivalent", "Year 12 or Equivalent"};
            String[] indigenousStatus = {"Indigenous", "Non-indigenous", "Not Stated"};            
            String[] populationStatistics = {
                "_0_4",
                "_5_9",
                "_10_14",
                "_15_19",
                "_20_24",
                "_25_29",
                "_30_34",
                "_35_39",
                "_40_44",
                "_45_49",
                "_50_54",
                "_55_59",
                "_60_64",
                "_65_yrs_ov"
            };
            String[] LTHC = {
                "Arthritis",
                "Asthma",
                "Cancer",
                "Dementia",
                "Heart Disease",
                "Kidney Disease",
                "Lung Condition",
                "Mental Health",
                "Stroke",
                "Other"
            };
            String[] weeklyIncome2016 = {
                "1-149",
                "150-299",
                "300-399",
                "400-499",
                "500-649",
                "650-799",
                "800-999",
                "1000-1249",
                "1250-1499",
                "1500-1999",
                "2000-2499",
                "2500-2999",
                "3000-more"         
            };
            String[] weeklyIncome2021 = {
                "1-149",
                "150-299",
                "300-399",
                "400-499",
                "500-649",
                "650-799",
                "800-999",
                "1000-1249",
                "1250-1499",
                "1500-1749",
                "1750-1999",
                "2000-2499",
                "2500-2999",
                "3000-3499",
                "3500-more"
            };

            String[] weeklyIncomeIndigenousStatus = {
                "Indigenous households",
                "Other Households",
                "Total Households" 
            };
            HashMap<String, String[]> checkListValues = new HashMap<String, String[]>();
            checkListValues.put("schoolCompletion", schoolCompletion);
            checkListValues.put("indigenousStatus", indigenousStatus);
            checkListValues.put("populationStatistics", populationStatistics);
            checkListValues.put("LTHC", LTHC);
            checkListValues.put("weeklyIncome2016", weeklyIncome2016);
            checkListValues.put("weeklyIncome2021", weeklyIncome2021);
            checkListValues.put("weeklyIncomeIndigenousStatus", weeklyIncomeIndigenousStatus);



            String yearStr = context.formParam("year");
            int year = Integer.parseInt(yearStr);
            String outcome = context.formParam("outcomes");

            JDBCConnection jdbc = new JDBCConnection();
            ArrayList<LGA> lgas = jdbc.getLGAs(year);
            html = html + "<div class='bottomLeftContainer'>";
            html = html + "<h1>Year selected:" + year +"</h1>";
            html = html + "<h1>Outcome Selected:" + outcome + "</h1><br>";
            html = html + "<form action='page6.html' method='POST'>";
            html = html + "<input hidden value="+ year + " name='year'>";
            html = html + "<input hidden value="+ outcome + " name='outcomes'>";

            html = html + "<label for='lgaSelect'><h1>Select LGA:</h1></label>";
            html = html + "<select id='lgaSelect' name='lgaSelect'>";
                for(int i = 0; i<lgas.size(); i++){
                    html = html + "<option value='" + lgas.get(i).getCode()  + "'>" + lgas.get(i).getName() + "</option>";
                }
            html = html + "</select>";
            html = html + "<br>";
            html = html + "<br>";
            html = html + "<h1>Select Indigenous Status</h1>";
           if(!outcome.equals("weeklyIncome")){
                for(int i = 0; i<checkListValues.get("indigenousStatus").length; i++){
                    html = html + "<input type='checkbox' name='indigenousCategory' id='indigenousCategory" + i + "' value='" + checkListValues.get("indigenousStatus")[i] + "'>";   
                    html = html + "<label for='indigenousCategory" + i + "'>" + checkListValues.get("indigenousStatus")[i] +  "</label><br>";               
                }
           }else{
                for(int i = 0; i<checkListValues.get("weeklyIncomeIndigenousStatus").length; i++){
                    html = html + "<input type='checkbox' name='indigenousCategory' id='indigenousCategory" + i + "' value='" + checkListValues.get("weeklyIncomeIndigenousStatus")[i] + "'>";   
                    html = html + "<label for='indigenousCategory" + i + "'>" + checkListValues.get("weeklyIncomeIndigenousStatus")[i] +  "</label><br>";               
                }
           }     
            

            html = html + "<br><h1>Select range from below:</h1>";
            if(!outcome.equals("weeklyIncome")){
                for(int i = 0; i<checkListValues.get(outcome).length; i++){
                    html = html + "<input type='checkbox' name='category' id='category" + i + "' value='" + checkListValues.get(outcome)[i] + "'>";   
                    html = html + "<label for='category" + i + "'>" + checkListValues.get(outcome)[i] +  "</label><br>";               
                }
            }else{
                System.out.println(checkListValues.get(outcome + Integer.toString(year)));
                for(int i = 0; i<checkListValues.get(outcome + Integer.toString(year)).length; i++){
                    html = html + "<input type='checkbox' name='category' id='category" + i + "' value='" + checkListValues.get(outcome + Integer.toString(year))[i] + "'>";   
                    html = html + "<label for='category" + i + "'>" + checkListValues.get(outcome + Integer.toString(year))[i] +  "</label><br>";               
                }
            }

            html = html + "<input type='submit'></form><br>";
            html = html + "</div>";

            List<String> indigenousStat = context.formParams(("indigenousCategory"));
            List<String> categories = context.formParams(("category"));
            String lga = context.formParam("lgaSelect");

            if(indigenousStat.size() > 0 && categories.size() > 0 ){
                html = html + "<div class='labelContainer'>";
                html = html + "<h1> Selected indigenous categories: " + indigenousStat +"</h1>";
                html = html + "<h1> Selected ranges: " + categories +"</h1>";    
                html = html + "<h1> Selected lga code: " + lga + "</h1>";
                int lgaCode = Integer.parseInt(lga);
                ArrayList<similarity> similarLgas = jdbc.getSimilarity(indigenousStat, categories, lgaCode, year, outcome);
                html = html + "<br>";
                html = html + "<input type='checkbox' id='tableContainerGap' class='hidden'>";
                html = html + "<label for='tableContainerGap' class='specialLabel rotateArrowGap'>Details of target LGA: </label>"; 
                html = html + "<div class='tableContainer tableContainerGapDrop'>";
                html = html + outputTargetLGA(lgaCode, year);
                html = html + "</div>";

                html = html + "<input type='checkbox' id='tableContainerRaw' class='hidden'>";
                html = html + "<label for='tableContainerRaw' class='specialLabel rotateArrowRaw'>Similar LGAs sorted from most to least similar: </label>";
                html = html + "<div class='tableContainer tableContainerRawDrop'>";
                html = html + outputSimilarityTable(similarLgas);
                html = html + "</div>";
                
                html = html + "<input type='checkbox' id='tableContainerProportion' class='hidden'>";               
                html = html + "<label for='tableContainerProportion' class='specialLabel rotateArrowProportion'>Details for similar LGAs: </label>"; 
                html = html + "<div class='tableContainer tableContainerProportionDrop'>";
                html = html + outputDetailsOfSimilarLGA(similarLgas);
                html = html + "</div>";

 
                html = html + "</div>";

            }

        }

        html = html + """    
              </main>
            </body>
        </html>
        """;
        context.html(html);
    }
    public String outputTargetLGA(int code, int year){
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        html = html + "<table>";
        html = html + "<thead>";
        html = html + "<tr><th>LGA code</th>";
        html = html + "<th>State</th>";
        html = html + "<th>Name</th>";
        html = html + "<th>Year</th>";
        html = html + "<th>Type</th>";
        html = html + "<th>Area(sqKm)</th>";
        html = html + "<th>Latitude</th>";
        html = html + "<th>Longitude</th></tr>";
        html = html + "</thead>";
        html = html + "<tbody>";
        LGAdetails detail = jdbc.getCertainLGADetails(code, year);
        html = html + "<tr>";
        html = html + "<td>" + detail.getCode() + "</td>";
        html = html + "<td>" + detail.getStateAbbr() + "</td>";
        html = html + "<td>" + detail.getLgaName() + "</td>";
        html = html + "<td>" + detail.getYear() + "</td>";
        html = html + "<td>" + detail.getLgaType() + "</td>";
        html = html + "<td>" + detail.getAreaSqkm() + "</td>";
        html = html + "<td>" + detail.getLatitude() + "</td>";
        html = html + "<td>" + detail.getLongitude() + "</td></tr>";
        html = html + "</tbody>";
        html = html + "</table>";
        return html;
    }

    public String outputDetailsOfSimilarLGA(ArrayList<similarity> similarLgas){
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        html = html + "<table>";
        html = html + "<thead>";
        html = html + "<tr><th>LGA code</th>";
        html = html + "<th>State</th>";
        html = html + "<th>Name</th>";
        html = html + "<th>Year</th>";
        html = html + "<th>Type</th>";
        html = html + "<th>Area(sqKm)</th>";
        html = html + "<th>Latitude</th>";
        html = html + "<th>Longitude</th></tr>";
        html = html + "</thead>";
        html = html + "<tbody>";

        for(int i = 0; i<similarLgas.size(); i++){
            LGAdetails detail = jdbc.getCertainLGADetails(similarLgas.get(i).getCode(), similarLgas.get(i).getYear() );
            html = html + "<tr>";
            html = html + "<td>" + detail.getCode() + "</td>";
            html = html + "<td>" + detail.getStateAbbr() + "</td>";
            html = html + "<td>" + detail.getLgaName() + "</td>";
            html = html + "<td>" + detail.getYear() + "</td>";
            html = html + "<td>" + detail.getLgaType() + "</td>";
            html = html + "<td>" + detail.getAreaSqkm() + "</td>";
            html = html + "<td>" + detail.getLatitude() + "</td>";
            html = html + "<td>" + detail.getLongitude() + "</td></tr>";
        }

        html = html + "</tbody>";
        html = html + "</table>";
        return html;
    }



    public String outputSimilarityTable(ArrayList<similarity> similarLgas){
        
        String html = "";
        html = html + "<table>";
        html = html + "<thead>";
        html = html + "<tr><th>LGA code</th>";
        html = html + "<th>Year</th>";
        html = html + "<th>LGA count</th>";
        html = html + "<th>Target count</th>";
        html = html + "<th>Absolute Proportional DIfference</th></tr>";
        html = html + "</thead>";
        html = html + "<tbody>";
        for(int i = 0; i<similarLgas.size(); i++){
            html = html + "<tr>";
            html = html + "<td>" + similarLgas.get(i).getCode() + "</td>";
            html = html + "<td>" + similarLgas.get(i).getYear() + "</td>";
            html = html + "<td>" + similarLgas.get(i).getPopuplation() + "</td>";
            html = html + "<td>" + similarLgas.get(i).getTargetPopulation() + "</td>";
            html = html + "<td>" + similarLgas.get(i).getDifference() + "</td>";
            html = html + "</tr>";
        }
        html = html + "</tbody>";
        html = html + "</table>";

        return html;
    }



}
