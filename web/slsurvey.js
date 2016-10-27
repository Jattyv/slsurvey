$(document).ready(function () {
    $("#ssubmit").click(function () {
        var x = document.getElementsByClassName("answer");
        var address = window.location.origin;
        var survey = $.ajax({
            url: address + "/slsurvey/survey/surveyform",
            type: "GET",
            success: function (data) {
                var res = survey.responseJSON;
                var pointer = 0;
                for (var i = 0; i < res.groups.length; i++) {
                    for (var i2 = 0; i2 < res.groups[i].questions.length; i2++) {
                        res.groups[i].questions[i2].answer = x[pointer].options[x[pointer].selectedIndex].value;
                        pointer += 1;
                    }
                }
                $.ajax({
                    url: address + "/slsurvey/survey/answered",
                    type: 'PUT',
                    data: JSON.stringify(res),
                    success: function (result) {
                        window.alert("Successfully submited.");
                    }
                });
            }
        });


    });
});