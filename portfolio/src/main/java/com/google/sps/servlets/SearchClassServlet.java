package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;

import com.google.sps.data.ClassData;
import com.google.gson.Gson;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/search-class")
public class SearchClassServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query =
            Query.newEntityQueryBuilder().setKind("Classes").setOrderBy(OrderBy.desc("year")).build();
        QueryResults<Entity> results = datastore.run(query);


        //test if any not required field is empty
        List<ClassData> classesResult = new ArrayList<ClassData>();
        while (results.hasNext()) {
            Entity entity = results.next();
      
            long id = entity.getKey().getId();
            String school = entity.getString("college");
            String department = entity.getString("department");
            String professor = entity.getString("professor");
            String semester = entity.getString("semester");
            String year = entity.getString("year");
            String className = entity.getString("className");

            ClassData c = new ClassData(school, department, professor,semester, year, className);
            classesResult.add(c);
        }

        String formsJson = convertToJsonByGson(classesResult);

        response.setContentType("application/json;");
        response.getWriter().println(formsJson);
    
    }

    /**
     * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
     * the Gson library dependency to pom.xml.
     */
    public String convertToJsonByGson(List<ClassData> classes){
        return new Gson().toJson(classes);
    }

}