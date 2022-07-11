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
import java.util.List;

@WebServlet("/search-class")
public class SearchClassServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
      Query<Entity> query =
          Query.newEntityQueryBuilder().setKind("Class").setOrderBy(OrderBy.desc("timestamp")).build();
      QueryResults<Entity> results = datastore.run(query);
      
      //get the filter option from user
      String schoolName = request.getParameter("schoolName");
      String department = request.getParameter("department");
      String professor = request.getParameter("professor");
      String semester = request.getParameter("classSemester");
      String className = request.getParameter("className");
      Long classYear = Long.valueOf(request.getParameter("classYear"));

      //create a new classData
      ClassData userInputData = new ClassData(schoolName, department, professor, semester, classYear, className);
      
      //TODO: test if any not required field is empty
      
      List<ClassData> classesResult = filterResult(results, userInputData);
      
      String formsJson = convertToJsonByGson(classesResult);
  
      response.setContentType("application/json;");
      response.getWriter().println(formsJson);
      
    }

    public List filterResult(QueryResults<Entity> results, ClassData usrInputData ){
      List<ClassData> classes = new ArrayList<ClassData>();
      while (results.hasNext()) {
        Entity entity = results.next();
  
        long id = entity.getKey().getId();
        String school = entity.getString("schoolName");
        String department = entity.getString("department");
        String professor = entity.getString("professor");
        String semester = entity.getString("classSemester");
        String className = entity.getString("className");
        Long year = entity.getLong("classYear");

        if (usrInputData.schoolName == school){
          if (usrInputData.department == department){
            if (usrInputData.professor == professor){
              if(usrInputData.semester == semester){
                if (usrInputData.className == className){
                  if (usrInputData.classYear == year){
                    ClassData c = new ClassData(school, department, professor,semester, year, className);
                    classes.add(c);
                  }
                }
              }
            }
          }
        }
  
        
      }
      
      return classes;
    }

    /**
     * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
     * the Gson library dependency to pom.xml.
     */
    public String convertToJsonByGson(List<ClassData> classes){
        return new Gson().toJson(classes);
    }

}