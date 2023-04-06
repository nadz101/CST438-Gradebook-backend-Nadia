package com.cst438;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class EndtoEndTestNewAssignment {

	public static final String CHROME_DRIVER_FILE_LOCATION = "/usr/local/bin/chromedriver";
	public static final String URL = "http://localhost:3000";

	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final String TEST_ASSIGNMENT_NAME = "Test Assignment";
    public static final String TEST_COURSE_ID = "99999";
	public static final int COURSE_ID = 99999;
    public static final String TEST_DUE_DATE = "2023-03-04";
	public static final int SLEEP_DURATION = 1000; // 1 second.

	@Autowired
	AssignmentRepository assignmentRepository;

	@Autowired
	CourseRepository courseRepository;
	
	

	@Test
	public void newAssignment() throws Exception {
//setting up driver
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);	 
	
		try {
			//"new Assignment"
	driver.findElement(By.xpath("//a[span='New Assignment']")).click();
			//locate input element for course id, due date and assignemnt name
	driver.findElement(By.xpath("//input[@name='courseId']")).sendKeys(TEST_COURSE_ID);
	driver.findElement(By.xpath("//input[@name='assignmentName']")).sendKeys(TEST_ASSIGNMENT_NAME);
	driver.findElement(By.xpath("//input[@name='dueDate']")).sendKeys(TEST_DUE_DATE);
	
	//submit
	driver.findElement(By.xpath("//input[@value='Submit']")).click();

	
}catch (Exception ex) {
    throw ex;
} finally {
    driver.close();
driver.quit();

}

}
}

