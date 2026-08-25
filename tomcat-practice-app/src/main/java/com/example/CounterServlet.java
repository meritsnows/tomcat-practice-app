package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A minimal servlet that counts visits in memory.
 * The counter resets whenever Tomcat restarts or the app is redeployed,
 * which is actually useful here: it's a quick visual way to confirm
 * that a fresh deploy really happened.
 */
@WebServlet("/count")
public class CounterServlet extends HttpServlet {

    private final AtomicInteger visits = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentCount = visits.incrementAndGet();

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Visit Counter</title></head><body>");
            out.println("<h1>Visitor Counter</h1>");
            out.println("<p>This page has been visited <strong>" + currentCount + "</strong> time(s) since Tomcat last started or this app was redeployed.</p>");
            out.println("<p><a href=\"index.jsp\">Back to home</a></p>");
            out.println("</body></html>");
        }
    }
}
