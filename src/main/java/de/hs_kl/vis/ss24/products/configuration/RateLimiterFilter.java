package de.hs_kl.vis.ss24.products.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimiterFilter implements Filter {

    private final Map<String, RequestCounter> requestCounters = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 3; // Maximum requests per window
    private final long WINDOW_SIZE = TimeUnit.MINUTES.toMillis(1); // Window size in milliseconds

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        RequestCounter counter = requestCounters.getOrDefault(clientIp, new RequestCounter(0, currentTime));

        synchronized (counter) {
            if (currentTime - counter.startTime >= WINDOW_SIZE) {
                counter.startTime = currentTime;
                counter.count = 0;
            }

            if (counter.count >= MAX_REQUESTS) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Too many requests");
                return;
            } else {
                counter.count++;
                requestCounters.put(clientIp, counter);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    private static class RequestCounter {
        int count;
        long startTime;

        RequestCounter(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}
