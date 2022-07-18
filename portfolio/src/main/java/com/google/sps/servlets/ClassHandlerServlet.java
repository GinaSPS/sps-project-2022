package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/class-handler")
public class ClassHandlerServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sanitize user input to remove HTML tags and JavaScript.
    //String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String college = request.getParameter("college");
    String department = request.getParameter("department");
    String professor = request.getParameter("professor");
    String semester = request.getParameter("semester");
    String year = request.getParameter("year");
    String className = request.getParameter("className");
    String classLink = request.getParameter("classlink");

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Classes");
    FullEntity visitorEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("email", email)
            .set("college", college)
            .set("department", department)
            .set("professor", professor)
            .set("semester", semester)
            .set("year",year)
            .set("className", className)
            .set("classLink", classLink)
            .build();
    datastore.put(visitorEntity);

    response.sendRedirect("/addclass.html");
  }
}