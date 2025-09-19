// 代码生成时间: 2025-09-19 23:13:25
package com.example.webcrawler.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
# 改进用户体验
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestController
public class WebPageCrawlerController {

    private final RestTemplate restTemplate;

    @Value("{webcrawler.url}")
    private String defaultUrl; // Replace with the actual URL from application.yml or application.properties

    public WebPageCrawlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the content of the specified URL and extracts the title.
# 改进用户体验
     * 
     * @param url The URL of the webpage to fetch.
# TODO: 优化性能
     * @return ResponseEntity with the title of the webpage.
     */
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchWebPage(@RequestParam String url) {
        try {
            String html = restTemplate.getForObject(url, String.class);
            Document doc = Jsoup.parse(html);
            Elements title = doc.select("title");
            return ResponseEntity.ok(title.text());
        } catch (Exception e) {
            // Error handling
            return ResponseEntity.badRequest().body("Error fetching webpage: " + e.getMessage());
        }
    }
# 优化算法效率

    /**
     * Fetches the content of the default URL and extracts all links.
# 扩展功能模块
     * This method is scheduled to run periodically using @Scheduled.
     */
    @Scheduled(fixedRateString = "{webcrawler.cron.expression}")
# 优化算法效率
    public void fetchAndStoreLinks() {
        try {
            String html = restTemplate.getForObject(defaultUrl, String.class);
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a[href]");
            List<String> hrefs = links.stream()
# 优化算法效率
                .map(link -> link.absUrl("href"))
                .collect(Collectors.toList());

            // Store the links or process them further
            hrefs.forEach(System.out::println);
        } catch (Exception e) {
            // Error handling for scheduled task
            System.err.println("Error fetching and storing links: " + e.getMessage());
        }
# 添加错误处理
    }
}
