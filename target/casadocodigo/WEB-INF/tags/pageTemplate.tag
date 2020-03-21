<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<!-- setando os atributos que serão utilizados na página 
os atributso são passados como parâmetros -->
<%@ attribute name="bodyClass" required="false" %>
<%@ attribute name="titulo" required="true" %>
<%@ attribute name="extraScripts" fragment="true" %>

<!-- pag template é reponsável por todo conteúdo de configuração "estático" 
e repetitivo. O conteúdo variável ficará nas outras JSPs, que serão "chamadas por tags

Temple funciona como um objeto que é "chamado" na JSP e recepe atributos por parâmetros-->

<!-- pageTemplate -->

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
	    
	<title>${titulo} - Casa do Código</title>
	
	<link href="https://plus.googlecom/108540024862647200608" rel="publisher"/>
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/cssbase-min.css" rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'/>
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/fontello-ie7.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/fontello-embedded.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/fontello.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/layout-colors.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/responsive-style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/guia-do-programador-style.css" rel="stylesheet" type="text/css"  media="all"  />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/produtos.css" rel="stylesheet" type="text/css"  media="all"  />    
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/book-collection.css" rel="stylesheet" type="text/css"  media="all"  />
	<link href="https://cdn.rawgit.com/alura-cursos/spring-mvc-i-criando-aplicacoes-web-master/master/src/main/webapp/resources/css/checkout-style.css" rel="stylesheet" type="text/css"  media="all"  />
</head>
<body class="${bodyClass }">

	<%@include file="/WEB-INF/views/cabecalho.jsp" %>

	<!-- Recebe o conteúdo da JSP mencionado entre as tags <tags:pageTemplad></tags:pageTemplate> -->
	<jsp:doBody />

	<!--Fragmentos servem para acrescentar info variada nas JSPs
		incluindo scripts padrões nas pg em forma de fragmentos-->
	 <jsp:invoke fragment="extraScripts"></jsp:invoke>

    <%@ include file="/WEB-INF/views/rodape.jsp" %>
</body>
</html>