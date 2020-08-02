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