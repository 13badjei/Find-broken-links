package day31;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {

	public static void main(String[] args) throws IOException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
		driver.get("http://www.deadlinkcity.com/");
		driver.manage().window().maximize();
		
		List<WebElement> links=driver.findElements(By.tagName("a"));
		System.out.println("Total number of links:"+links.size());
		
		int brokenlinks = 0;
		
		for(WebElement linkEle:links)
		{
			String hrefAttValue=linkEle.getAttribute("href");
			
				
			if(hrefAttValue==null || hrefAttValue.isEmpty())
			{
				System.out.println("href atttribute value is empty.");
				continue;
			}
			
			URL linkurl=new URL(hrefAttValue); // convert String --> URL format
			
			//send request to server - open , connect
			HttpURLConnection conn=(HttpURLConnection) linkurl.openConnection();
			
			conn.connect();
			
			if(conn.getResponseCode()>=400)
			{
				System.out.println(hrefAttValue+"       "+"====> Broken Link");
				brokenlinks++;
			}
			else
			{
				System.out.println(hrefAttValue+"       "+"====> Not Broken Link");
			}
			
			
		}
		
		System.out.println("total number of broken links:"+brokenlinks);
		
	}

}
