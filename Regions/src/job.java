import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class job{
	int rowsQ;
	int rowsCur=0;
	
	public int getRowsQ(){
		return rowsQ;
	}
	public int getRowsCur(){
		return rowsCur;
	}
	
    public static void main(String[] args) {
    	job job = new job();
    	job.writeExcel(new File("база демо 26-28.12.14 - Дима.xlsx"));
    }
    	public void writeExcel (File file1) {
        try
        {
        	SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        	SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
            FileInputStream file = new FileInputStream(file1);
 
            //Create Workbook instance holding reference to .xlsx file
            @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            CellStyle hlink_style = workbook.createCellStyle();
            Font hlink_font = workbook.createFont();
            hlink_font.setUnderline(Font.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            CreationHelper createHelper = workbook.getCreationHelper();
            
            
            rowsQ=sheet.getPhysicalNumberOfRows();
            
            System.out.println("Total rows:" + rowsQ);
            System.out.println("Total columns:" + sheet.getRow(0).getPhysicalNumberOfCells());
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
 
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                rowsCur++;
                //For each row, iterate through all the columns
                Cell cell = row.getCell(0);
                if (cell.getNumericCellValue()==0 || cell == null){
                	break;
                }
               
                

                
                cell=row.getCell(3); //ip to 14
                row.createCell(12).setCellValue(cell.getStringCellValue());
     
                cell=row.getCell(7); //time
                if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
                	Cell cell1= row.getCell(2);
                	row.createCell(3).setCellValue("[["+format2.format(cell1.getDateCellValue())+"]]");
               }
               else {
               	row.createCell(3).setCellValue(cell.getStringCellValue().substring(11,19));
               }
                cell=row.getCell(1); //date
                row.createCell(2).setCellValue(format1.format(cell.getDateCellValue()));
                
                /*cell= row.getCell(7);
                if(cell.getCellType()==cell.CELL_TYPE_BLANK){
                	 row.createCell(10).setCellValue("БА");
                 	System.out.println("Opening:");
                }
                else {
                	row.createCell(10).setCellValue("");
                }*/
                cell= row.getCell(6);
                if(cell.getCellType()==Cell.CELL_TYPE_STRING) row.createCell(13).setCellValue(cell.getStringCellValue());
                
                cell= row.getCell(5);
                if(cell.getCellType()==Cell.CELL_TYPE_STRING) row.createCell(8).setCellValue(cell.getStringCellValue());
                else row.createCell(8).setCellValue("");
                
                
                cell = row.getCell(0);
                String value = new DecimalFormat("#.#######################").format(cell.getNumericCellValue());
                System.out.print("["+value+"]");
              String checkedNum = CSV.checkNumber(value);
             //   System.out.print("["+CSV.checkNumber(value)+"]");
                row.createCell(5).setCellValue(checkedNum);
                row.createCell(6).setCellValue(Utils.timeZone(checkedNum));
              //  cell= row.getCell(6);
              //  cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell = row.createCell(11);
                cell.setCellValue(value);
                Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);

                link.setAddress(Utils.numbers(value));
                cell.setHyperlink(link);
                cell.setCellStyle(hlink_style);
                
                
                cell = row.createCell(14);
                cell.setCellValue("CallHistory");
                Hyperlink link1 = createHelper.createHyperlink(Hyperlink.LINK_URL);
                

                link1.setAddress(Utils.callHistory(value));
                cell.setHyperlink(link1);
                cell.setCellStyle(hlink_style);
                
                Map<String, String> hashmap = Utils.googleNumber(value);
                int ii=0;
                for (Map.Entry<String, String> entry: hashmap.entrySet()){
                
                cell = row.createCell(15+ii);
                cell.setCellValue(entry.getKey());
                Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_URL);
                
                link2.setAddress(entry.getValue());
                cell.setHyperlink(link2);
                cell.setCellStyle(hlink_style);
                ii++;
                }
                
                
                
                row.createCell(9).setCellValue("");
                row.createCell(7).setCellValue("");
                row.createCell(1).setCellValue("");
                

                
        /*        cell = row.getCell(1);       
                System.out.print("["+format1.format(cell.getDateCellValue())+"]");
                cell = row.getCell(2);       
                System.out.print("["+format2.format(cell.getDateCellValue())+"]");*/
                
 /*               for (int i=3;i<11;i++){
                cell = row.getCell(i);       
                switch (cell.getCellType())
                {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print("["+cell.getNumericCellValue() + "] ");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print("["+cell.getStringCellValue() + "] ");
                        break;
                }
                
                
                
                
                
            }*/
                
                
                System.out.println("");
                
            }
            file.close();
            String name = file1.getName().substring(0,file1.getName().lastIndexOf(".xlsx"))+"+регионы.xlsx";
            System.out.println(name);
            FileOutputStream fileOut = new FileOutputStream(name);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    }
