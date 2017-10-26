package br.com.cwi.automation_training.pages.sikuli.notepad;

import br.com.cwi.automation_training.pages.BasePage;
import io.github.marcoslimaqa.sikulifactory.FindBy;
import io.github.marcoslimaqa.sikulifactory.SikuliElement;

public class NotepadPageElementMap extends BasePage {
	
	@FindBy(image = "notepad-menu-win10.png", similarity = 60)
	protected SikuliElement menu;
	
}