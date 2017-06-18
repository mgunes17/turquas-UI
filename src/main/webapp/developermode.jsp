<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <li><a href="#myModal" data-toggle="modal">Ayarlar</a></li>
        </ul>
    </div>
</nav>
<div class="jumbotroncontainer-fluid">
    <div class="row">
        <div class="col-md-4" align="left">
            <img src="ytulogopng.png" width="150px" height="150px"/>
        </div>
        <div class="col-md-4">
            <h1 class="text-center" style="color: turquoise;">TURQUAS</h1>
            <div class="panel panel-primary">
                <div class="panel-heading">İstatistikler</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <tbody>
                            <tr>
                                <th>Toplam Süre</th>
                                <td><fmt:formatNumber type = "number"
                                                      maxFractionDigits = "3"
                                                      value = "${istatistik.totalAnswerTime}" /> ms</td>
                            </tr>
                            <tr>
                                <th>Aday Cümle Çekilmesi</th>
                                <td><fmt:formatNumber type = "number"
                                                      maxFractionDigits = "3"
                                                      value = "${istatistik.candidateFetchTime}" /> ms</td>
                            </tr>
                            <tr>
                                <th>Python'dan Cevap Alınması</th>
                                <td><fmt:formatNumber type = "number"
                                                      maxFractionDigits = "3"
                                                      value = "${istatistik.pythonAnswerTime}" /> ms</td>
                            </tr>
                            <tr>
                                <th>Diğer İşlemler</th>
                                <td><fmt:formatNumber type = "number"
                                                      maxFractionDigits = "3"
                                                      value = "${istatistik.otherTime}" /> ms</td>
                            </tr>
                            <tr>
                                <th>Toplam Aday Sayısı</th>
                                <td>${istatistik.totalCandidateCount} cümle</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
        <div class="col-md-4" align="right">
            <img src="bird.png" width="130px" height="130px"/>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <form method="POST" action="findinganswers">
                <div class="form-group">
                    <label for="questionUI">Sor bakalım..</label>
                    <input type="text" id="questionUI" class="form-control" name="question" value="${question}">
                    <br/>
                    <input type="submit" class="btn btn-primary" value="Cevapla"/>
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
                            <form method="POST" action="setsearchingparameterlist">
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
                                    <label>Soru kelimesi silinsin mi</label><br/>
                                    <input type="radio" name="question_word" value="no" checked> Hayır<br>
                                    <input type="radio" name="question_word" value="yes"> Evet<br>
                                </div>
                                <div class="form-group">
                                    <label>Gösterilecek Max Cevap Sayısı (5-50)</label>
                                    <input type="number" id="answercount" name="answer_count" class="form-control"
                                           max="50" min="5" value="${answercount}">
                                </div>
                                <div class="form-group">
                                    <label>Google'da aranacak link sayısı (5-15)</label>
                                    <input type="number" id="linkcount" name="link_count" class="form-control"
                                            max="15" min="5" value="${linkcount}">
                                </div>
                                <input type="submit" class="btn btn-primary" value="Kaydet"/>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Kapat</button>
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
                <div class="col-md-6">
                    <h3>LSTM ile Benzerlik</h3>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th class="col-md-1">Sıra</th>
                                <th class="col-md-5">Cevap</th>
                                <th class="col-md-4">Kaynak</th>
                                <th class="col-md-2">Benzerlik Oranı</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${dlAnswerList}" varStatus="myIndex">
                                <tr>
                                    <td>${myIndex.index + 1}</td>
                                    <td>${item.originalAnswer}</td>
                                    <td><a href="${item.sourceName}" target="_blank">${item.sourceName}</a></td>
                                    <td>${item.similarityValue}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h3>W2V Average Benzerliği</h3>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th class="col-md-1">Sıra</th>
                            <th class="col-md-5">Cevap</th>
                            <th class="col-md-4">Kaynak</th>
                            <th class="col-md-2">Benzerlik Oranı</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${wsAnswerList}" varStatus="myIndex">
                            <tr>
                                <td>${myIndex.index + 1}</td>
                                <td>${item.originalAnswer}</td>
                                <td><a href="${item.sourceName}" target="_blank">${item.sourceName}</a></td>
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