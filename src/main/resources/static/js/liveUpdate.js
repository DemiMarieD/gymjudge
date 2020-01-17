$(document).ready(function () {
    let stop = false;
    function again() {
        let id = location.href.split('/').pop();
        if (!isNaN(id)) {
            $.get('/livescores/update-scores/' + id, function (fragment) {
                $('#scoring').replaceWith(fragment);
            });
        } else {
            console.log('return');
            stop = true;
        }
    }

    if (!stop) {
        setInterval(again, 2000);
    }
});