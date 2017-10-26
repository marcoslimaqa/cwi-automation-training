package br.com.cwi.automation_training.pages.selenium;

import org.openqa.selenium.support.PageFactory;

import br.com.cwi.automation_training.util.TestRule;

public class HomePage extends HomePageElementMap {

	public HomePage() {
		PageFactory.initElements(TestRule.getDriver(), this);
	}

	public void acessarMenuSobreCWIQuemSomos(){
		moveToElement(menuSobreCWI);
		submenuQuemSomos.click();
		logPrint("Acessou menu Quem Somos");
	}
	
	public void acessarMenuServicosFabricaDeTeste(){	
		moveToElement(menuServicos);
		logPrint("Acessou menu Servicos");
		submenuFabricaDeTestes.click();
		logPrint("Acessou submenu Fabrica de Testes");
	}
	
	public void acessarMenuQualidade(){
		menuQualidade.click();
	}
	
	public void acessarMenuOportunidades(){
		menuOportunidades.click();
	}
}