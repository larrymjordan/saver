package com.wawand.co.wallie;

import java.io.Console;
import java.io.IOException;

import org.apache.commons.validator.routines.EmailValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WallieAdvisor {

	private WebDriver driver;
	private WallieTask wallieTask;

	public WallieAdvisor(WallieTask wallieTask) {
	  this.wallieTask = wallieTask;
	  initializeChromeDriver();
	}

	private void initializeChromeDriver() {
	  System.setProperty("webdriver.chrome.driver", chromeDriverLocationPath());
	  driver = new ChromeDriver();
	}

	private String chromeDriverLocationPath() {
	  return WallieAdvisor.class.getResource(chromeDriverFileNamePathResolved()).getPath();
	}

	private String chromeDriverFileNamePathResolved() {
	  return String.format("chromedriver/%s", isWindowsOS() ? "chromedriver.exe" : "chromedriver");
	}

	private boolean isWindowsOS() {
	  String currentOS = System.getProperty("os.name").toLowerCase();
	  return currentOS.indexOf("win") != -1;
	}

	public void startLogWork() {
	  driver.get("http://wallie.wawand.co");
	  authenticate(wallieTask.getEmail(), wallieTask.getPassword());
	  logWork(
	    wallieTask.getComment(),
	    wallieTask.getTeam().toUpperCase(),
	    wallieTask.getHours()
	  );
	  logout();
	  driver.quit();
	}
	
	private void authenticate(String email, String password) {
	  enterEmail(email);
	  enterPassword(password);
	}
	
	private void enterEmail(String email) {
	  driver.findElement(By.name("email"))
	  .sendKeys(email);
	}

	private void enterPassword(String password) {
	  WebElement passwordElement = driver.findElement(By.name("password"));
	  passwordElement.sendKeys(password);
	  passwordElement.submit();
	}

	private void logWork(String comment, String team, String hours) {
	  openLogWorkPopup();
	  enterComment(comment);
	  selectTeam(team);
	  selectTimeSpent(hours);
	  cancel();
	}

	private void openLogWorkPopup() {
	  driver.findElement(By.className("show-worklog-text-wrapper"))
	  .click();
	}

	private void enterComment(String comment) {
	  driver.findElement(By.name("worklog[comment]"))
	  .sendKeys(comment);
	}

	private void selectTeam(String team) {
	  selectOn("worklog_project_chosen", team);
	}
	
	private void selectTimeSpent(String hours) {
	  selectOn("worklog_hours_chosen", hours);
	}
	
	private void selectOn(String id, String option) {
	  WebElement wrapee = driver.findElement(By.id(id));
	  SelectElement element = new SelectElement(wrapee);
	  element.setValue(option);
	}
	
	private void cancel(){
	  driver.findElement(By.cssSelector("#new_worklog > div.modal-footer > div"))
	  .click();
	}
	
	private void logout(){
	  driver.findElement(By.className("dropdown-wrapper"))
	  .click();
	  
	  driver.findElement(By.xpath("//*[@id=\"fat-menu\"]/ul/li[3]/a"))
	  .click();
	}

	public static void main(String... args) throws IOException {
	  WallieAdvisor advisor = new WallieAdvisor(wallieTaskAsked());
	  advisor.startLogWork();
	}

	private static WallieTask wallieTaskAsked() {
	  WallieTask wallieTask = new WallieTask();
	  Console console = System.console();
		  
	  // Ask what's your email.
	  while(wallieTask.getEmail().isEmpty() || 
	        !EmailValidator.getInstance().isValid(wallieTask.getEmail())){
	    System.out.print("Enter your email: ");
	    wallieTask.setEmail(console.readLine());
	  }
		  
	  // Ask what's your password.
	  while(wallieTask.getPassword().isEmpty()){
	    System.out.print("Enter your password: ");
	    wallieTask.setPassword(new String(console.readPassword()));
	  }
		  
	  // Ask the comment you want to publish.
	  while(wallieTask.getComment().isEmpty()) {
	    System.out.print("Enter what you did: ");
	    wallieTask.setComment(console.readLine());
	  }
		  
	  // Ask the team you belong to.
	  while(wallieTask.getTeam().isEmpty()){
	    System.out.print("Enter your team: ");
	    wallieTask.setTeam(console.readLine());
	  }
		  
	  System.out.print("Enter the time spent: ");
	  String hours = console.readLine();
	  wallieTask.setHours(hours.isEmpty()? "8h" : hours);
	  
	  return wallieTask;
	}
}
