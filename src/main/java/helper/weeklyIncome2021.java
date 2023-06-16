package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Closing-the-Gap data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statments!
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au

 */
public class weeklyIncome2021 {

   // MODIFY these to load/store to/from the correct locations
   
   private static final String DATABASE = "jdbc:sqlite:database/ctg.db";
   private static final String CSV_FILE = "database/lga_total_household_income_weekly_by_indigenous_status_of_household_2021.csv";


   public static void main (String[] args) {
      

      // // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category[] = {
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
      String status[] = {
         "Indigenous households",
         "Other Households",
         "Total Households"
      };
    

      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // // These indicies track which column we are up to
            int indexStatus = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();
            int year = 2021;


            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into weeklyIncome VALUES ("
                              + lgaCode + ","
                              + "'" + year + "',"
                              + "'" + status[indexStatus] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count + ")";

               // Execute the INSERT
               statement.execute(query);
               System.out.println("Executing: " + query);

               indexStatus++;
               if(indexStatus >= status.length){
                  indexStatus = 0;
                  indexCategory++;
               }

               row++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      
    }
}
