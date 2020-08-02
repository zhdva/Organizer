<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Органайзер</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="res/style.css" rel="stylesheet" type="text/css"/>
</head>
<body onload="fill()">
    <form ModelAttribute="form"  method="post" class="form" style="max-width: 600px">

        <h1>Органайзер</h1>
        <hr>

        <input type="text" placeholder="Выберите задачу..." name="task" list="tasks" onchange="showFields();" id="tasks-field">
        <datalist id="tasks">
            <option value="Comparator">
            <option value="Expander">
        </datalist>

        <textarea placeholder="Введите слова..." name="array1" class="array" id="array1"></textarea>
        <textarea placeholder="Введите слова..." name="array2" class="array" id="array2"></textarea>

        <input type="text" onkeyup="this.value = this.value.replace(/[^\d]/g,'');" placeholder="Введите число..." name="number" class="number" id="number">

        <div class="buttons">
            <button type="submit" formaction="save" class="saveopenbtn" id="savebtn" disabled>Сохранить</button>
            <button type="submit" formaction="calculate" id="calculatebtn" disabled>Посчитать</button>
            <button type="button" onclick="findFile();" class="saveopenbtn" id="openbtn">Загрузить</button>
        </div>

        <div class="hiddenInput">
            <input type="file" id="my_hidden_file" name="file" onchange="loadFile();">
            <input type="submit" formaction="open" formenctype="multipart/form-data" id="my_hidden_load" style="display: none" value='Загрузить'>
        </div>

        <textarea name="message" class="mess" id="message" readonly></textarea>

    </form>
    <script src="res/script.js"></script>
    <script type="text/javascript">
        function fill() {
            document.getElementById('tasks-field').value = "${map.get('task')}";
            showFields();
            document.getElementById('array1').value = "${map.get('array1')}";
            document.getElementById('array2').value = "${map.get('array2')}";
            document.getElementById('number').value = "${map.get('number')}";
            document.getElementById('message').value = "${map.get('message')}";
        }
    </script>
</body>
</html>