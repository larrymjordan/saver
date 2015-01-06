package com.wawand.co.wallie;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SelectElement {
	
  private WebElement wrapee;
	
  public SelectElement(WebElement wrapee){
    this.wrapee = wrapee;
  }
  
  public void setValue(String value) {
    click();
    selectOn(nominees(), value);
  }
  
  private void click(){
    wrapee.findElement(By.className("chosen-single"))
    .click();
  }
  
  private List<WebElement> nominees() {
    return wrapee.findElement(By.className("chosen-drop"))
				 .findElement(By.className("chosen-results"))
				 .findElements(By.tagName("li"));
  }
  
  private void selectOn(List<WebElement> nominees, String name) {
    for (Iterator<WebElement> i = nominees.iterator(); i.hasNext();) {
	  WebElement nominee = i.next();
	  if (nominee.getText().indexOf(name) != -1) {
	    nominee.click();
		break;
	  }
	}
  }
}
