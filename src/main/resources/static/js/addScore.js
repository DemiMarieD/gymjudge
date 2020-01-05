function updateUI(id) {
    $('#form_' + id).submit();
    $(document).find('.score_' + id).each(function () {
        $(this).find('input').attr('disabled', true);
    });

    $('#save_' + id).get(0).type = 'hidden';
    $('#edit_' + id).get(0).type = 'button';
}

function makeEditable(id) {
    $(document).find('.score_' + id).each(function () {
        $(this).find('input').attr('disabled', false);
    });

    $('#edit_' + id).get(0).type = 'hidden';
    $('#save_' + id).get(0).type = 'submit';
}