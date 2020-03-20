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
<script src="${contextPath}resources/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<style type="text/css">
	body{
		padding: 60px 0px;
	}
</style>

</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
		  <div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			  <span class="sr-only">Toggle navigation</span>
			  <span class="icon-bar"></span>
			  <span class="icon-bar"></span>
			  <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Casa do Código</a>
		  </div>
		  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
			  <li><a href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a></li>
			  <li><a href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a></li>
		  </ul>
		  <ul class="nav navbar-nav navbar-right">
			<li>
			  <a href="#"> 
				  <!--"principal" - usuario logado -->
				  <security:authentication property="principal" var="usuario"/>
				  Usuário: ${usuario.username} 
			  </a>
			</li>
			<li class="nav-item">
				<a href="${contextPath}logout">Sair</a>
			</li>
		  </ul>
		  </div><!-- /.navbar-collapse -->
		</div>
	  </nav>

	<div class="container">
		<h1>Cadastro de Produto</h1>
		
		<!--Substituido a HTML form pelo form:form do jstl em virtude das funcionalidades 
		-commandName="produto" > identifica o formulario como objeto produto e facilita ao "chamar" atributos
		
		from:errors - utilizado para reportar mensagens do arquivo massages.proprieties
		
		
		s:mvcUrl('PC#gravar') >> notação jstl para otimizar a passagem de referencia do endereço de destino
		PC > abreviação de ProdutoController
		#gravar() > referencia o metodo gravar dentro da Classe ProdutoController
		-->
		<form:form action="${ s:mvcUrl('PC#gravar').build() }" method="post" commandName="produto" enctype="multipart/form-data">
		<!-- enctype="multpart/form-data" >> configurando o form para transportar arquivo -->
			<div cssClass="form-group">
				<label>Título</label> 
				<form:input path="titulo" cssClass="form-control"/>
				<form:errors path="titulo" />
				
				<!-- <input type="text" name="titulo" /> Atualizado os inputs para form:input 
				caso haja algum erro com algum input.. os inputs que possuem valor ok.. não serão zerados-->
			</div>
			<div cssClass="form-group">
				<label>Descrição</label>
				<form:textarea path="descricao" cssClass="form-control"/>
				<form:errors path="descricao" />
				
			</div>
			<div cssClass="form-group">
				<label>Páginas</label>	        
				<form:input path="paginas" cssClass="form-control"/>
				<form:errors path="paginas" />
				
			</div>
			<div cssClass="form-group">
				<label>Data de Lançamento</label>	        
				<form:input path="dataLancamento" cssClass="form-control"/>
				<form:errors path="dataLancamento" />
			</div>
			<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
				<div cssClass="form-group">
					<label>${tipoPreco}</label> 
					<form:input path="precos[${status.index}].valor" cssClass="form-control"/>
					<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />    
					
				</div>
			</c:forEach>
			<div cssClass="form-group">
				<label>Sumário</label>
				<input name="sumario" type="filecssClass="form-control" cssClass="form-control"> 
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>

		</form:form>
	</div>
</body>
</html>