package com.example.domaincrawler.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter
@Setter
@NoArgsConstructor
public class Page {

    public String URL;
    public LinkedHashSet<String> internalLinks;
    public LinkedHashSet<String> externalLinks;
    public LinkedHashSet<String> imageLinks;
}
