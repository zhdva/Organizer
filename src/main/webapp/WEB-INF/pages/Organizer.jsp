<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Органайзер</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="<c:url value='/res/style.css'/>"/>
</head>
<body>
  <form class="form" style="max-width: 600px">
    <div class="container">
      <h1>Органайзер</h1>
      <hr>

      <input type="text" placeholder="Выберите задачу..." name="task" list="tasks">
      <datalist id="tasks">
        <option value="Comparator">
        <option value="Expander">
      </datalist>
      <select name="form_select">
        <option selected="selected" value=""></option>
        <option value="Comparator">Comparator</option>
        <option value="Expander">Expander</option>
      </select>

      <label for="array1"><b>Массив 1</b></label>
      <input type="textarea" cols="20" rows="15" placeholder="Введите слова..." name="array1">

      <label for="array2"><b>Массив 2</b></label>
      <input type="textarea" cols="20" rows="15" placeholder="Введите слова..." name="array2">

      <label for="number"><b>Число</b></label>
      <input type="text" placeholder="Введите число..." name="number">

      <button type="button" class="calculatebtn" onclick="form.array1.style.backgroundColor='#e1dccd'">Посчитать</button>

      <p id="result"><b>Результат</b></p>

      <div class="saveopen">
        <button type="button" class="saveopenbtn">Сохранить</button>
        <button type="submit" class="saveopenbtn">Загрузить</button>
      </div>

    </div>
  </form>
</body>
</html>