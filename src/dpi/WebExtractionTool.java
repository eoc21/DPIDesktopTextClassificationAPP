package dpi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


/**
 * Class that uses the webHarvester jar to extract content from the bbc news.
 * @author ed
 *
 */
public class WebExtractionTool {
	private String rssFeedURL;
	
	public WebExtractionTool(String feedURI){
		this.rssFeedURL = feedURI;
	}
	
	public void processFeed(String outputDirectory) throws IllegalArgumentException, FeedException, IOException{
        URL feedSource = new URL(rssFeedURL);	
        String currentdir = System.getProperty("user.dir");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        int counter=0;
        for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
	        SyndEntry entry = (SyndEntry) i.next();
	        String fileName = currentdir+outputDirectory+Integer.toString(counter)+".txt";
	        File file = new File(fileName);
	        String title = entry.getTitle();
	        String content = entry.getDescription().getValue();
	        FileUtils.writeStringToFile(file, title);
	        FileUtils.writeStringToFile(file, content);
	        counter++;
        }
	}	
}
