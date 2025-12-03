(function(window, document, undefined) {
  window.onload = init;

  function init(){
    var loadedStudents = JSON.parse(localStorage.getItem('students'));
    showTable(loadedStudents);
  }

})(window, document, undefined);



function save(){
    var nev = document.getElementById("nev").value;
    var evf = document.getElementById("evf").value;
    var szak = document.getElementById("szak").value;
    var email = document.getElementById("email").value;
    var neptun = document.getElementById("neptun").value;    

    var student = {
        "nev": nev,
        "evf": evf,
        "szak": szak,
        "email": email,
        "neptun": neptun
    }

    var input = JSON.parse(localStorage.getItem('students'));

    var out = {
        "students": []
    };
    out.students = input.students;
    out.students.push(student);
    localStorage.setItem('students', JSON.stringify(out));

    console.log(JSON.parse(localStorage.getItem('students')));
}

function showTable(students){
    var table = document.getElementById("table");
    for(var i = 0; i < students.students.length; i++){
        var newRow = `
        <tr>
            <td>${students.students[i].nev}</td>
            <td>${students.students[i].evf}</td>
            <td>${students.students[i].szak}</td>
            <td>${students.students[i].email}</td>
            <td>${students.students[i].neptun}</td>
        </tr>`;

        table.innerHTML += newRow;
    }
}
