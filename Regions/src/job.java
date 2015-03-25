import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	final SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
	final SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
	int rowsQ;
	int rowsCur=0;
	File file1;
	CreationHelper createHelper;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	CellStyle hlink_style;
	FileInputStream file;
	Iterator<Row> rowIterator;
	Cell cell;
	Row row;
	
	public int getRowsQ(){
		return rowsQ;
	}
	public int getRowsCur(){
		return rowsCur;
	}
	
	public int updateBar(){
		return rowsCur;
	}
	
	public job(File file2) throws IOException{
		file1=file2;
		file = new FileInputStream(file2);
		workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        hlink_style = workbook.createCellStyle();
        Font hlink_font = workbook.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);
        createHelper = workbook.getCreationHelper();
        rowIterator = sheet.iterator();
        
        rowsQ=sheet.getPhysicalNumberOfRows();
        GUI.progressBar.setMaximum(rowsQ);
        System.out.println("Total rows:" + rowsQ);
        System.out.println("Total columns:" + sheet.getRow(0).getPhysicalNumberOfCells());
	}
	public boolean checkCellNull(Cell cell){
		cell = row.getCell(0);
		if(cell.getNumericCellValue()==0 || cell == null) return true;
		else return false;
				
	}
	
	public void makeOneRow(Row row) throws UnsupportedEncodingException, IOException{
		
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
        
        row.createCell(1).setCellValue("");
        row.createCell(7).setCellValue("");
        row.createCell(9).setCellValue("");
	}
	
	public void saveFile() throws IOException{
        file.close();
        String name = file1.getName().substring(0,file1.getName().lastIndexOf(".xlsx"))+"+регионы.xlsx";
        
        String absolutePath = file1.getAbsolutePath();
	    String filePath = absolutePath.
	    	     substring(0,absolutePath.lastIndexOf(File.separator));
	    
	    System.out.println(filePath+"\\"+name);
        FileOutputStream fileOut = new FileOutputStream(filePath+"\\"+name);
        workbook.write(fileOut);
        fileOut.close();
	}
	
	public void moveOneRow(){
        row = rowIterator.next();
	}

    	public void writeExcel () {
        try
        {
        	moveOneRow();
                if (!checkCellNull(cell)) makeOneRow(row);
                rowsCur++;
                GUI.progressBar.setSelection(rowsCur);

                System.out.println("");
            if (rowIterator.hasNext()) writeExcel();
            else saveFile();
        }
        
        
        //uselessbelow
        
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    	
    	
    	
    	
    	
        public static void main(String[] args) throws IOException {
        	job job = new job(new File("база демо 26-28.12.14 - Дима.xlsx"));
        }
    }
