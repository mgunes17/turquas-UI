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
            <li><a href="#myModal" data-toggle="modal">Ayarlar</a></li>
        </ul>
    </div>
</nav>
<div class="jumbotroncontainer-fluid">
    <div class="row">
        <div class="col-md-4" align="left">
            <img src="bird.png" width="200px" height="200px"/>
        </div>
        <div class="col-md-4">
            <h1 class="text-center" style="color: turquoise;">TURQUAS</h1>
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
                    <label for="questionUI">Sor bakalım..</label>
                    <input type="text" id="questionUI" class="form-control" name="question" value="${question}">
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
                                    <label for="threshold">Min Benzerlik Oranı (0-100)</label>
                                    <input type="number" id="threshold" name="threshold" class="form-control" value="${threshold}">
                                </div>
                                <div class="form-group">
                                    <label>Arama Yeri</label><br/>
                                    <input type="radio" name="source" value="google" checked> Google<br>
                                    <input type="radio" name="source" value="database"> Veritabanı<br>
                                </div>
                                <div class="form-group">
                                    <label>Gösterilecek Max Cevap Sayısı (5-50)</label>
                                    <input type="number" id="answercount" name="answercount" class="form-control" value="${answercount}">
                                </div>
                                <div class="form-group">
                                    <label>Google'da aranacak link sayısı (5-15)</label>
                                    <input type="number" id="linkcount" name="linkcount" class="form-control" value="${linkcount}">
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
        <c:when test="${cevap eq 5}">
            <div class="alert alert-danger">
                Soket bağlantısında hata meydana geldi.
            </div>
        </c:when>
        <c:when test="${cevap eq 4}">
            <div class="alert alert-warning">
                Aday cümle bulunamadı.
            </div>
        </c:when>
        <c:when test="${cevap eq 3}">
            <div class="alert alert-danger">
                Beklenmedik bir hata oluştu.
            </div>
        </c:when>
        <c:when test="${cevap eq 2}">
            <div class="alert alert-warning">
                Lütfen geçerli bir soru giriniz
            </div>
        </c:when>
        <c:when test="${cevap eq 1}">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th class="col-md-6">Cevap</th>
                                <th class="col-md-4">Kaynak</th>
                                <th class="col-md-2">Benzerlik Oranı</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${answerList}">
                                <tr>
                                    <td>${item.originalAnswer}</td>
                                    <td>${item.sourceName}</td>
                                    <td>${item.similarityValue}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
    </c:choose>
</div>
<footer>
    <p class="text-center">&copy; Yıldız Teknik Üniversitesi Bilgisayar Mühendisliği</p>
    <p class="text-center">Türkçe İçerikler İçin Soru Cevaplama Sistemi</p>
</footer>
</body>
</html>