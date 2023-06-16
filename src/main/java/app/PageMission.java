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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

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
                <link rel='stylesheet' href='homepage.css'>
                <link rel='stylesheet' href='mission.css'>
            </head>
            <body>
            
            
                <header id='top'>
                    <div class='left'>
                        <h1><a style="text-decoration:none" href='/'>CloseTheGap</a></h1>
                    </div>
                    <div class='rightLinks'>
                    <a href='/'>Homepage</a>
                    <a href='/mission.html'  class='linkActive' >Our Mission</a>
                    <a href='page3.html'>View Raw Data</a>
                    <a href='page4.html'>Focused View</a>
                    <a href='page5.html'>GAP score</a>
                    <a href='page6.html'>Similar LGAs</a>
                       
            
                    </div>
                    
                </header>
                
                <main>
                    <div class='imgContainer active'>
                        <img  class='homepageImage' src='missionpage.jpg'>
                        <div class='attentionGrabber'>
                            Because caring goes beyond actions <br>
                            <h2>- CloseTheGap</h2>
                        </div>
                        <p>Image by <a href='https://www.pexels.com/photo/sea-horizon-1089168/'>Nathan Cowley</a></p>
                    </div>
                    <div class='cardContainer'>
                        <img class="snake-bg" src="snake.png">
                        <div class='missionCardContainerTop'>
                            <div class='cardMission'>
                                <h1>Our Mission</h1>
                                <p>Our goal is to shed light into the actions taken towards closing the gap through data that has been collated and presented to you accurately and concisely.</p>
                            </div>
                            <div class='cardMission'>
                                <h1>About Us</h1>
                                <p>Ritish Kandel - S3947204 <br>
                                   Nikith Kaluwitharana - S3943409</p>
                            </div>                    
                        </div>
                        <div class='missionCardContainerMiddle'>
                            <div class='cardMission'>
                                <h1>What do we Provide?</h1>
                                <p>Our website houses tools that can help users research further into closing the gap through features that let the user compare databases or view datasets. References are also recorded for further diving if deemed necessary.</p>
                            </div>                  
                        </div>
                        <div class='missionCardContainerBottom'>
                        <div class='cardMission'>
                            <h1>Our Personas</h1>
                            <p>Have a look at our two key personas below who would thrive the most using our website over any other forms of information and why.</p>
                        </div>
                        <div class='cardMission'>
                            <h1>Who are we for?</h1>
                            <p>We are for the people who get misrepresented and for the people who deserve equality amongst the majority.</p>
                        </div>
                    </div>              
                </div>
            """;
            JDBCConnection jdbc = new JDBCConnection();
            ArrayList<personas> personas = jdbc.getPersonas();
            html = html + "<div class = 'personasContainer'>";
            for(int i = 0; i<personas.size(); i++){
            
                html = html + "<div class='persona'><h1>" + personas.get(i).getName() + "</h1>";
                html = html + "<img src='" + personas.get(i).getImgFilePath() + "'>";
             
                ArrayList<personaAttributes> personaAttributes = jdbc.getPersonaAttributes(personas.get(i).getName());
                for(int j = 0; j<personaAttributes.size(); j++){
                    html = html + "<p>" + personaAttributes.get(j).getAttributeType() + "</p>";
                    String[] multiLine = personaAttributes.get(j).getDesc().split("\\\\n");
                    html = html + "<ul>";
                    for(int k = 0; k<multiLine.length; k++){
                        html = html + "<li>" + multiLine[k] + "</li>";
                    }
                    
                    html = html + "</ul><br>";
                }
                html = html + "</div>";
            }
            html = html + "</div>";

            


            html = html +  """    
                </main>
                <div class='toTopContainer'>
                    <a href='#top' class='toTop'>↑</a>
                </div>
            </body>
            </html>
             """;
        context.html(html);
    }

}
