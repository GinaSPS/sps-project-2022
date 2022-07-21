// preparing data for autocomplete
$.getJSON("./data/us_colleges.json", function(data){
    var colleges = [];
    $.each(data.Colleges, function(key, value){
        colleges.push(value.institution);
    });
    
    // autocomplete for school name input field with ID = schoolName
    $("#schoolName").autocomplete({
        source: colleges,
        scroll: true
    });
});


$.getJSON("./data/us_college_departments.json", function(data){
    var departments = [];
    $.each(data.Departments, function(key, value){
        departments.push(value.department);
    });
    
    // autocomplete for school name input field with ID = department
    $("#department").autocomplete({
        source: departments,
        scroll: true
    });
});


$("#classSemester").autocomplete({
    source: [
        "Spring",
        "Summer",
        "Fall",
        "Winter"
    ],
    scroll: true
});


var year = new Date().getFullYear();
var years = []
for(var i = year-10; i <= year; i++){
    years.push(i.toString());
}
$("#classYear").autocomplete({
    source: years,
    scroll: true
});

