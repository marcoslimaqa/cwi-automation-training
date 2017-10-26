package br.com.cwi.automation_training.pages.sikuli.win8calculator;

import br.com.cwi.automation_training.pages.BasePage;
import io.github.marcoslimaqa.sikulifactory.FindBy;
import io.github.marcoslimaqa.sikulifactory.SikuliElement;

public class CalculadoraWin8PageElementMap extends BasePage {
	
	@FindBy(image="botao-igual.png")
	protected SikuliElement botaoIgual;
	
}