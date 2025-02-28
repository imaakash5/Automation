package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.Baseclass;
import pages.ErailPage;
@Test
public class ErailTest extends Baseclass{
	ErailPage Erail = new ErailPage(driver);
	public List<String>stationCodes = new ArrayList<String>();	
	public List<String> dataFromFile1 = new ArrayList<String>();
	
	
	public void testErailStationSelection() throws InterruptedException {
		test = extent.createTest("Erail Station Selection Test");
		ErailPage Erail = new ErailPage(driver);
		Erail.openUrl("https://erail.in/");
		try {
			stationCodes=Erail.selectFromStation("Del");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(Erail.textat4thPos, "Delhi Azadpur\n"
				+ "DAZ");
		System.out.println("Test case passed");
		Erail.selectTheDateFromCalendar();
	}
	
	
	public void testcompareTwoExcels() throws IOException {
		String filePath = "/Users/aakash/eclipse-workspace/rapi/";
		
		dataFromFile1 = Erail.readDataFromExcel(filePath
				+ "Stationcodes.xlsx","Sheet1");
		
		Erail.writeDataToExcel(this.stationCodes);
		test = extent.createTest("Erail write data to excel");
		for(String data1: dataFromFile1 ) {
			if(this.stationCodes.contains(data1)) {
				System.out.println("Match found");
			}
			else
			{
				System.out.println("Match not found");
			}
		}
	}
	
	public void testSelectTheDateFromCalendar() throws InterruptedException {
		Erail.selectTheDateFromCalendar();
	}
	
	
	
	
}
