## Domain Crawler

### Build/Test/Run

The program uses Maven to build, test and run. After cloning the repository and with Maven installed, this can be done by running the following commands from the root directory of the project:

* **Build**- ./mvnw install
* **Test**- ./mvnw test
* **Run**- ./mvnw spring-boot:run

### Reasoning

* The program was created with Spring Initializr and is written in Java 8. The jsoup library is used for parsing HTML from webpages the crawler visits. There are numerous alternative options for libraries which can be used for the same function in Java and other languages, but after research I felt jsoup was best for my requirement.
* The program consists of just a few classes due its simplicity and limited scope:
  
* Each object created from the **Page** class represents a crawled page with its URL, and has separate lists for its internal, external and image links. Originally I was going to use a LinkedMap and sort the links elsewhere, but creating a separate class makes the code easier to read and modify.
* The **Crawler** class takes a domain URL and visits it, gathering the links from the page by finding all 'a' elements and extracting the 'href' attribute value. If a link belongs to the same domain, the recursive function allows the crawler to collect the links from that page until it has crawled through all linked pages in the domain. It creates a Page object for every internal page it crawls. External links are added to the list of links, but are not crawled themselves. Image links are extracted in the same way where an 'img' element is used, extracting the 'src' attribute value. 
* In other cases, the image URL was contained within the 'style' attribute on 'a' and 'div' elements, and extracting this required more complexity. The **ImageFromStyleAttrHelper** class took the URL from the style attribute value through String manipulation and fed this back to the Crawler class. I also had to account for inconsistencies in the spacing of the style attributes which caused some image links to either miss a letter off the start or the end. This workaround works well for this program, but there is a trade off in that it would likely not work correctly if used on another domain, and a more elegant solution would be required to make it scalalbe.
* The **Sitemap** class generates the data for the Sitemap by calling the Crawler class' crawlLinksOnPage function. It then takes the list of page objects and for each object, it writes the page URL and the list of internal, external and image links to a FileWriter. The use of HashSets ensures there is no duplicated links. The FileWriter is then saved to a chosen filename.
* The **DomainCrawlerApplication** class contains the main method where the program is run from. It calls the Sitemap's generateSitemapData method passing the domain URL, and then calls its writeToFile method with the filename.

### With More Time I Would...

* **Improve Testing**- The current test coverage is very limited, it checks that the outputted data meets certain base requirements which I feel ensures the program works, but it lacks granularity which comes with unit testing each  class' methods. More thorough test coverage would make it easier to diagnose issues if something weren't working.
* **Improve Output**- Currently, the list of links for each object are outputted into a sitemap.txt file and are divided into internal, external and image links. A better output would be an XML sitemap or a HTML file with clickable links.
* **Further refactoring**- While I put a lot of effort into refactoring, there are a couple of methods that are still quite large and could probably be broken down further to improve readability of the code. 
* **Caching**- Currently the 'sitemap.txt' is populated from scratch every time the program is run, implementing caching would improve performance by preventing repeated runs from extracting the same data and rewriting it to the file.