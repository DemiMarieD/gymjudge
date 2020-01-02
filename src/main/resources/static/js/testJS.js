$(document).ready(function () {
    function again() {
        let id = location.href.split('/').pop();
        $.get('/update-scores/' + id, function (fragment) {
            $('#scoreTable').replaceWith(fragment);
        });
    }

    setInterval(again, 2000);
});

