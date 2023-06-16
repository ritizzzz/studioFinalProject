package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

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
                <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>

                <link rel='stylesheet' type='text/css' href='common.css' />
                <link rel='stylesheet' href='homepage.css'>
                

            </head>
            <body>
            
            
                <header id='top'>
                    <div class='left'>
                        <h1><a href='/'>CloseTheGap</a></h1>
                    </div>
                    <div class='rightLinks'>
                        <a href='/' class='linkActive'>Homepage</a>
                        <a href='/mission.html'>Our Mission</a>
                        <a href='page3.html'>View Raw Data</a>
                        <a href='page4.html'>Focused View</a>
                        <a href='page5.html'>GAP score</a>
                        <a href='page6.html'>Similar LGAs</a>
                       
            
                    </div>
                    
                </header>
                
                <main>
                <div class='toggle'>
                    <div class='leftSlide'><img src='chevron-down.svg' class='toggleButton'></div>
                    <div class='rightSlide'><img src='chevron-down.svg' class='toggleButton'></div>
                    <a href='#SEO'><div class='goDown'><img src='chevron-down.svg' class='toggleButton'></div></a>

                </div>
                        
                <div class='imgContainer active'>
                    <img  class='homepageImage' src='uluru.avif'>
                    <div class='attentionGrabber'>
                        Closing the gap is a strategy undertaken by the government to reduce the disadvantage among Aboriginal and Torres Strait Islander People in regards to the 17 socioeconomic outcomes shown below. 
                    </div>
                    <p> Photo by <a href='https://unsplash.com/@ondrejmachart?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText'>Ondrej Machart</a> on <a href='https://unsplash.com/photos/w5wF5co3OBw?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText'>Unsplash</a></p>
  
                </div>
            
                <div class='imgContainer'>
                    <img  class='homepageImage' src='koala.jpg'>
                    <div class='attentionGrabber'>
                        The objective of the national agreement is to enable the government and the aboriginal and torres islander people to work together to pass through policies that aid in closing the gap. 

                    </div>
                    <p>Photo by <a href='https://unsplash.com/@davidclode?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText'>David Clode</a> on <a href='https://unsplash.com/s/photos/aboriginal-art-australia?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText'>Unsplash</a></p>

                </div>

                <div class='imgContainer'>
                    <img  class='homepageImage' src='art.jpg'>
                    <div class='attentionGrabber'>
                        The implementation plan are the plans undertaken by different states and territories to aid in closing the gap. 
                    </div> 
                    <p>Photo by form <a href='https://pxhere.com/en/photo/954042'>PxHere</a> </p>
                </div>
                
                    <div class='socioeconomicOutcomes'>
                        <h2 id='SEO'>Socio-economic outcomes</h2>
                        <div class='cardContainer'>
            """;

            JDBCConnection jdbc = new JDBCConnection();
            ArrayList<SEO> outcomes = jdbc.getOutcomes();
            String[] coveredOutcomes = {"LTHC", "schoolCompletion", "weeklyIncome"};
            int a = 0;
            for(int i = 0; i<outcomes.size(); i++){
                if((i + 1) == 1 || (i + 1) == 5  || (i + 1) == 8){
                    html = html + "<div class='card coveredOutcomes'>";
                    html = html + "<details>";
                    html = html + "<summary>" + outcomes.get(i).getID() + ". " + outcomes.get(i).getTitle() + "</summary>" ;
                    html = html + "<p>"+ outcomes.get(i).getDescription() + "</p>";
                    html = html + "<a href=http://localhost:7001/page3.html?page=1&outcome=" + coveredOutcomes[a] +  "&columnDropdown=code&orderDropdown=ASC>View Data</a>" ;
                    html = html + "</details>";
                    html = html + "</div>";
                    a = a + 1;
                }else{
                    html = html + "<div class='card'>";
                    html = html + "<details>";
                    html = html + "<summary>" + outcomes.get(i).getID() + ". " + outcomes.get(i).getTitle() + "</summary>" ;
                    html = html + "<p>"+ outcomes.get(i).getDescription() + "</p>";
                    html = html + "</details>";
                    html = html + "</div>";
                }

            }


            html = html +  """            
                        </div>
                    </div>
                </main>
                <div class='toTopContainer'>
                    <a href='#top' class='toTop'>↑</a>
                </div>
                <script type='text/javascript' src='carousel.js'></script>
               </body>
            </html>
                """;
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
