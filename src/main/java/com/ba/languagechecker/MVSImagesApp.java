package com.ba.languagechecker;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class MVSImagesApp {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");
    
	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "~/tmp/1";
		
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.mvs.gov.ua/mvs/control/investigation/searchresults/wantedPerson?&SEX=%D0%A7&OVD=&page=300");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, 1);
	}

	private class MyCrawler extends WebCrawler {


	    /**
	     * This method receives two parameters. The first parameter is the page
	     * in which we have discovered this new url and the second parameter is
	     * the new url. You should implement this function to specify whether
	     * the given url should be crawled or not (based on your crawling logic).
	     * In this example, we are instructing the crawler to ignore urls that
	     * have css, js, git, ... extensions and to only accept urls that start
	     * with "http://www.ics.uci.edu/". In this case, we didn't need the
	     * referringPage parameter to make the decision.
	     */
	     @Override
	     public boolean shouldVisit(Page referringPage, WebURL url) {
	         String href = url.getURL().toLowerCase();
	         return !FILTERS.matcher(href).matches()
	                && href.startsWith("http://www.mvs.gov.ua/mvs/control/investigation/searchresults/wantedPerson");
	     }

	     /**
	      * This function is called when a page is fetched and ready
	      * to be processed by your program.
	      */
	     @Override
	     public void visit(Page page) {
	         String url = page.getWebURL().getURL();
	         System.out.println("URL: " + url);

	         if (page.getParseData() instanceof HtmlParseData) {
	             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	             Set<WebURL> links = htmlParseData.getOutgoingUrls();
	             for (WebURL link : links) {
	            	 if (link.getPath().endsWith("/1"))
	            	 System.out.println(link.getPath());
	             }
	         }
	    }
	}
}
