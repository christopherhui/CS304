const sin = $('#sin');

<input id="sin"> </input>

let sin = 1;
let year = 3;

$('#new-app').click(() => {
    const submit = $.ajax({
        type: 'POST',
        url: '/applicant/create/sin/{year}/{major}/{fname}/{lname}/{address}'
    });

    submit.done(function (result) {
        if (result === "Success") {
            $('#text-bubble').text(`Successfully added in application with name ${fname} and ${lname}!`);
        }
        else {
            $('#text-bubble').text("Unsuccessful");
        }
    });

    submit.fail() {
        $('#text-bubble').text("Unsuccessful");
    }
});

for (let res : result) {
    let jobNo = res['jobNo']; // 1000
    let jobTitle = res['jobTitle'];
    let description = res['description'];
}