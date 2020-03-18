<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>


<c:url value="/" var="contextPath" />
<link rel="stylesheet" href="${contextPath}resources/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
	<h1>Casa do Código</h1>
	
	<!--Substituido a HTML form pelo form:form do jstl em virtude das funcionalidades 
	-commandName="produto" > identifica o formulario como objeto produto e facilita ao "chamar" atributos
	
	from:errors - utilizado para reportar mensagens do arquivo massages.proprieties
	
	
	s:mvcUrl('PC#gravar') >> notação jstl para otimizar a passagem de referencia do endereço de destino
	PC > abreviação de ProdutoController
	#gravar() > referencia o metodo gravar dentro da Classe ProdutoController
	-->
	<form:form action="${ s:mvcUrl('PC#gravar').build() }" method="post" commandName="produto" enctype="multipart/form-data">
	<!-- enctype="multpart/form-data" >> configurando o form para transportar arquivo -->
	    <div>
	        <label>Título</label> 
	        <form:input path="titulo"/>
	        <form:errors path="titulo" />
	        
	        <!-- <input type="text" name="titulo" /> Atualizado os inputs para form:input 
	        caso haja algum erro com algum input.. os inputs que possuem valor ok.. não serão zerados-->
	    </div>
	    <div>
	        <label>Descrição</label>
	        <form:input path="descricao" rows="10" cols="20"/>
	        <form:errors path="descricao" />
	        
	    </div>
	    <div>
	        <label>Páginas</label>	        
	        <form:input path="paginas"/>
	        <form:errors path="paginas" />
	        
	    </div>
	    <div>
	        <label>Data de Lançamento</label>	        
	        <form:input path="dataLancamento"/>
	        <form:errors path="dataLancamento" />
	    </div>
	    <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label> 
	        	<form:input path="precos[${status.index}].valor"/>
	        	<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />    
	             
	        </div>
	    </c:forEach>
	    <div>
	    	<label>Sumário</label>
	    	<input name="sumario" type="file">
	    </div>
	    <button type="submit">Cadastrar</button>

	</form:form>
</body>
</html>