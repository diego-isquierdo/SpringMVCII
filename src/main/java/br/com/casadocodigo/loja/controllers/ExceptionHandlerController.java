package br.com.casadocodigo.loja.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/** Classe para tratar as exceptions da aplicação */

//ControllerAdvice > 
@ControllerAdvice
public class ExceptionHandlerController {

    //tratando erro ao consultar produtos direto na url
	//tratando as exceptions de null
	@ExceptionHandler(Exception.class)
	public ModelAndView trataExceptionGenerica(Exception exception){
        exception.printStackTrace();


        //apenas para enviar para a pg qual exceção ocorreu
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", exception);

		return modelAndView;
	}

}
