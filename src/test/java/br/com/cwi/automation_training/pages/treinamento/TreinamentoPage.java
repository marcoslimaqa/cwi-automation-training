package br.com.cwi.automation_training.pages.treinamento;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import br.com.cwi.automation_training.util.TestRule;

public class TreinamentoPage extends TreinamentoElementMap {

	public TreinamentoPage() {
		PageFactory.initElements(TestRule.getDriver(), this);
	}	

	public void buscarProduto(String produto) {	
		search_query_top.sendKeys(produto);
		botaoPesquisar.click();
		logPrint("Buscou pelo produto " + produto);
	}
}