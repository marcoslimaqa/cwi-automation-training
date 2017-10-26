package br.com.cwi.automation_training.pages.sikuli.notepad;

import br.com.cwi.automation_training.util.TestRule;
import io.github.marcoslimaqa.sikulifactory.SikuliFactory;

public class NotepadPage extends NotepadPageElementMap{

	public NotepadPage() {
		SikuliFactory.initElements(TestRule.getSikuli(), this);
	}
	
	public void adicionarTexto(String texto) {
		menu.wait(10);
		paste(texto);
		logPrint("Adicionou o texto");
	}
	
}