/**
 * Async function loads the class data from the DataStore
 *      and display data to the table
 */
async function loadSearchClassData(){
    const serverResponse = await fetch('/search-class');
    const serverResponseJson = await serverResponse.json();
    const formDataContainer = document.getElementById('class-data-display-container');

    // use JSON library to print the string of the response
    //   formDataContainer.innerText = JSON.stringify(serverResponseJson);
    //   console.log(serverResponseJson);
    for(var i = 0; i < serverResponseJson.length; i++){
        // only limit table to 9 elements first
        if(i == 10){
            break;
        }

        // add a row to the table
        var row = formDataContainer.insertRow(-1);
        var obj = serverResponseJson[i];

        // traverse the json arrays, extract each class info and display to table
        var college = row.insertCell(0);
        college.innerHTML = obj.schoolName;
        var department = row.insertCell(1);
        department.innerHTML = obj.department;
        var classinfo = row.insertCell(2);
        classinfo.innerHTML = obj.className;
        var semester = row.insertCell(3);
        semester.innerHTML = obj.semester + " " + obj.classYear;
        var prof = row.insertCell(4);
        prof.innerHTML = obj.professor;
        // add a button to the end of each table row
        var button = row.insertCell(5);
        // not adding hyperlink yet, will add once implemented
        button.innerHTML = "<button>ADD CLASS</button>"
    }

}




/**
 * form function that filters the table by input
 */

function filter(){
    // element from form inputs
    const inputs = [];
    inputs[0] = document.getElementById("schoolName").value.trim().toLowerCase();
    document.getElementById("schoolName").value = "";

    inputs[1] = document.getElementById("department").value.trim().toLowerCase();
    document.getElementById("department").value = "";

    inputs[2] = document.getElementById("className").value.trim().toLowerCase();
    document.getElementById("className").value = "";

    var semester = document.getElementById("classSemester").value.trim().toLowerCase();
    document.getElementById("classSemester").value = "";

    var year = document.getElementById("classYear").value.trim().toLowerCase();
    document.getElementById("classYear").value = "";

    inputs[3] = (semester + " " + year).trim().toLowerCase();
    
    inputs[4] = document.getElementById("professor").value.trim().toLowerCase();
    document.getElementById("professor").value = "";
    console.log(inputs);

    // element from table
    var table = document.getElementById('class-data-display-container');
    var tr = table.getElementsByTagName("tr");
    for(var i = 0; i < tr.length; i++){
        tr[i].style.display = "";
        const td = tr[i].getElementsByTagName("td");
        for(var j = 0; j < td.length; j++){
            // base case to skip checking button and if input is empty
            if(j == 5 || inputs[j] === ""){
                continue;
            }
            const cellValue = td[j].innerHTML.trim().toLowerCase();
            const input = inputs[j];
            if(cellValue !== input){
                tr[i].style.display = "none";
                break;
            }
        }
    }
}   


