package com.example.jspdemo;

import com.example.jspdemo.model.AnimalBean;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "1", value = "/1")
public class MainServlet extends HttpServlet {
    AnimalBean dog;

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("init");
        if (dog == null) {

            dog = new AnimalBean(12, "dog");
            request.setAttribute("animalBean", dog);
        }

        response.sendRedirect("./123");
    }

    public void destroy() {
    }
}