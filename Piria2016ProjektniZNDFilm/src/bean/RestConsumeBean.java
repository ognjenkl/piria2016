package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dto.RestConsumeDTO;
import util.JSFUtil;

/**
 * @author ognjen
 *
 */
@ManagedBean(name="restConsume")
@ViewScoped
public class RestConsumeBean {

	private String searchText;
	private List<RestConsumeDTO> titlesList;
	private List<RestConsumeDTO> namesList;
	private List<RestConsumeDTO> charactersList;
	private List<RestConsumeDTO> companiesList;
	private List<RestConsumeDTO> keywordsList;
	
	@PostConstruct
	public void init() {
		titlesList = new ArrayList<>();
		namesList = new ArrayList<>();
		charactersList = new ArrayList<>();
		companiesList = new ArrayList<>();
		keywordsList = new ArrayList<>();
	}

	
	public String restSearch() {
		titlesList = new ArrayList<>();
		namesList = new ArrayList<>();
		charactersList = new ArrayList<>();
		companiesList = new ArrayList<>();
		keywordsList = new ArrayList<>();
		if (searchText != null && !searchText.equals("")){
			String url = "http://imdb.wemakesites.net/api/search?api_key=ee85e29c-58ef-43d2-a036-539699415543&q=" + searchText;
			BufferedReader in = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
				String line;
				StringBuilder response = new StringBuilder();
				while ((line = in.readLine()) != null)
					response.append(line);
				
				Integer respCode = parseResponse(response.toString());
				if( respCode == null || respCode != 200){
					FacesContext.getCurrentInstance().addMessage("restIllustrationForm", new FacesMessage(JSFUtil.getLangMessage("restSearchError")));
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		return null;
	}
	
	public Integer parseResponse(String textToParse) {
		Integer retVal = null;
		JSONObject jsonObj;
		titlesList = new ArrayList<>();
		namesList = new ArrayList<>();
		charactersList = new ArrayList<>();
		companiesList = new ArrayList<>();
		keywordsList = new ArrayList<>();
		
		try {
			jsonObj = new JSONObject(textToParse);
			retVal = jsonObj.getInt("code");
			
			if (retVal != 200)
				return retVal;
			else {
				JSONObject jsonData = jsonObj.getJSONObject("data");
				JSONObject jsonResults = jsonData.getJSONObject("results");
				
				if(jsonResults.toString().contains("names")){
					JSONArray names = jsonResults.getJSONArray("names");
					for (int i = 0; i < names.length(); i++) {
						JSONObject jObj = names.getJSONObject(i);
						RestConsumeDTO restConsumeDTO = new RestConsumeDTO();
						restConsumeDTO.setTitle(jObj.getString("title"));
						restConsumeDTO.setId(jObj.getString("id"));
						restConsumeDTO.setThumbnail(jObj.getString("thumbnail"));
						restConsumeDTO.setUrl(jObj.getString("url"));
						namesList.add(restConsumeDTO);
					}
				}
				
				if(jsonResults.toString().contains("titles")){
					JSONArray titles = jsonResults.getJSONArray("titles");
					for (int i = 0; i < titles.length(); i++) {
						JSONObject jObj = titles.getJSONObject(i);
						RestConsumeDTO restConsumeDTO = new RestConsumeDTO();
						restConsumeDTO.setTitle(jObj.getString("title"));
						restConsumeDTO.setId(jObj.getString("id"));
						restConsumeDTO.setThumbnail(jObj.getString("thumbnail"));
						restConsumeDTO.setUrl(jObj.getString("url"));
						titlesList.add(restConsumeDTO);
					}
				}
				
				if(jsonResults.toString().contains("characters")){	
					JSONArray characters = jsonResults.getJSONArray("characters");
					for (int i = 0; i < characters.length(); i++) {
						JSONObject jObj = characters.getJSONObject(i);
						RestConsumeDTO restConsumeDTO = new RestConsumeDTO();
						restConsumeDTO.setTitle(jObj.getString("title"));
						restConsumeDTO.setId(jObj.getString("id"));
						restConsumeDTO.setThumbnail(jObj.getString("thumbnail"));
						restConsumeDTO.setUrl(jObj.getString("url"));
						charactersList.add(restConsumeDTO);
					}
				}
				
				if(jsonResults.toString().contains("companies")){
					JSONArray companies = jsonResults.getJSONArray("companies");
					for (int i = 0; i < companies.length(); i++) {
						JSONObject jObj = companies.getJSONObject(i);
						RestConsumeDTO restConsumeDTO = new RestConsumeDTO();
						restConsumeDTO.setTitle(jObj.getString("title"));
						restConsumeDTO.setId(jObj.getString("id"));
						restConsumeDTO.setThumbnail(jObj.getString("thumbnail"));
						restConsumeDTO.setUrl(jObj.getString("url"));
						companiesList.add(restConsumeDTO);
					}
				}
			
				if(jsonResults.toString().contains("keywords")){
					JSONArray keywords = jsonResults.getJSONArray("keywords");
					for (int i = 0; i < keywords.length(); i++) {
						JSONObject jObj = keywords.getJSONObject(i);
						RestConsumeDTO restConsumeDTO = new RestConsumeDTO();
						restConsumeDTO.setTitle(jObj.getString("title"));
						restConsumeDTO.setId(jObj.getString("id"));
						restConsumeDTO.setThumbnail(jObj.getString("thumbnail"));
						restConsumeDTO.setUrl(jObj.getString("url"));
						keywordsList.add(restConsumeDTO);
					}
				}

			}
			
			return retVal;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	public String getSearchText() {
		return searchText;
	}


	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}


	public List<RestConsumeDTO> getTitlesList() {
		return titlesList;
	}

	public void setTitlesList(List<RestConsumeDTO> titlesList) {
		this.titlesList = titlesList;
	}

	public List<RestConsumeDTO> getNamesList() {
		return namesList;
	}

	public void setNamesList(List<RestConsumeDTO> namesList) {
		this.namesList = namesList;
	}

	public List<RestConsumeDTO> getCharactersList() {
		return charactersList;
	}

	public void setCharactersList(List<RestConsumeDTO> charactersList) {
		this.charactersList = charactersList;
	}

	public List<RestConsumeDTO> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<RestConsumeDTO> companiesList) {
		this.companiesList = companiesList;
	}

	public List<RestConsumeDTO> getKeywordsList() {
		return keywordsList;
	}

	public void setKeywordsList(List<RestConsumeDTO> keywordsList) {
		this.keywordsList = keywordsList;
	}
	
	
	
	
	
}
