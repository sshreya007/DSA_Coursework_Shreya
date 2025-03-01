import java.util.concurrent.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCrawler {
    // Constants
    private static final int NUM_WORKERS = 11; // Number of worker threads
    private static final String STOP_SIGNAL = "STOP"; // Signal to stop workers

    // Shared counter for unique file names
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // Create directory to store crawled pages
        File directory = new File("crawled_pages");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Initialize the URL queue with seed URLs
        BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
        List<String> seedUrls = Arrays.asList(
            "http://example.com",
            "http://example.org",
            "http://example.net"
            // Add more URLs as needed
        );
        urlQueue.addAll(seedUrls);

        // Create a thread pool using ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS);

        // Submit worker tasks to process URLs
        for (int i = 0; i < NUM_WORKERS; i++) {
            executor.submit(() -> {
                try {
                    while (true) {
                        String url = urlQueue.take(); // Blocks until a URL is available
                        if (url.equals(STOP_SIGNAL)) {
                            break; // Exit when stop signal is received
                        }
                        crawl(url);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Add stop signals to terminate workers after URLs are processed
        for (int i = 0; i < NUM_WORKERS; i++) {
            urlQueue.put(STOP_SIGNAL);
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Crawling completed.");
    }

    /**
     * Crawls a single URL, fetches its content, and saves it to a file.
     * @param url The URL to crawl
     */
    private static void crawl(String url) {
        try {
            // Create URL object and open connection
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read the page content
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // Save content to a unique file
                String fileName = "crawled_pages/page" + counter.getAndIncrement() + ".html";
                FileWriter writer = new FileWriter(fileName);
                writer.write(content.toString());
                writer.close();

                System.out.println("Successfully crawled: " + url);
            } else {
                System.out.println("Failed to crawl " + url + ": HTTP " + responseCode);
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL " + url + ": " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error crawling " + url + ": " + e.getMessage());
        }
    }
}