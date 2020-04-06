// const sin = $('#SIN');
// const year = $('#Year');
// const major = $('#Major');
// const fname = $('#fn');
// const lname = $('#ln');
// const address = $('#address');

// <input id="sin"> </input>

$(document).ready(function () {

    $('#addButton').click(() => {
        let sin = $("#sinField").val();
        let year = $("#yearField").val();
        let major = $("#majorField").val();
        let fname = $("#fnField").val();
        let lname = $("#lnField").val();
        let address = $("#addressField").val();

        const submit = $.ajax({
            type: 'POST',
            url: `/applicant/create/${sin}/${year}/${major}/${fname}/${lname}/${address}`
        });

        submit.done(function (result) {
            if (result === "Success") {
                //           $('#text-bubble').text(`Successfully added in applicant with name ${fname} ${lname}!`);
                alert(`Successfully added in applicant!`);
            }
            else {
                alert(`That person already exists!`);
                // $('#text-bubble').text("Unsuccessful");
            }
        });
    });

    $('#delete').click(() => {
        let sin = $("#sinF").val();

        const submit = $.ajax({
            type: 'DELETE',
            url: `/applicant/delete-applicant/${sin}`
        });

        submit.done(function (result) {
            if (result === "Success") {
                //           $('#text-bubble').text(`Successfully added in applicant with name ${fname} ${lname}!`);
                alert(`Successfully deleted applicant with SIN ${sin}`);
            }
            else {
                alert(`This person does not exist!`);
                // $('#text-bubble').text("Unsuccessful");
            }
        });
    });

    $('#update').click(() => {
        let sin = $("#sin").val();
        let year = $("#year").val();
        let major = $("#major").val();

        const submit = $.ajax({
            type: 'PUT',
            url: `/applicant/update/${sin}/${major}/${year}`
        });

        submit.done(function (result) {
            if (result === "Success") {
                //           $('#text-bubble').text(`Successfully added in applicant with name ${fname} ${lname}!`);
                alert(`Successfully updated applicant with ${sin}, ${major}, ${year}`);
            }
            else {
                alert(`This person does not exist!`);
                // $('#text-bubble').text("Unsuccessful");
            }
        });
    });

    $('#project').click(() => {
        let first = "no";
        let second = "no";
        let third = "no";
        $('#sel1 > option').each(function() {
            if (this.selected) {
                if (this.value === "Company Name") {
                    first = "COMPANY_NAME";
                }
                if (this.value === "Hiring Amount") {
                    second = "HIRINGAMT";
                }
                if (this.value === "Type") {
                    third = "TYPE";
                }
                console.log(this.value);
            }
        });
        const submit = $.ajax({
            type: 'GET',
            url: `/company/${first}/${second}/${third}`
        });

        submit.done(function (result) {
            $('#first').empty();
            $('#second').empty();
            if (first !== "no") {
                $('#first').append(`<th scope=\"col\">${first}</th>`);
            }
            if (second !== "no") {
                $('#first').append(`<th scope=\"col\">${second}</th>`);
            }
            if (third !== "no") {
                $('#first').append(`<th scope=\"col\">${third}</th>`);
            }
            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i];
                if (curr['name'] != null) {
                    if (!done) {
                        console.log('added');
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['name']}</td>`);
                }
                if (curr['type'] != null) {
                    if (!done) {
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['type']}</td>`);
                }
                if (curr['hiringAmt'] !== -1) {
                    if (!done) {
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['hiringAmt']}</td>`);
                }
            }
        });
    });

    $('#select').click(() => {
        let status = 0;
        let filter = $('#selectit');

        $('#exampleFormControlSelect1 > option').each(function() {
            if (this.selected) {
                if (this.value === "Find by Description") {
                    status = 0;
                }
                if (this.value === "Find by Company Name") {
                    status = 1;
                }
                if (this.value === "Find by Job Title") {
                    status = 2;
                }
            }
        });
        const submit = $.ajax({
            type: 'GET',
            url: `/company/description/${filter}/${status}`
        });

        submit.done(function (result) {
            $('#first').empty();
            $('#second').empty();
            if (first !== "no") {
                $('#first').append(`<th scope=\"col\">${first}</th>`);
            }
            if (second !== "no") {
                $('#first').append(`<th scope=\"col\">${second}</th>`);
            }
            if (third !== "no") {
                $('#first').append(`<th scope=\"col\">${third}</th>`);
            }
            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i];
                if (curr['name'] != null) {
                    if (!done) {
                        console.log('added');
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['name']}</td>`);
                }
                if (curr['type'] != null) {
                    if (!done) {
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['type']}</td>`);
                }
                if (curr['hiringAmt'] !== -1) {
                    if (!done) {
                        $('#second').append(`<tr id=\"a${i}\"></tr>`);
                        done = true;
                    }
                    $(`#a${i}`).append(`<td>${curr['hiringAmt']}</td>`);
                }
            }
        });
    });
});

