function findFile() {
    document.getElementById('my_hidden_file').click();
}

function loadFile() {
    document.getElementById('my_hidden_load').click();
}

function saveFile() {
    document.getElementById('my_hidden_save').click();
}

function onResponse(d) {
    eval('var obj = ' + d + ';');
    if(obj.success!=1) {
      alert('Ошибка!\nфайл ' + obj.filename + " не загружен - " + obj.myres);
      return;
    };
    alert('Файл загружен');
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
            document.getElementById('message').value = "Введите слова через пробел в верхнее и нижнее поля";
            break;
        case 'Expander':
            document.getElementById('array1').style.display='none';
            document.getElementById('array2').style.display='none';
            document.getElementById('number').style.display='block';
            document.getElementById('message').value = "Введите число";
            break;
        default:
            document.getElementById('array1').style.display='none';
            document.getElementById('array2').style.display='none';
            document.getElementById('number').style.display='none';
            document.getElementById('message').value = "Выберите задачу или загрузите файл";
  }
}