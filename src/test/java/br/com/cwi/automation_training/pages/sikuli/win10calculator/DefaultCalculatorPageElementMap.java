package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.pages.BasePage;
import io.github.marcoslimaqa.sikulifactory.FindBy;
import io.github.marcoslimaqa.sikulifactory.SikuliElement;

public class DefaultCalculatorPageElementMap extends BasePage {
	
	@FindBy(image="menu.png")
	protected SikuliElement menu;
	
}