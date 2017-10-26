package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.util.TestRule;
import io.github.marcoslimaqa.sikulifactory.SikuliFactory;

public class MenuPage extends MenuPageElementMap {
	
	public MenuPage() {
		SikuliFactory.initElements(TestRule.getSikuli(), this);
	}

	public void setScientific() {
		scientific.wait(5).click();
		logPrint("Clicou na calculadora cientifica");
	}

}
