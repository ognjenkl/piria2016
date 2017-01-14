package bean;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import dao.GalleryPictureDAO;
import dto.GalleryPictureDTO;
import util.JSFUtil;

@ManagedBean (name="gallery")
@ViewScoped
public class GalleryBean {

	Part eventPicPart;
	List<GalleryPictureDTO> eventPicsList;
	String eventPicName;
	
	@PostConstruct
	public void init() {
		eventPicsList = GalleryPictureDAO.getAll();
		
	}
	
	public String addEventPicture() {
		Properties prop = new Properties();
		try {
			prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String filePath = JSFUtil.uploadEventPic(eventPicPart, prop);
		if(filePath != null && filePath.length() > 0){
			GalleryPictureDTO galleryPictureDTO = new GalleryPictureDTO();
			galleryPictureDTO.setLocation(filePath);
			galleryPictureDTO.setName(filePath);
			System.out.println("event filePath: " + filePath );
			
			Integer picId = GalleryPictureDAO.insert(galleryPictureDTO);
			if(picId > 0) {
				galleryPictureDTO.setId(picId);
				eventPicsList = GalleryPictureDAO.getAll();
				FacesContext.getCurrentInstance().addMessage( "formGallery", new FacesMessage(JSFUtil.getLangMessage("galleryEventPicAddSuccesful")));
			} else
				FacesContext.getCurrentInstance().addMessage( "formGallery", new FacesMessage(JSFUtil.getLangMessage("galleryEventPicAddError")));
		} else
			FacesContext.getCurrentInstance().addMessage("formGallery", new FacesMessage(JSFUtil.getLangMessage("galleryEventPicAddNoPic")));
		
		return null;
	}
	
	
	
	
	
	
	
	
	public Part getEventPicPart() {
		return eventPicPart;
	}

	public void setEventPicPart(Part eventPicPart) {
		this.eventPicPart = eventPicPart;
	}

	public List<GalleryPictureDTO> getEventPicsList() {
		return eventPicsList;
	}

	public void setEventPicsList(List<GalleryPictureDTO> eventPicsList) {
		this.eventPicsList = eventPicsList;
	}

	
	
	
	
}
