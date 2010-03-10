package dpi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import com.sun.syndication.io.FeedException;

public class RefreshClickHandler implements MouseListener {

	public void mouseClicked(MouseEvent e) {
		WebExtractionTool bbcNewsFeed = new WebExtractionTool("http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss.xml");
		WebExtractionTool polymerFeed = new WebExtractionTool("http://www3.interscience.wiley.com/rss/journal/36444");
		try {
			bbcNewsFeed.processFeed("/Training/NewsText/NewsTr");
			polymerFeed.processFeed("/Training/PolymerPapers/PolymerTr");
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (FeedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
