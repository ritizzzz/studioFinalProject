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
public class PageST22 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page4.html";

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
                <link rel='stylesheet' href='task2B.css'>

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
                        <a href='page4.html' class='linkActive'>Focused View</a>
                        <a href='page5.html'>GAP score</a>
                        <a href='page6.html'>Similar LGAs</a>

                       
            
                    </div>
                    
                </header>
                <main>
            """;
               
        if((context.formParam("year") == null)){
            html = html + "<h1>Please select from both options before you submit</h1>";
        }else{

            String yearStr = context.formParam("year");
            int year = Integer.parseInt(yearStr);

            JDBCConnection jdbc = new JDBCConnection();
            ArrayList<LGA> lgas = jdbc.getLGAs(year);
            html = html + "<div class='bottomLeftContainer'>";
            html = html + "<h1>Year selected: " + year +"</h1>";
            html = html + "<form action='page4.html' method='POST'>";
            html = html + "<input hidden value= "+ year + " name='year'>";
            html = html + "<label for='lgaOrState'><h1>Search by LGA or State?</h1></label>";
            html = html + "<select id='lgaOrState' name='lgaOrState'>";
                html = html + "<option value='LGA'>LGA</option>'";
                html = html + "<option value='STATE'>State</option>'";
                html = html + "</select>";
                html = html + "<br>";
                html = html + "<input type='submit'>";
                if(context.formParam("lgaOrState").equals("LGA")){
                    html = html + "<label for='lgaSelect'><h1>Select LGA:</h1></label>";
                    html = html + "<select id='lgaSelect' name='lgaSelect'>";
                        for(int i = 0; i<lgas.size(); i++){
                            html = html + "<option value='" + lgas.get(i).getCode()  + "'>" + lgas.get(i).getName() + "</option>";
                        }
                        html = html + "</select>";
                        html = html + "<input type='submit'></form>";
                    }
                else if(context.formParam("lgaOrState").equals("State")){
                    html = html + "<label for='stateSelect'><h1>Select State:</h1></label>";
                    html = html + "<select id='stateSelect' name='stateSelect'>";
                        for(int i = 0; i<lgas.size(); i++){
                            html = html + "<option value='" + lgas.get(i).getCode()  + "'>" + lgas.get(i).getName() + "</option>";
                        }
                        html = html + "</select>";
                        html = html + "<input type='submit'></form>";
                    }
            html = html + "<br>";
            html = html + "<br>";
            html = html + "</div>";
            html = html + "<br>";
            if(context.formParam("lgaSelect") != null && (context.formParam("stateSelect") != null)){
                html = html + "<h1>Please select from only one field</h1>";
            }
            String lga = context.formParam("lgaSelect");
            if(lga != null){
                html = html + outputTargetLGA(Integer.parseInt(lga), year);
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
}
