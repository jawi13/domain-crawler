package com.example.domaincrawler;

import com.example.domaincrawler.page.Page;
import com.example.domaincrawler.sitemap.Sitemap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DomainCrawlerApplicationTests {

	public Sitemap sitemap;

	@BeforeAll
	public void init() {
		sitemap = new Sitemap();
		sitemap.generateSitemapData("https://wiprodigital.com");
	}

	@Test
	public void pagesListShouldNotBeEmpty() {
		Assertions.assertFalse(sitemap.pagesList.isEmpty());
	}

	@Test
	public void linkListsForPagesShouldNotBeEmpty() {
		for (Page page : sitemap.pagesList) {
			Assertions.assertFalse(page.internalLinks.isEmpty());
			Assertions.assertFalse(page.externalLinks.isEmpty());
			Assertions.assertFalse(page.imageLinks.isEmpty());
		}
	}

	@Test
	public void internalLinksShouldBeginWithDomainName() {
		for (Page page : sitemap.pagesList) {
			page.internalLinks.forEach(link -> {
				Assertions.assertTrue(link.startsWith("https://wiprodigital.com"));
			});
		}
	}

	@Test
	public void externalLinksShouldBeLinkButNotBeginWithDomainName() {
		for (Page page : sitemap.pagesList) {
			page.externalLinks.forEach(link -> {
				Assertions.assertTrue(link.startsWith("http") || link.startsWith("mailto:"));
				Assertions.assertFalse(link.startsWith("https://wiprodigital.com"));
			});
		}
	}

	@Test
	public void imageLinksShouldBeLinkAndEndWithImageExtension() {
		String jpg = "jpg";
		String jpeg = "jpeg";
		String png = "png";
		String gif = "gif";
		String svg = "svg";
		for (Page page : sitemap.pagesList) {
			page.imageLinks.forEach(link -> {
				Assertions.assertTrue(link.startsWith("http"));
				Assertions.assertTrue(link.endsWith(jpg) || link.endsWith(jpeg) || link.endsWith(png) || link.endsWith(gif) || link.endsWith(svg));
			});
		}
	}
}
