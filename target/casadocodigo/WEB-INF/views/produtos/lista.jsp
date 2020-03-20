<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/" var="contextPath" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>


<link rel="stylesheet" href="${contextPath}resources/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="${contextPath}resources/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<style type="text/css">
	body{
		padding-top: 60px;
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
		  </div><!-- /.navbar-collapse -->
			<ul class="nav navbar-nav navbar-right">
			  <li>
				<a href="#"> 
					<!--"principal" - usuario logado -->
					<security:authentication property="principal" var="usuario"/>
					Usuário: ${usuario.username}
					<a href="${contextPath}logout">Sair</a>
				</a></li>
			</ul>
		</div>
	  </nav>

	<div class="container">
		<h1>Lista de Produtos</h1>
		
		<div>${sucesso }</div>
		<div>${falha }</div>
		
		<table class="table table-bordered table-striped table-rover">
			<tr>
				<th>
					Título
				</th>
				<th>
					Descrição
				</th>
				<th>
					Páginas
				</th>
			</tr>
			<c:forEach items="${produtos }" var="produto">
				<tr>
					<td>
						<!-- usando o spring para acionar a url via classe -->
						<!-- arg - recebendo id por argumentos do 'PC'(ProdutosController) '0' eh padrão -->
						<!-- cada link apontará o o seu respectivo produto e seu próprio 'id' -->
						<a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.titulo }</a>
					</td>
					<td>
						${produto.descricao }
					</td>
					<td>
						${produto.paginas }
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>