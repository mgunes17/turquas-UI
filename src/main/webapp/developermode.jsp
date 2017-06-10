<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mustafa
  Date: 22.05.2017
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Turquas</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">TURQUAS</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Test Modu</a></li>
            <li><a href="#">Kullanıcı Modu</a></li>
        </ul>
    </div>
</nav>
<div class="jumbotroncontainer-fluid">
    <div class="row">
        <div class="col-md-4" align="left">
            <img src="bird.png" width="200px" height="200px"/>
        </div>
        <div class="col-md-4">
            <h2 class="text-center">TURQUAS</h2>
        </div>
        <div class="col-md-4" align="right">
            <img src="bird.png" width="200px" height="200px"/>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <form method="POST" action="/findinganswers">
                <div class="form-group">
                    <label for="question">Sor bakalım..</label>
                    <input type="text" id="question" class="form-control" name="question" value="${question}">
                    <input type="submit" class="btn btn-primary center-block" value="Cevapla"/>
                </div>
            </form>
            <c:choose>
                <c:when test="${set eq 1}">
                    <div class="alert alert-success">
                        Parametreler güncellendi.
                    </div>
                </c:when>
                <c:when test="${set eq 2}">
                    <div class="alert alert-danger">
                        Güncellenemedi! Geçersiz değerler.
                    </div>
                </c:when>
            </c:choose>
            <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Ayarlar</button>

            <div id="myModal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Arama Parametreleri</h4>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="/setsearchingparameterlist">
                                <div class="form-group">
                                    <label for="threshold">Min Benzerlik Oranı</label>
                                    <input type="number" id="threshold" name="threshold" class="form-control" value="${threshold}">
                                </div>
                                <div class="form-group">
                                    <label>Arama Yeri</label><br/>
                                    <input type="radio" name="source" value="google" checked> Google<br>
                                    <input type="radio" name="source" value="database"> Veritabanı<br>
                                </div>
                                <div class="form-group">
                                    <label>Gösterilecek Max Cevap Sayısı</label>
                                    <input type="number" id="answercount" name="answercount" class="form-control" value="${answercount}">
                                </div>
                                <input type="submit" class="btn btn-primary" value="Kaydet"/>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:choose>
        <c:when test="${cevap eq 3}">
            <div class="alert alert-danger">
                Bir hata meydana geldi.
            </div>
        </c:when>
        <c:when test="${cevap eq 2}">
            <div class="alert alert-warning">
                Lütfen geçerli bir soru giriniz
            </div>
        </c:when>
        <c:when test="${cevap eq 1}">
            <!--<div class="row">
                <div class="col-md-6">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Benzerlik Oranı</th>
                            <th>Soruların W2V Benzerliği</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${similarityList}">
                            <tr>
                                <td>${item.value}</td>
                                <td>${item.questionForCompare.answer}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Benzerlik Oranı</th>
                            <th>Soru-Cevap Çiftleri(Derin Öğrenme)</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${learningList}">
                            <tr>
                                <td>${item.value}</td>
                                <td>${item.questionForCompare.answer}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div> -->

            <c:forEach var="item" items="${answerList}">
                ${item.originalAnswer} ${item.sourceName} <br/>
            </c:forEach>
        </c:when>
    </c:choose>
</div>
<footer>
    <p class="text-center">&copy; Yıldız Teknik Üniversitesi Bilgisayar Mühendisliği</p>
    <p class="text-center">Türkçe İçerikler İçin Soru Cevaplama Sistemi</p>
</footer>
</body>
</html>