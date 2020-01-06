$(document).ready(function() {
    // set correct badge on card
    let rounds = $('[id^=round_]');

    rounds.each(function() {
        let badge = 'complete';
        let id = $(this).attr('id').split('_').pop();
        let buttons = $(this).find(':submit');

        buttons.each(function() {
            if ($(this).attr('id').indexOf('save') >= 0) {
                badge = 'open';
            }
        });

        $('#' + badge + '_' + id).removeClass('no-show');
    });

    // open first card
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

function makeEditable(partId, scoreId) {
    let form = $('#form_' + partId);
    let action = form.attr('action') + '/delete/' + scoreId;
    form.attr('action', action);
    form.submit();
}