package RegressionSuite;

/*
 Author : Ponmathi Masanam
 Created Date: 22-July-2020
 Test Description: Validate below functionalities of Webstaurant ecommerce website
 						*Keyword Search 
 						*Page Navigation & Add to Cart (Add Last Found Cart Item)
 						*Empty Cart 						
 */

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SearchandEmptyCart {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
				
		//Launch Webstaurant website
		System.setProperty("webdriver.chrome.driver", "PATH OF CHROMEDRIVER EXE FILE TO BE PROVIDED");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.webstaurantstore.com/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);

		//Perform Search
		driver.findElement(By.id("searchval")).sendKeys("stainless work table");
		driver.findElement((By.xpath("//button[@value='Search']"))).click();;	
		
		//Peform Page Navigation
		boolean hasNextPage=true;
        while(hasNextPage)
        {
        	//Scroll to the bottom of the page
        	((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        	//Click Next Icon
        	List <WebElement> NextPageIcon = driver.findElements(By.xpath("//li[@class='disabled']/span/i[@class='icon-right-open']"));
        	//Loop through the page navigation until last page
        	if(NextPageIcon.size()==0) {        	
        		List<WebElement> ItemsList = driver.findElements(By.xpath("//a[@data-testid='itemDescription']"));
        			for(WebElement ItemName : ItemsList) {
        			//Validate the Search Result contains the String "Table"
					if(!ItemName.getText().contains("Table")) {
					    	System.out.println("Fail"); 
					    	String PageNumber = driver.findElement(By.xpath("//li[@class='active']/span")).getText();
					    	System.out.println("Irrelevant Search Item Found in Page: "+PageNumber+" listed as "+ItemName.getText()); 
					  }
        		}
        	   //Click Next Page Icon
        	   driver.findElement(By.className("icon-right-open")).click();          
            }else {
           		 List <WebElement> DisabledNextIcon = driver.findElements(By.xpath("//li[@class='disabled']/span/i[@class='icon-right-open']"));
           		 	if(DisabledNextIcon.size()==1) {
           		 		//Get the total pages handled
           		 		String LastPage = driver.findElement(By.xpath("//li[@class='active']/span")).getText();
           		 		System.out.println("Total Pages Handled: "+LastPage);
           		 		//Make the Boolean flag False to stop the page navigation
           		 		hasNextPage=false;
           		 		}
            }
        
        }
        
        //To get the last item found in the search
        List<WebElement> ItemsRetrieved = driver.findElements(By.xpath("//a[@data-testid='itemDescription']"));
    	String LastItem = ItemsRetrieved.get(ItemsRetrieved.size()-1).getText().trim();
        //Perform Add to Cart
        List <WebElement> addtocart = driver.findElements(By.xpath("//input[@name='addToCartButton']"));
   		addtocart.get(addtocart.size()-1).click();
        //Scroll to the top of Page
   		((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 0)");
        //Click View Cart
   		WebElement element =driver.findElement(By.xpath("//a[contains(text(),'View Cart')]"));			
   		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
   		//Checkpoint to validate the item added in cart
		String AddedItem=driver.findElement(By.xpath("//a[contains(@title,'Table')]")).getText().trim();
		if(LastItem.equalsIgnoreCase(AddedItem)) {
			System.out.println("Validation of Cart Item Description is Successful");	
		}else {
			System.out.println("Item added to the cart doesn't match with View Cart Item");
		}
		//Click Empty Cart	
        driver.findElement(By.xpath("//a[contains(text(),'Empty Cart')]")).click();
        //Confirm Empty Cart submission
        WebElement EmptyCartAccept =driver.findElement(By.xpath("//button[contains(text(),'Empty Cart')]"));			
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", EmptyCartAccept);
		//Validation of Empty Cart
		Boolean EmptyCart =driver.findElement(By.xpath("//a[contains(text(),'Continue Shopping')]")).isDisplayed();
		if(EmptyCart.TRUE) {
			System.out.println("Empty Cart performed successfully");	
		}else {
			System.out.println("Cart is not empty");
		}
		driver.close();		
	}

}


