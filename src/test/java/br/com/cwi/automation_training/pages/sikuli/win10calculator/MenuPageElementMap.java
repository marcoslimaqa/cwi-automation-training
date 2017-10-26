package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.pages.BasePage;
import io.github.marcoslimaqa.sikulifactory.FindBy;
import io.github.marcoslimaqa.sikulifactory.SikuliElement;

public class MenuPageElementMap extends BasePage {
	
	@FindBy(image="scientific.png")
	protected SikuliElement scientific;
	
}