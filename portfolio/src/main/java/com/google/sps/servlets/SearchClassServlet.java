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
          Query.newEntityQueryBuilder().setKind("Classes").build();
      QueryResults<Entity> results = datastore.run(query);
      
        // key = the field, value = query user searched for
        //HashMap<String, String> myParameters = new HashMap<>();

    //get the filter option from user
    // try{
    // String schoolName = request.getParameter("schoolName");
    // myParameters.put("college",schoolName);
    // }catch (Exception e) {
    // // school name wasn’t provided. Ignore from the query
    
    // }   

    // try{
    //     String department = request.getParameter("department");
    //     myParameters.put("department",department);
    // }catch (Exception e) {
    
    // }

    // try{
    //     String professor = request.getParameter("professor");
    //     myParameters.put("professor",professor);
    // }catch (Exception e){

    // }

    // try{
    //     String semester = request.getParameter("classSemester");
    //     myParameters.put("semester",semester);
    // }catch (Exception e){

    // }

    // try{
    //     String className = request.getParameter("className");
    //     myParameters.put("className",className);
    // }catch (Exception e){

    // }

    // try{
    //     String classYear = request.getParameter("classYear");
    //     myParameters.put("year",classYear);
    // }catch (Exception e){

   // }

   //get the filter option from user
   String schoolName = request.getParameter("schoolName");
   String department = request.getParameter("department");
   String professor = request.getParameter("professor");
   String semester = request.getParameter("classSemester");
   String className = request.getParameter("className");
   String classYear = request.getParameter("classYear");

   //create a new classData
   ClassData userInputData = new ClassData(schoolName, department, professor, semester, classYear, className);
    
      //test if any not required field is empty
      
      List<ClassData> classesResult = filterResult(results, userInputData);
      if (classesResult.size() == 0){
        response.getWriter().println("empty result");
      }
      else{
        String formsJson = convertToJsonByGson(classesResult);
    
        response.setContentType("application/json;");
        response.getWriter().println(formsJson);
      }
      
      
    }

    public List filterResult(QueryResults<Entity> results, ClassData usrInputData ){
      List<ClassData> classes = new ArrayList<ClassData>();
      while (results.hasNext()) {
        Entity entity = results.next();
  
        long id = entity.getKey().getId();
        String school = entity.getString("college");
        String department = entity.getString("department");
        String professor = entity.getString("professor");
        String semester = entity.getString("semester");
        String year = entity.getString("year");
        String className = entity.getString("className");
        

        if (usrInputData.schoolName.equals(school) ){
            
          if (usrInputData.department.equals(department)){
            
            if (usrInputData.professor.equals(professor)){
               
              if(usrInputData.semester.equals(semester)){
             
                if (usrInputData.className.equals(className) ){
                   
                  if (usrInputData.classYear.equals(year)){
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