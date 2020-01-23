$(document).ready(function() {
    // set correct badge on card
    let rounds = $('[id^=round_]');

    rounds.each(function() {
        let badge = 'complete';
        let id = $(this).attr('id').split('_').pop();
        let buttons = $(this).find(':submit');
        let forms = $(this).find('form');

        forms.each(function () {
            let partId = $(this).attr('id').split('_').pop();
            let value = $('#status_' + partId).attr('value');
            if (value === undefined) {
                badge = 'open';
                return false;
            } else if (value === '0') {
                badge = 'incomplete';
                return false;
            }
        });

        // set show correct badge
        $('#' + badge + '_' + id).removeClass('hide');
    });

    // open first card
    let id = $('.card-body:first').attr('id').split('_').pop();
    displayRound(id);
});

function displayRound(id) {
    if ($('#roundBody_' + id).css('display') === 'none') {
        $('#roundBody_' + id).css('display', 'block');
        $('#dontShow_' + id).css('display', 'none');
        $('#show_' + id).css('display', 'block');
    } else {
        $('#roundBody_' + id).css('display', 'none');
        $('#dontShow_' + id).css('display', 'block');
        $('#show_' + id).css('display', 'none');
    }
}

function hideRound(id) {
    $('#roundBody_' + id).css('display', 'none');
    $('#dontShow_' + id).css('display', 'block');
    $('#show_' + id).css('display', 'none');
}

function save(partId) {
    const form = $('#form_' + partId);
    let action = form.attr('action');
    form.attr('action', action + '1');
    form.submit();
}

function hold(partId) {
    const form = $('#form_' + partId);
    let action = form.attr('action');
    form.attr('action', action + '0');
    form.submit()
}

function approve(partId) {
    $('.score_' + partId).each(function () {
        $(this).find('label').each(function () {
            let id = $(this).attr('id');
            id = '#i' + id.substring(1, id.length);
            $(id).val($(this).html());
        });
    });

    const form = $('#form_' + partId);
    form.attr('action', form.attr('action') + '1');
    form.submit();
}

function deleteScore(partId, scoreId) {
    let form = $('#form_' + partId);
    let action = form.attr('action');
    form.attr('action', action + 'delete/' + scoreId);
    form.submit()
}

function makeEditable(partId) {
    $('#hold_ctrl_' + partId).addClass('hide');
    $('#edit_ctrl_' + partId).addClass('hide');

    $('[id*="_ctrl_"]').each(function () {
        $(this).find('button').prop('disabled', true);
    });

    $('.score_' + partId).each(function () {
        $(this).find('label').each(function () {
            let id = $(this).attr('id');
            id = '#i' + id.substring(1, id.length);
            $(id).val($(this).html());
        });
        $(this).find('input').removeClass('hide');
        $(this).find('label').addClass('hide');
    });

    $('#save_ctrl_' + partId).removeClass('hide').find('button').prop('disabled', false);
}