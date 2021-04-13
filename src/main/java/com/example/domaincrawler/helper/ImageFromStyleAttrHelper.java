package com.example.domaincrawler.helper;

import org.jsoup.nodes.Element;

import java.util.LinkedHashSet;

public class ImageFromStyleAttrHelper {

    public void addImageUrlFromStyleAttr(Element element, LinkedHashSet<String> imageLinks) {
        String styleAttr = element.attr("style");
        if (!styleAttr.isEmpty() && styleAttr.contains("url(")) {
            String imageUrl = getImageUrlFromStyleAttr(styleAttr);
            if (!imageUrl.isEmpty()) imageLinks.add(imageUrl);
        }
    }

    private String getImageUrlFromStyleAttr(String styleAttr) {
        String imageUrl = styleAttr.substring(styleAttr.indexOf("url(") + 5, styleAttr.lastIndexOf(")") - 1);
        if (imageUrl.startsWith("t")) imageUrl = "h" + imageUrl;
        if (!imageUrl.endsWith("g") && !imageUrl.isEmpty()) imageUrl = imageUrl + "g";
        return imageUrl;
    }
}
