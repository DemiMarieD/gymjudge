$(document).ready(function () {
    function again() {
        $.get("update-competitions", function (fragment) {
            $('#table').replaceWith(fragment);
        });
    }

    setInterval(again, 3000);
});