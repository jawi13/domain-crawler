package com.example.domaincrawler;

import com.example.domaincrawler.sitemap.Sitemap;

public class DomainCrawlerApplication {

    public static void main(String[] args) {
        Sitemap sitemap = new Sitemap();
        sitemap.generateSitemapData("https://wiprodigital.com");
        sitemap.writeToFile("sitemap.txt");
    }
}
