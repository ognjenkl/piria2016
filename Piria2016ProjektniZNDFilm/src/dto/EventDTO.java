package dto;

import java.io.Serializable;
import java.util.Date;

public class EventDTO implements Serializable {

	private static final long serialVersionUID = -2275684107663898476L;

	Integer id;
	Date eventAnnouncement;
	Date eventMaintained;
	String name;
	String location;
	Integer active;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getEventAnnouncement() {
		return eventAnnouncement;
	}
	public void setEventAnnouncement(Date eventAnnouncement) {
		this.eventAnnouncement = eventAnnouncement;
	}
	public Date getEventMaintained() {
		return eventMaintained;
	}
	public void setEventMaintained(Date eventMaintained) {
		this.eventMaintained = eventMaintained;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
	
	
	

}
