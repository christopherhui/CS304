$(document).ready(function () {

    $('#search').click(() => {
        let name = $('#name').val();

        const submit = $.ajax({
            type: 'GET',
            url: `/applicant/name/${name}`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Year</th>`);
            $('#firs').append(`<th scope=\"col\">Major</th>`);
            $('#firs').append(`<th scope=\"col\">First Name</th>`);
            $('#firs').append(`<th scope=\"col\">Last Name</th>`);
            $('#firs').append(`<th scope=\"col\">Address</th>`);

            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i];
                if (curr['year'] !== -1) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['year']}</td>`);
                }
                if (curr['major'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['major']}</td>`);
                }
                if (curr['firstName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['firstName']}</td>`);
                }
                if (curr['lastName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['lastName']}</td>`);
                }
                if (curr['address'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['address']}</td>`);
                }
            }
        });
    });

    // applicantSelect
    $('#join').click(() => {
        let name = $('#app').val();

        const submit = $.ajax({
            type: 'GET',
            url: `/applicant/${name}`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Year</th>`);
            $('#firs').append(`<th scope=\"col\">Major</th>`);
            $('#firs').append(`<th scope=\"col\">First Name</th>`);
            $('#firs').append(`<th scope=\"col\">Last Name</th>`);
            $('#firs').append(`<th scope=\"col\">Address</th>`);
            $('#firs').append(`<th scope=\"col\">Company Name</th>`);
            $('#firs').append(`<th scope=\"col\">Platform Name</th>`);
            $('#firs').append(`<th scope=\"col\">SIN</th>`);
            $('#firs').append(`<th scope=\"col\">Application ID</th>`);

            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i]['first'];
                let uh = result[i]['second'];
                console.log(curr);
                if (curr['year'] !== -1) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['year']}</td>`);
                }
                if (curr['major'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['major']}</td>`);
                }
                if (curr['firstName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['firstName']}</td>`);
                }
                if (curr['lastName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['lastName']}</td>`);
                }
                if (curr['address'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['address']}</td>`);
                }
                if (uh['company_Name'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${uh['company_Name']}</td>`);
                }
                if (uh['platform_Name'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${uh['platform_Name']}</td>`);
                }
                if (uh['sin'] !== -1) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${uh['sin']}</td>`);
                }
                if (uh['app_ID'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${uh['app_ID']}</td>`);
                }
            }
        });
    });

    $('#aggregation').click(() => {
        let appl = $('#appl').val();

        const submit = $.ajax({
            type: 'GET',
            url: `/applications/count/${appl}`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Count</th>`);

            $('#secon').append(`<tr id=\"b${0}\"></tr>`);
            console.log(result);
            $(`#b${0}`).append(`<td>${result}</td>`);
        });
    });

    $('#aggregation').click(() => {
        let appl = $('#appl').val();

        const submit = $.ajax({
            type: 'GET',
            url: `/applications/count/${appl}`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Count</th>`);

            $('#secon').append(`<tr id=\"b${0}\"></tr>`);
            console.log(result);
            $(`#b${0}`).append(`<td>${result}</td>`);
        });
    });

    $('#nestedAggregation').click(() => {
        const submit = $.ajax({
            type: 'GET',
            url: `/company/advanced`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Hiring Amount</th>`);
            $('#firs').append(`<th scope=\"col\">Type</th>`);
            $('#firs').append(`<th scope=\"col\">Name</th>`);
            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i];
                if (curr['hiringAmt'] !== -1) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['hiringAmt']}</td>`);
                }
                if (curr['type'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['type']}</td>`);
                }
                if (curr['name'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['name']}</td>`);
                }
            }
        });
    });

    $('#division').click(() => {
        const submit = $.ajax({
            type: 'GET',
            url: `/applicant/all-submit`
        });

        submit.done(function (result) {
            $('#firs').empty();
            $('#secon').empty();
            $('#firs').append(`<th scope=\"col\">Year</th>`);
            $('#firs').append(`<th scope=\"col\">Major</th>`);
            $('#firs').append(`<th scope=\"col\">First Name</th>`);
            $('#firs').append(`<th scope=\"col\">Last Name</th>`);
            $('#firs').append(`<th scope=\"col\">Address</th>`);

            for (let i = 0; i < result.length; i++) {
                let done = false;
                let curr = result[i];
                if (curr['year'] !== -1) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['year']}</td>`);
                }
                if (curr['major'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['major']}</td>`);
                }
                if (curr['firstName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['firstName']}</td>`);
                }
                if (curr['lastName'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['lastName']}</td>`);
                }
                if (curr['address'] != null) {
                    if (!done) {
                        $('#secon').append(`<tr id=\"b${i}\"></tr>`);
                        done = true;
                    }
                    $(`#b${i}`).append(`<td>${curr['address']}</td>`);
                }
            }
        });
    });
});

