let sin = document.getElementById("sinField");
let year = document.getElementById("yearField");
let major = document.getElementById("majorField");
let fname = document.getElementById("fnfield");
let lname = document.getElementById("lnfield");
let address = document.getElementById("addressfield");

$('#addButton').click(() => {
    const submit = $.ajax({
        type: 'POST',
        url: '/applicant/create/sin/year/major/fname/lname/address'
    });

    submit.done(function (result) {
        if (result === "Success") {
            //           $('#text-bubble').text(`Successfully added in applicant with name ${fname} ${lname}!`);
            alert("Successfully added in applicant with name ${fname} ${lname}!");
        }
        else {
            $('#text-bubble').text("Unsuccessful");
        }
    });

    // submit.fail(){
    //     $('#text-bubble').text("Unsuccessful");
    // }
});

for (let res in result) {
    let sin = res['sin']; 
    let year = res['year'];
    let major = res['major'];
    let fname = res['fname'];
    let lname = res['lname'];
    let address = res['address'];
};
