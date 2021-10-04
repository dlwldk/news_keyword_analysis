function alert_button(str) {
    alert(str);
}

function jAjax(divID, uri) {
    $.ajax({
        type: GET,
        async: true,
        url: uri,
        success: function(response) {
            console.dir(response);
            //$('#'+divID).html(response);
        }
    })
}