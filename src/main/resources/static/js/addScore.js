$(document).ready(function() {
    let id = $('.card-body:first').attr('id').split('_').pop();
    displayRound(id);
});

function displayRound(id) {
    $('#roundBody_' + id).css('display', 'block');
    $('#dontShow_' + id).css('display', 'none');
    $('#show_' + id).css('display', 'block');
}

function hideRound(id) {
    $('#roundBody_' + id).css('display', 'none');
    $('#dontShow_' + id).css('display', 'block');
    $('#show_' + id).css('display', 'none');
}