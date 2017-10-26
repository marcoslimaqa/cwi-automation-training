package br.com.cwi.automation_training.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.ExtentTest;
import com.jayway.jsonpath.JsonPath;

import br.com.cwi.automation_training.pages.BasePage;
import br.com.cwi.automation_training.util.Utils;

public class HttpRequest extends BasePage {

	private HttpURLConnection connection;
	private String requestUrl;
	private List<List<String>> headerParams;
	private String requestBody;
	private String requestMethod;
	private String responseBody;
	private int responseCode;
	private String responseContentLength;

	private ExtentTest reportRequest;
	private ExtentTest reportResponse;
	
	public HttpRequest() {
		headerParams = new ArrayList<List<String>>();
		reportRequest = createChildStart("Request");
		reportResponse = createChildStart("Response");
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
		childLogInfo(reportRequest, "<b>URL Requisicao:</b> " + requestUrl);
	}

	public void setHeaderParam(String parametro, String valor) {
		List<String> headerParam = new ArrayList<String>();
		headerParam.add(parametro);
		headerParam.add(valor);
		headerParams.add(headerParam);
		childLogInfo(reportRequest, "<b>Cabecalho Requisicao adicional:</b> " + parametro + " : " + valor);
	}

	public void setRequestBodyFromFile(String fileName) {
		String requestBody = Utils.readFileToString("src/test/resources/request-body-files/" + fileName, StandardCharsets.UTF_8);
		
		if (fileName.contains(".json")) {
			JSONObject json = new JSONObject(requestBody);
			requestBody = json.toString(4);
		}
		
		this.requestBody = requestBody;
		childLogInfo(reportRequest, "<b>Corpo Requisicao:</b> <pre>" + requestBody + "</pre>");
	}
	
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
		childLogInfo(reportRequest, "<b>Corpo Requisicao:</b> <pre>" + requestBody + "</pre>");
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		childLogInfo(reportRequest, "<b>Metodo Requisicao:</b> " + requestMethod);
	}
	
	private void setResponseBody() throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader br;
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		    br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		} else {
		    br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
		}
		String output;
		while ((output = br.readLine()) != null) sb.append(output);
		
		if (isJson(sb.toString())) {
			JSONObject json = new JSONObject(sb.toString());
			this.responseBody = json.toString(4);
		} else {
			this.responseBody = sb.toString();
		}
		
		childLogInfo(reportResponse, "<b>Corpo Resposta:</b> <pre>" + responseBody + "</pre>");
	}
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
		childLogInfo(reportResponse, "<b>Codigo Resposta:</b> " + responseCode);
	}
	
	public void setResponseContentLength(String responseContentLength) {
		this.responseContentLength = responseContentLength;
		childLogInfo(reportResponse, "<b>Tamanho Conteudo Resposta:</b> " + responseContentLength);
	}
	
	public void execute(){
		
		httpsIgnoreSecurityCertifacate();
		
		/** 
		 * Por seguranca, alguns parametros de headers relacionados a CORS, por exemplo Origin, sao restritos na classe HttpURLConnection.
		 * Para que esta restricao seja liberada, foi utilizado a linha abaixo, conforme explicado neste link:
		 * http://stackoverflow.com/questions/13255051/setting-origin-and-access-control-request-method-headers-with-jersey-client/13266620
		 */
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		
		try {
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(15000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod(requestMethod);
			
			for (List<String> headerParam : headerParams) {
				connection.setRequestProperty(headerParam.get(0), headerParam.get(1));
			}
			
			if (requestBody != null) {
				connectionSetRequestBody(requestBody);
			}
			connection.connect();
			
			setResponseCode(connection.getResponseCode());
			setResponseContentLength(connection.getHeaderField("Content-Length"));
			setResponseBody();
			logger.debug(responseBody);
			
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void httpsIgnoreSecurityCertifacate(){
        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub
			}
			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub
			}

        } };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /* End of the fix*/
	}

	private void connectionSetRequestBody(String requestBody) throws IOException {
		OutputStream os = connection.getOutputStream();
        os.write(requestBody.getBytes("UTF-8"));
        os.close();
	}
	
	private boolean isJson(String string){
		try {
			new JSONObject(string);
		} catch (Exception e) {
		    return false;
		}
		return true;
	}

	public boolean jsonEqualsText(String jsonPath, String textoEsperado) {
		String jsonPathValue = JsonPath.read(responseBody, jsonPath).toString();
		boolean jsonEqualsText = textoEsperado.equals(jsonPathValue);
		if (jsonEqualsText) {
			logPass("<b>Resultado Apresentado corretamente: </b><br>" + jsonPath + " = " + jsonPathValue);
		} else {
			logFail("<b>Resultado Esperado: </b><br>" + jsonPath + " = " + textoEsperado + "<br><b>"
					+  "Resultado Apresentado: </b><br>" + jsonPath + " = " + jsonPathValue);
		}
		return jsonEqualsText;
	}

	public boolean isResponseCodeCorrect(int responseCode) {
		boolean isResponseCodeCorrect = this.responseCode == responseCode;
		if (isResponseCodeCorrect) {
			logPass("<b>Response Code apresentado corretamente: </b>" + this.responseCode);
			return true;
		}
		logFail("Response Code esperado: " + responseCode + "<br>Response Code apresentado: " + this.responseCode);
		return false;
	}

}
