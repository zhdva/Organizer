function findFile() {
    document.getElementById('my_hidden_file').click();
}

function loadFile() {
    document.getElementById('my_hidden_load').click();
}

function showFields() {
    document.getElementById('array1').value = null;
    document.getElementById('array2').value = null;
    document.getElementById('number').value = null;
    switch (document.getElementById('tasks-field').value) {
        case 'Comparator':
            document.getElementById('array1').style.display='block';
            document.getElementById('array2').style.display='block';
            document.getElementById('number').style.display='none';
            document.getElementById('savebtn').disabled = false;
            document.getElementById('calculatebtn').disabled = false;
            document.getElementById('message').value = "Введите слова через пробел в верхнее и нижнее поля";
            break;
        case 'Expander':
            document.getElementById('array1').style.display='none';
            document.getElementById('array2').style.display='none';
            document.getElementById('number').style.display='block';
            document.getElementById('savebtn').disabled = false;
            document.getElementById('calculatebtn').disabled = false;
            document.getElementById('message').value = "Введите число";
            break;
        default:
            document.getElementById('array1').style.display='none';
            document.getElementById('array2').style.display='none';
            document.getElementById('number').style.display='none';
            document.getElementById('savebtn').disabled = true;
            document.getElementById('calculatebtn').disabled = true;
            document.getElementById('message').value = "Выберите задачу или загрузите файл";
    }
}

function stopReloadKey(evt) {
    var evt = (evt) ? evt : ((event) ? event : null);
    var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
    if (evt.keyCode == 13)  {
        return false;
    }
}

document.onkeypress = stopReloadKey;
document.getElementById('tasks-field').onkeypress = stopReloadKey;
document.getElementById('array1').onkeypress = stopReloadKey;
document.getElementById('array2').onkeypress = stopReloadKey;
document.getElementById('number').onkeypress = stopReloadKey;
document.getElementById('message').onkeypress = stopReloadKey;