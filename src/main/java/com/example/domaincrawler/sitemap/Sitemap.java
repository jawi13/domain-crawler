package com.example.domaincrawler.sitemap;

import com.example.domaincrawler.crawler.Crawler;
import com.example.domaincrawler.page.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Sitemap {

    public LinkedHashSet<Page> pagesList;
    private Crawler crawler = new Crawler();
    private FileWriter writer;

    public Sitemap() {
        pagesList = crawler.pagesList;
    }

    public void generateSitemapData(String URL) {
        System.out.println("Begin crawling domain for links...");
        crawler.crawlLinksOnPage(URL);
        System.out.println("... crawling completed.");
    }

    public void writeToFile(String filename) {
        System.out.println("Begin writing links to file...");
        try {
            writer = new FileWriter(filename);
            writer.write("Domain Sitemap\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Page p : pagesList) {
            try {
                writer.write("\nPage: " + p.getURL() + "\n\n");
                writer.write("Internal Links:\n\n");
                for (String link : p.getInternalLinks()) {
                    writeLinkToWriter(link);
                }
                writer.write("\nExternal Links:\n\n");
                for (String link : p.getExternalLinks()) {
                    writeLinkToWriter(link);
                }
                writer.write("\nImage Links:\n\n");
                for (String link : p.getImageLinks()) {
                    writeLinkToWriter(link);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("... writing links to file completed.");
    }

    private void writeLinkToWriter(String link) {
        try {
            writer.write(link + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
