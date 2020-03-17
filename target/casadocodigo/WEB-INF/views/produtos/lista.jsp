<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>

<link rel="stylesheet" href="/resources/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="/resources/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</head>
<body>
	<h1>Lista de Produtos</h1>
	
	<div>${sucesso }</div>
	<div>${falha }</div>
	
	<table>
		<tr>
			<td>
				Título
			</td>
			<td>
				Descrição
			</td>
			<td>
				Páginas
			</td>
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
</body>
</html>