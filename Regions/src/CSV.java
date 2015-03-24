import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
 
public class CSV {
     static String CodePath = "Kody.csv";
	public static void changeCodes(File file2){
    	CodePath = file2.getPath();
	}
    	public static String checkNumber(String num){
        CSVReader reader = null;
        try
        {
            //Get the CSVReader instance with specifying the delimiter to be used
        	//JOptionPane.showMessageDialog(null, "No Codes");
            reader = new CSVReader(new FileReader(CodePath),';');
            
            String dig3 = num.substring(1,4);
            int dig7 = Integer.parseInt(num.substring(4,11));
            String [] nextLine;
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                for(@SuppressWarnings("unused") String token : nextLine)
                {
                	
                	 if (dig3.equals(nextLine[0])){
                		 
                		if(dig7>Integer.parseInt(nextLine[1])) {
                	
                		if( dig7<Integer.parseInt(nextLine[2])){
                			return nextLine[5];
                		 //return nextLine[1]+"-"+nextLine[2]+" in "+nextLine[5];
                			
                		}}
                	 }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return "no";
    }}
