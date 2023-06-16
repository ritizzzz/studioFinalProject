package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import ognl.enhance.OrderedReturn;
import java.util.HashMap;

import org.sqlite.jdbc3.JDBC3Connection;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST21 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3.html";
      

      

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = """
            <!DOCTYPE html>
            <html lang='en'>
            <head>
                <meta charset='UTF-8'>
                <meta http-equiv='X-UA-Compatible' content='IE=edge'>
                <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                <title>Document</title>
                <link rel='stylesheet' type='text/css' href='common.css' />
                <link rel='stylesheet' href='task2A.css'>

            </head>
            <body>
            
            
                <header id='top'>
                    <div class='left'>
                        <h1><a style="text-decoration:none" href='/'>CloseTheGap</a></h1>
                    </div>
                    <div class='rightLinks'>
                        <a href='/'>Homepage</a>
                        <a href='/mission.html'>Our Mission</a>
                        <a href='page3.html' class='linkActive'>View Raw Data</a>
                        <a href='page4.html'>Focused View</a>
                        <a href='page5.html'>GAP score</a>
                        <a href='page6.html'>Similar LGAs</a>

                       
            
                    </div>
                    
                </header>
                <main>
                    <h1>Select options from dropdown and submit</h1> 
                    <form action='/page3.html?page=1', method='post'>
                            <label for='outcomeDropdown'>Select an outcome:</label>
                            <select id='outcomeDropdown', name='outcomeDropdown'>
                                <option value='LTHC' default>1. Long Term Health Condition</option>
                                <option value='schoolCompletion'>5. School Completion</option>
                                <option value='weeklyIncome'>8. Weekly Income</option>
                                <option value='populationStatistics'>Population Statistics</option>
                            </select>

                            <label for='columnDropdown'>Select a column to sort by:</label>  
                            <select id='columnDropdown', name='columnDropdown'>
                                <option value='code'>Code</option>
                                <option value='lgaName'>Name</option>
                                <option value='indigenousStatus'>Indigenous Status</option>
                                <option value='sex'>Sex</option>
                                <option value='condition'>Condition</option>
                                <option value='count'>Count</option>
                            </select>

                            <label for='orderDropdown'>Select ascending or descending:</label>
                            <select id='orderDropdown', name='orderDropdown'>
                                <option value='ASC'>Ascending</option>
                                <option value='DESC'>Descending</option>
                            </select>

                        <input type='submit'>    
                    </form>
                    
            """;
        
        HashMap<String, String> dbNameToUI = new HashMap<String, String>();

        dbNameToUI.put("LTHC", "Long term health condition");
        dbNameToUI.put("schoolCompletion", "School Completion");
        dbNameToUI.put("weeklyIncome", "Weekly Income");
        dbNameToUI.put("populationStatistics", "Population statistics");


        if(context.formParam("outcomeDropdown") == null && context.queryParam("outcome") == null){
            System.out.println("Nothing to display");
        }else{
        
            if(context.formParam("outcomeDropdown") == null){
                int pageNum;
                int rowsPerPage = 5000;
                String outcomeName = context.queryParam("outcome");
                String orderByColumn = context.queryParam("columnDropdown");
                String ascOrDesc = context.queryParam("orderDropdown");
                
                JDBCConnection jdbc = new JDBCConnection();
                int totalRows = jdbc.getOutcomeLength(outcomeName, 2021);
                int totalNumberOfPages = (int) Math.ceil(totalRows/(double) rowsPerPage);



                if(context.queryParam("page") == null){
                    pageNum = 1;
                }else{
                    pageNum = Integer.parseInt(context.queryParam("page"));
                }
                html = html + "<div class='rightAlignedContainer2A'>";
                html = html + "<h1>Data for the year 2021 for all LGAs" + dbNameToUI.get(outcomeName) + "</h1>";
                html = html + "<div class='pagination'>";
                if(pageNum == 1){
                    html = html + "<a href=page3.html?page=" + totalNumberOfPages + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">&laquo;</a>";
                }else{
                    html = html + "<a href=page3.html?page=" + (pageNum - 1) + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">&laquo;</a>";
                }
                for(int i = 1; i < (totalNumberOfPages+1); i++){
                    if(i == pageNum){
                        html = html + "<a class='activePagination' href=page3.html?page=" + i + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">" + i + "</a>";
                    }else{
                        html = html + "<a href=page3.html?page=" + i + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">" + i + "</a>";
                    }
                }
                html = html + "<a href=page3.html?page=" + (pageNum + 1) % pageNum + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">&raquo;</a>";
                html = html + "</div>";
                
                int offset = rowsPerPage * (pageNum - 1);
                System.out.println(offset);
                html = html + outputOverallTable(outcomeName, totalRows, totalNumberOfPages, offset, orderByColumn, ascOrDesc);
                html = html + "</div>";

            }else{
                String outcomeName = context.formParam("outcomeDropdown");
                String orderByColumn = context.formParam("columnDropdown");
                String ascOrDesc = context.formParam("orderDropdown");
                
                int rowsPerPage = 5000;
                JDBCConnection jdbc = new JDBCConnection();
                int totalRows = jdbc.getOutcomeLength(outcomeName, 2021);
                int totalNumberOfPages = (int) Math.ceil(totalRows/(double) rowsPerPage);
                
                html = html + "<div class='rightAlignedContainer2A'>";
                html = html + "<h1>Data for the year 2021 for all LGAs" + dbNameToUI.get(outcomeName) + "</h1>";
                html = html + "<div class='pagination'>";
                html = html + "<a href=page3.html?page=" + (totalNumberOfPages) + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">&laquo;</a>";
                for(int i = 1; i < (totalNumberOfPages+1); i++){
                    if(i == 1){
                        html = html + "<a class='activePagination' href=page3.html?page=" + i + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">" + i + "</a>";
                    }else{
                        html = html + "<a href=page3.html?page=" + i + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">" + i + "</a>";
                    }
                }
                html = html + "<a href=page3.html?page=" + 2 + "&outcome=" + outcomeName + "&columnDropdown=" + orderByColumn + "&orderDropdown=" + ascOrDesc + ">&raquo;</a>";
                html = html + "</div>";
                html = html + outputOverallTable(outcomeName, totalRows, totalNumberOfPages, 0, orderByColumn, ascOrDesc);
                html = html + "</div>";




            }

        }
        html = html + """   
                    </main>
                    <script type='text/javascript' src='subtask21.js'></script>
                </body>
            </html>                
                """;
          
        // Makes Javalin render the webpage
        context.html(html);
    }


    public String outputOverallTable(String outcomeName, int year, int limit, int offset, String orderByColumn, String ascOrDesc){
        String html = "";
        HashMap<String, String> dbNameToUI = new HashMap<String, String>();

        dbNameToUI.put("LTHC", "Long term health condition");
        dbNameToUI.put("schoolCompletion", "School Completion");
        dbNameToUI.put("weeklyIncome", "Weekly Income");
        dbNameToUI.put("populationStatistics", "Population statistics");

        html = html + "<div class='labelContainer'>";
        html = html + "<br><input type='radio' checked id='tableContainerRaw' name='radio'>";
        html = html + "<label for='tableContainerRaw' class='specialLabel rotateArrowRaw'>Latest Data of year 2021 for " + dbNameToUI.get(outcomeName) + "</label>";
        html = html + "<div class='tableContainer tableContainerRawDrop'>";
        html = html +   outputTableHealthAndSchool(outcomeName,2021,5000,offset, orderByColumn, ascOrDesc) ;  
        html = html + "</div>"; 
        
        html = html + "<input type='radio' id='tableContainerProportion' name='radio'>";               
        html = html + "<label for='tableContainerProportion' class='specialLabel rotateArrowProportion'>Proportion values for " + dbNameToUI.get(outcomeName) + "(2021)</label>"; 
        html = html + "<div class='tableContainer tableContainerProportionDrop'>";
        html = html + outputTableProportion(outcomeName,2021,5000,offset) ;  
        html = html + "</div>"; 

        html = html + "<input type='radio' id='tableContainerGap' name='radio'>";
        html = html + "<label for='tableContainerGap' class='specialLabel rotateArrowGap'>Gap values for " + dbNameToUI.get(outcomeName) + "(2021)</label>"; 
        html = html + "<div class='tableContainer tableContainerGapDrop'>";
        html = html +   outputGap(outcomeName,2021,5000,offset) ;  
        html = html + "</div>"; 
        html = html + "</div>";
        return html;
    }

    public String outputTableHealthAndSchool(String outcomeName, int year, int limit, int offset, String sortBy, String order){
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<outcomesData> dataForOutcomes = jdbc.getOutcomeData(outcomeName, year, limit, offset, sortBy, order);
        
        
        html = html + "<table>";
        html = html + """
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Name</th>
                            <th>Indigenous Status</th>
            """;
        if(!outcomeName.equals("weeklyIncome")){
            html = html + "<th>Sex</th>";
        }
        
    
        if(outcomeName.equals("LTHC")){
            html = html + "<th>Condition</th>";
        }else if (outcomeName.equals("schoolCompletion")){
            html = html + "<th>School Year</th>";
        }else if (outcomeName.equals("populationStatistics")){
            html = html + "<th>Age category</th>";
        }else{
            html = html + "<th>Income Bracket</th>";

        }
        html = html + "<th>Count</th></tr></thead>";

        
        for(int  i = 0; i < dataForOutcomes.size(); i++){
                html = html + "<tr>";
                html = html + "<td>" + dataForOutcomes.get(i).getCode() + "</td>";
                html = html + "<td>" + dataForOutcomes.get(i).getName() + "</td>";
                html = html + "<td>" + dataForOutcomes.get(i).getIndigenousStatus() + "</td>";
                if(!outcomeName.equals("weeklyIncome")){
                    html = html + "<td>" + dataForOutcomes.get(i).getSex() + "</td>";
                }
                html = html + "<td>" + dataForOutcomes.get(i).getUniqueAttribute() + "</td>";
                html = html + "<td>" + dataForOutcomes.get(i).getCount() + "</td>";
                html = html + "</tr>";
        }
        html = html + "</table>";

        return html;
    }




    public String outputTableProportion(String outcomeName, int year, int limit, int offset){
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<outcomesProportion> dataForOutcomes = jdbc.getProportionData(outcomeName, year, limit, offset);
      
        HashMap<String, String> outcomeNameMapToView = new HashMap<String, String>();
        outcomeNameMapToView.put("schoolCompletion", "proportionSchoolCompletion2021");
        outcomeNameMapToView.put("weeklyIncome", "proportionWeeklyIncome2021");
        outcomeNameMapToView.put("populationStatistics", "proportionPopulation2021");
        outcomeNameMapToView.put("LTHC", "proportionLTHC2021");
      
        int rowNum = jdbc.getOutcomeLength(outcomeNameMapToView.get(outcomeName), 0);
        if(!(offset > rowNum)){
            html = html + "<table>";
            html = html + """
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Indigenous Status</th>
                """;
            if(!outcomeName.equals("weeklyIncome")){
                html = html + "<th>Sex</th>";
            }
            
        
            if(outcomeName.equals("LTHC")){
                html = html + "<th>Condition</th>";
            }else if (outcomeName.equals("schoolCompletion")){
                html = html + "<th>School Year</th>";
            }else if (outcomeName.equals("populationStatistics")){
                html = html + "<th>Age category</th>";
            }else{
                html = html + "<th>Income Bracket</th>";

            }
            html = html + "<th>Count</th>";
            html = html + "<th>Total</th>";
            html = html + "<th>Proportion</th></tr></thead>";

            
            for(int  i = 0; i < dataForOutcomes.size(); i++){
                    html = html + "<tr>";
                    html = html + "<td>" + dataForOutcomes.get(i).getCode() + "</td>";
                    html = html + "<td>" + dataForOutcomes.get(i).getName() + "</td>";
                    html = html + "<td>" + dataForOutcomes.get(i).getIndigenousStatus() + "</td>";
                    if(!outcomeName.equals("weeklyIncome")){
                        html = html + "<td>" + dataForOutcomes.get(i).getSex() + "</td>";
                    }
                    html = html + "<td>" + dataForOutcomes.get(i).getUniqueAttribute() + "</td>";
                    html = html + "<td>" + dataForOutcomes.get(i).getCount() + "</td>";
                    html = html + "<td>" + dataForOutcomes.get(i).getTotal() + "</td>";
                    html = html + "<td>" + dataForOutcomes.get(i).getProportion() + "</td>";
                    html = html + "</tr>";
            }
            html = html + "</table>";
        }else{
            html = html + "<h1>No more rows to display</h1>";
        }
        

        return html;
    }

    public String outputGap(String outcomeName, int year, int limit, int offset){
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();
        
        HashMap<String, String> outcomeNameMapToView = new HashMap<String, String>();
        outcomeNameMapToView.put("schoolCompletion", "gapSchoolCompletion2021");
        outcomeNameMapToView.put("weeklyIncome", "gapWeeklyIncome2021");
        outcomeNameMapToView.put("LTHC", "gapLTHC2021");

        int rowNum = jdbc.getOutcomeLength(outcomeNameMapToView.get(outcomeName), 0);
        System.out.println(offset);
        System.out.println(rowNum);
        if(!outcomeName.equals("populationStatistics") && !(offset > rowNum)){

            ArrayList<outcomesGap> gapData = jdbc.getGap2A(outcomeName, year, limit, offset);
            
            
            html = html + "<table>";
            html = html + """
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                """;
            if(!outcomeName.equals("weeklyIncome")){
                html = html + "<th>Sex</th>";
            }
            
        
            if(outcomeName.equals("LTHC")){
                html = html + "<th>Condition</th>";
            }else if (outcomeName.equals("schoolCompletion")){
                html = html + "<th>School Year</th>";
            }else if (outcomeName.equals("populationStatistics")){
                html = html + "<th>Age category</th>";
            }else{
                html = html + "<th>Income Bracket</th>";

            }
            html = html + "<th>Indigenous Proportion</th>";
            html = html + "<th>Non Indigenous Proportion</th>";
            html = html + "<th>Gap</th></tr></thead>";

            
            for(int  i = 0; i < gapData.size(); i++){
                    html = html + "<tr>";
                    html = html + "<td>" + gapData.get(i).getCode() + "</td>";
                    html = html + "<td>" + gapData.get(i).getName() + "</td>";
                    if(!outcomeName.equals("weeklyIncome")){
                        html = html + "<td>" + gapData.get(i).getSex() + "</td>";
                    }
                    html = html + "<td>" + gapData.get(i).getUniqueAttribute() + "</td>";
                    html = html + "<td>" + gapData.get(i).getIndigenousProportion() + "</td>";
                    html = html + "<td>" + gapData.get(i).getNonIndigenousProportion() + "</td>";
                    html = html + "<td>" + gapData.get(i).getGAP() + "</td>";
                    html = html + "</tr>";
            }
            html = html + "</table>";
        }else{
            html = html + "<h1>Out of rows or Gap not applicable</h1>";  
        }
        return html;
    } 
}
