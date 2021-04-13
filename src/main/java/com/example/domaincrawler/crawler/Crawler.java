package com.example.domaincrawler.crawler;

import com.example.domaincrawler.helper.ImageFromStyleAttrHelper;
import com.example.domaincrawler.page.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Crawler {

    public LinkedHashSet<Page> pagesList;
    private final HashSet<String> crawledLinks;

    private final ImageFromStyleAttrHelper imageFromStyleAttrHelper = new ImageFromStyleAttrHelper();

    public Crawler() {
        pagesList = new LinkedHashSet<>();
        crawledLinks = new HashSet<>();
    }

    public void crawlLinksOnPage(String URL) {
        if (!crawledLinks.contains(URL)) {
            crawledLinks.add(URL);
            Page page = new Page();
            page.setURL(URL);
            try {
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                LinkedHashSet<String> internalLinks = new LinkedHashSet<>();
                LinkedHashSet<String> externalLinks = new LinkedHashSet<>();
                LinkedHashSet<String> imageLinks = new LinkedHashSet<>();
                for (Element link : linksOnPage) {
                    String linkUrl = link.attr("abs:href");
                    if (isLinkInternal(linkUrl)) {
                        internalLinks.add(linkUrl);
                        page.setInternalLinks(internalLinks);
                    } else {
                        externalLinks.add(linkUrl);
                        page.setExternalLinks(externalLinks);
                    }
                    addImageLinks(document, imageLinks);
                    page.setImageLinks(imageLinks);
                    pagesList.add(page);
                    if (isLinkInternal(linkUrl) && !linkUrl.contains("#") && !linkUrl.contains("[]")) {
                        crawlLinksOnPage(linkUrl);
                    }
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    private boolean isLinkInternal(String linkUrl) {
        return linkUrl.startsWith("https://wiprodigital.com");
    }

    private void addImageLinks(Document document, LinkedHashSet<String> imageLinks) {
        addImageLinksFromCrawledLinks(document, imageLinks);
        addImageLinksFromDivs(document, imageLinks);
        addImageLinksFromImgs(document, imageLinks);
    }

    private void addImageLinksFromCrawledLinks(Document document, LinkedHashSet<String> imageLinks) {
        Elements linksOnPage = document.select("a[href]");
        for (Element link : linksOnPage) {
            imageFromStyleAttrHelper.addImageUrlFromStyleAttr(link, imageLinks);
        }
    }

    private void addImageLinksFromDivs(Document document, LinkedHashSet<String> imageLinks) {
        Elements divsOnPage = document.select("div[style]");
        for (Element div : divsOnPage) {
            imageFromStyleAttrHelper.addImageUrlFromStyleAttr(div, imageLinks);
        }
    }

    private void addImageLinksFromImgs(Document document, LinkedHashSet<String> imageLinks) {
        Elements imagesOnPage = document.select("img[src]");
        for (Element image : imagesOnPage) {
            String srcAttr = image.attr("src");
            if (!srcAttr.isEmpty()) imageLinks.add(srcAttr);
        }
    }
}
