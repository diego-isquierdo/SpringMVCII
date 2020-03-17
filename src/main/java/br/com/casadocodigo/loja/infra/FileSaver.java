package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//nota a classe como compenente - 'jnjeta um componente' - no caso , um arquivo
@Component
public class FileSaver {
	
	//spring injeta o request atuomático
	@Autowired
	private HttpServletRequest request;
	
	//metodo que irá 'escrever' o arquivo que 'upou'
	public String write(String baseFolder, MultipartFile file) {

		try {
			//montando a Srting com o caminho para salvar o arquivo
			String realPath = request.getServletContext().getRealPath("\\" + baseFolder);
			System.out.println("realPath > " + realPath);
			String path =path = realPath + "\\" + file.getOriginalFilename();
			
			System.out.println("path > " + path);
			
			//transferindo o arquivo para o 'new file' no caminho orientado
			file.transferTo(new File(path));
			
			//retornando o caminho onde o arquivo foi salvo
			return path;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "";
		} catch ( IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
