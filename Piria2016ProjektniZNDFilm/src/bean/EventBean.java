package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.EventDAO;
import dto.EventDTO;
import util.JSFUtil;

@ManagedBean (name="event")
@ViewScoped
public class EventBean {

	EventDTO event;
	List<EventDTO> eventsList;
	
	public EventBean() {
		event = new EventDTO();
		eventsList = EventDAO.getAllInactive();
	}
	
	public String eventAdd() {
		System.out.println("event ann: " + event.getEventAnnouncement() + " name: " + event.getName());
		if (EventDAO.insert(event) > 0)
			FacesContext.getCurrentInstance().addMessage("formEvent", new FacesMessage( JSFUtil.getLangMessage("eventAddSuccessful")));
		else
			FacesContext.getCurrentInstance().addMessage("formEvent", new FacesMessage(JSFUtil.getLangMessage("eventAddError")));
		
		event = new EventDTO();
		return null;
	}
	
	public String eventCancel() {
		event = new EventDTO();
		return "guest?faces-redirect=true";
	}
	
	public String eventGrant(EventDTO e) {
		if (EventDAO.grant(e.getId()) > 0)
			FacesContext.getCurrentInstance().addMessage("formEventAdmin", new FacesMessage(JSFUtil.getLangMessage("eventGrantSuccessful")));
		else
			FacesContext.getCurrentInstance().addMessage("formEventAdmin", new FacesMessage(JSFUtil.getLangMessage("eventGrantNotSuccessful")));

		eventsList = EventDAO.getAllInactive();
		return null;
	}
	
	
	
	
	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}

	public List<EventDTO> getEventsList() {
		return eventsList;
	}

	public void setEventsList(List<EventDTO> eventsList) {
		this.eventsList = eventsList;
	}
	
	
	

}
