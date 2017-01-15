package bean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.EventDAO;
import dao.UserDAO;
import dto.EventDTO;
import dto.UserDTO;
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
	
	public String eventApprove(EventDTO e) {
		if (EventDAO.approve(e.getId()) > 0) {
			FacesContext.getCurrentInstance().addMessage("formEventAdmin", new FacesMessage(JSFUtil.getLangMessage("eventApproveSuccessful")));
			List<UserDTO> usersList = UserDAO.getByPrivilege(2);
			usersList.addAll(UserDAO.getByPrivilege(3));
			sendMailToAllMembers(usersList, e);
			
		}
		else
			FacesContext.getCurrentInstance().addMessage("formEventAdmin", new FacesMessage(JSFUtil.getLangMessage("eventApproveNotSuccessful")));

		eventsList = EventDAO.getAllInactive();
		return null;
	}
	
	public void sendMailToAllMembers(List<UserDTO> usersList, EventDTO e) {
		for (UserDTO user : usersList) {
			sendMail(user, e);
		}
	}
	
	public void sendMail(UserDTO user, EventDTO eventDTO) {
		String to = user.getEmail();
		String from = "zndfproject@gmail.com";
		String username = "zndfproject@gmail.com";
		String password = "zndfzndf";
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, 
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Event notification");
			message.setText("Hello " + user.getFirstName() + ",\n\n"
					+ "you are invited to the event:\n"
					+ "Name: " + eventDTO.getName() + "\n"
					+ "Time: " + new SimpleDateFormat("dd.MM.yyyy. HH:mm").format(eventDTO.getEventMaintained()) + "\n "
					+ "Location: " + eventDTO.getLocation() + "\n\n "
					+ "Best regards,\n ZNDFilm team");
			
			Transport.send(message);
			
			System.out.println("Sent mail successfully to " + user.getEmail());
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
