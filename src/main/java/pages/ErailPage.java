package pages;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.WebDriverWait;

import Base.Baseclass;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class ErailPage extends Baseclass{
	
	public String textat4thPos;
	private WebDriver driver;
	
	
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	
	By from = By.xpath("//input[@id='txtStationFrom']");
	By elementAt4thPosition = By.xpath("//div[@class='autocomplete']/child::div[4]");
	By dropDownOptions = By.xpath("(//div[@class='autocomplete'])/div");
	By selectDateButton = By.cssSelector("input[title*='for availability']");
	
	
	public ErailPage(WebDriver driver) {
		this.driver = driver;}
	
	public void openUrl(String url) {
		driver.get(url);
		}
	
	
	public List<String> selectFromStation(String StationCode) throws InterruptedException {
		
		 List<String> stationCodesGetText = new ArrayList<String>();
		 List<WebElement> stationCodes = new ArrayList<WebElement>();
		
		 Thread.sleep(1000);
		 
		driver.findElement(from).clear();
		
		driver.findElement(from).sendKeys(StationCode);
		try {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(dropDownOptions)));
		Thread.sleep(1000);
		stationCodes = driver.findElements(By.xpath("//div[@class='autocomplete']/div"));
		for(int i=0;i<stationCodes.size();i++) {
			stationCodesGetText.add(stationCodes.get(i).getText());
		}
		textat4thPos = wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementAt4thPosition))).getText();
		driver.findElement(elementAt4thPosition).click();
		System.out.println("Station code at 4th position is : "+textat4thPos);
	}
		catch(Exception e)
		{
			System.out.println("exception "+e);
		}
		
		Assert.assertEquals(textat4thPos,"Delhi Azadpur\n"
				+ "DAZ");
		return stationCodesGetText;
	}
	//read the data from existing excel
	//store the data into new excel
	//compare both the data 
	
	
	public List<String> readDataFromExcel(String filePath, String sheetName) throws IOException {
	    List<String> dataFromExcel = new ArrayList<>();
	    
	    try (FileInputStream fis = new FileInputStream(filePath);
	    		XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
	        XSSFSheet sheet = workbook.getSheet(sheetName);
	 
	        
	        
	        if (sheet == null) {
	            throw new IllegalArgumentException("Sheet with name " + sheetName + " does not exist in the Excel file.");
	        }

	        for (Row row : sheet) {
	            Cell cell = row.getCell(0);
	            if (cell != null && cell.getCellType() == CellType.STRING) {
	                dataFromExcel.add(cell.getStringCellValue());
	            }
	        }
	    }
	    return dataFromExcel;
	}
	
	public void writeDataToExcel(List<String> stationCodes) throws IOException {	
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		int rowNum = 0;
		
        for (String item :stationCodes) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0); // Create a cell in the first column
            cell.setCellValue(item);
        }
      
        String filePath = "/Users/aakash/eclipse-workspace/rapi/DropDownStationCodes.xlsx";
		
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);  // Write the workbook content to the file
            System.out.println("Data written successfully to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();  // Close the workbook to release resources
        }
        
	}
	
	public void selectTheDateFromCalendar() throws InterruptedException {
		String month="";String dd="";
		By nextMonthSelection = By.xpath("//td[text()='"+month+"']/parent::tr/following-sibling::tr//td[text()='"+dd+"']");
		try {
		Thread.sleep(2000);
		driver.findElement(selectDateButton).click();
		WebElement dateField = driver.findElement(selectDateButton);
		String value = dateField.getAttribute("value");
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy EEE");
        
        // Parse the string into a LocalDate
        LocalDate date = LocalDate.parse(value, formatter);
        
        // Add 30 days
        LocalDate newDate = date.plusDays(30);
        
        // Convert the new date back to string
        String newDateString = newDate.format(formatter);
        
		 month = newDateString.substring(3,9);
		 dd = newDateString.substring(0,2);
		 nextMonthSelection = By.xpath("//td[text()='"+month+"']/parent::tr/following-sibling::tr//td[text()='"+dd+"']");
		
		 Thread.sleep(1000);
		 wait.until(ExpectedConditions.visibilityOf(driver.findElement(nextMonthSelection))).click();
		}
		catch(Exception e) {
	
	
}
}
}
