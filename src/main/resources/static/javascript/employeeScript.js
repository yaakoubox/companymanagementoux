//
// 	fetch("http://localhost:8080/principal/username", {credentials: 'include', method: 'get'}).then(
// 		function (body) {
// 			return body.text();
// 		}).then(
// 		function (data) {
// 			localStorage.setItem("principalUsername", data);
// 		});
// 		document.getElementById("principalUsername").innerHTML = localStorage.principalUsername;
// // }


//////////////////////////////////////////////////////////////////////////////////////////////////
function populate() {
	var selectedItem = document.getElementById("phoneNumber");
	var displaytext = selectedItem.options[selectedItem.selectedIndex].text;
	document.getElementById("selectedPhone").value = displaytext;
	document.getElementById("selectedPhoneHidden").value = displaytext;
};

//////////////////////////////////////////////////////////////////////////////////////////////////

//if(localStorage.myDepartments===undefined){
// fetch("http://localhost:5000/department/list/projects").then(
fetch("http://companymanagement-env.eba-guu9cedh.us-east-2.elasticbeanstalk.com/department/list/projects").then(
	function(resp){
		return resp.json();
	}).then(
	function(data){
		localStorage.setItem("myDepartments",JSON.stringify(data));
	});
//}

let departments = JSON.parse(localStorage.getItem("myDepartments"));

////////////////////////////////////////////////////////////////////////////////////////////////

// fetch("http://localhost:5000/employee/list/projects").then(
	fetch("http://companymanagement-env.eba-guu9cedh.us-east-2.elasticbeanstalk.com/employee/list/projects").then(
	function(resp){
		return resp.json();
	}).then(
	function(data){
		localStorage.setItem("myProjects",JSON.stringify(data));
	});

let projects = JSON.parse(localStorage.getItem("myProjects"));


////////////////////////////////////////////////////////////////////////////////////////////////

var departmentsName = [] ;

for (const depName in departments) {
	departmentsName.push(depName);
	}

////////////////////////////////////////////////////////////////////////////////////////////////

var myDepartmentNames = document.getElementById("departmentsFromLocal"); 
var empId = document.getElementById("empId").value;

////////////////////////////////////////////////////////////////////////////////////////////////

var departmentNameOnChange = document.getElementById("departmentsFromServer")
departmentNameOnChange.addEventListener("change", function(e){
	var myProjectNamesTemp = [];
	var selectedDepartmentName = departmentNameOnChange.options[departmentNameOnChange.selectedIndex].text;
	
	for(var i=0 ; i<departmentsName.length ; i++){
		
		if(departmentsName[i] == selectedDepartmentName){
			myProjectNamesTemp = []
			myProjectNamesTemp = Object.values(departments)[i];	
			break;
		}	
		
	}	
	var div = document.getElementById("projectNameFromLocal");
	
	while(div.firstChild) div.removeChild(div.firstChild);
	
	for (var i=0 , len=myProjectNamesTemp.length; i<len; i++) {
		var projectName = myProjectNamesTemp[i];
		var label = document.createElement("label");
		label.for = i;
		label.className="projectOtions";
		var input = document.createElement("INPUT");
		input.type = "checkbox";
		input.value = projectName;
				
		if(empId != 0 && projects[empId].includes(projectName)){
			console.log(projects[empId])
			input.checked="checked";
		}
		
		input.name = "workOnListAsString";
		input.id = i;
		label.appendChild(input);
		var textNode = document.createTextNode(projectName);
		label.appendChild(textNode);
		div.appendChild(label);
	}
	
},false);

////////////////////////////////////////////////////////////////////////////////////////////////

if(empId!=0){
	var myProjectNamesTemp = [];
	var selectedDepartmentName = departmentNameOnChange.options[departmentNameOnChange.selectedIndex].text;
	
	for(var i=0 ; i<departmentsName.length ; i++){
		
		if(departmentsName[i] == selectedDepartmentName){
			myProjectNamesTemp = []
			myProjectNamesTemp = Object.values(departments)[i];	
			break;
		}	
		
	}	
	var div = document.getElementById("projectNameFromLocal");
	
	while(div.firstChild) div.removeChild(div.firstChild);
	
	for (var i=0 , len=myProjectNamesTemp.length; i<len; i++) {
		var projectName = myProjectNamesTemp[i];
		var label = document.createElement("label");
		label.for = i;
		var input = document.createElement("INPUT");
		input.type = "checkbox";
		input.value = projectName;
				
		if(empId != 0 && projects[empId].includes(projectName)){
			console.log(projects[empId])
			input.checked="checked";
		}
		
		input.name = "workOnListAsString";
		input.id = i;
		label.appendChild(input);
		var textNode = document.createTextNode(projectName);
		label.appendChild(textNode);
		div.appendChild(label);
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////



var expanded = false;
function showProjectsName() {
	console.log("//////////////////////")
  var dropdown = document.getElementById("dropDownCheckBoxsId");
  var divCheckboxes = document.getElementById("projectNameFromLocal");
  if (!expanded) {
	  console.log("++++++++++++++++++")
	dropdown.style.display = "block";
	divCheckboxes.style.display = "block";
    expanded = true;
  } else {
	  console.log("*******************")
    dropdown.style.display = "inline-block";
    divCheckboxes.style.display = "none";
    expanded = false;
  }
	console.log("=====================")
}

document.addEventListener("mouseup",function (e){
	var dropdown = document.getElementById("dropDownCheckBoxsId");
	var divCheckboxes = document.getElementById("projectNameFromLocal");
	if(expanded){
		dropdown.style.display = "inline-block";
		divCheckboxes.style.display = "none";
		expanded=false;
	}
});
console.log("expanded = "+expanded)

////////////////////////////////////////////////////////////////////////////////////////////////



function addProjectToLocalStorage(){
	var departmentName = document.getElementById("selectedDepartment");
	var location = document.getElementById("location");
	var projectName = document.getElementById("projectName");
	
	var isDepartmentNameOk = departmentName.value.length>0
	var isLocationOk = (location.value.trim().length>=2) && (location.value.trim().length<=60)
	var isProjectNameOK = projectName.value.trim().length>=2 && projectName.value.trim().length<=30 && (/^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+/).test(projectName.value);
	
	if(isDepartmentNameOk && isLocationOk && isProjectNameOK){
		
		var departmentSelectedName = departmentName.options[departmentName.selectedIndex].text;
		var projectNameForAdd = projectName.value;
		
		var departmentFromLocalStorageForUpdate = JSON.parse(localStorage.getItem("myDepartments"));
												  
		
		departmentFromLocalStorageForUpdate[departmentSelectedName].push(projectNameForAdd);
		
		localStorage.setItem("myDepartments", JSON.stringify(departmentFromLocalStorageForUpdate)); 
		
		
	}else{
		
	}


//var projectNameEroors = document.getElementById("projectNameEroors");
//var projectNameExisting = document.getElementById("projectNameExisting");
//var locationEroors = document.getElementById("locationEroors");
//var departmentErrors = document.getElementById("departmentErrors");
//if((projectNameEroors===null)&&(projectNameExisting===null)&&(locationEroors===null)&&(departmentErrors===null)){
//	console.log("///////////////////////////////////////////////////////////")	
//}else{
//	console.log("===========================================================")
//}
	
}

////////////////////////////////////////////////////////////////////////////////////////////////




