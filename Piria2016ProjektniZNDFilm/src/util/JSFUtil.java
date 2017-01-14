package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

public class JSFUtil {

	/**
	 * Creates view message.
	 * 
	 * @param componentID view component to show message
	 * @param messageKey message key in locale language properties 
	 */
	public static void jsfMessage(String componentID, String messageKey){
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ResourceBundle msgResourceBundle = ResourceBundle.getBundle("resources.lang", locale, loader);
		
		FacesContext.getCurrentInstance().addMessage( componentID, new FacesMessage(msgResourceBundle.getString(messageKey)));
	}
	
	/**
	 * Gets message from language resource bundle properties locale defined in faces-config.xhtml
	 * 
	 * @param messageKey message key in locale language properties
	 * @return
	 */
	public static String getLangMessage(String messageKey){
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ResourceBundle msgResourceBundle = ResourceBundle.getBundle("resources.lang", locale, loader);
		
		return msgResourceBundle.getString(messageKey);
		
	}
	
	public static String getFilename(Part part) {
        // courtesy of BalusC : http://stackoverflow.com/a/2424824/281545
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
	
	

	/**
	 * Uploads profile picture on server.
	 * @param profilePicName name of the profile picture to be saved.
	 * @param profilePicPart javax.servlet.http.Part object with info about picture. 
	 * @param prop Properties file with info about file location, default name etc.
	 * @return profile picture name with extension.
	 */
	public static String uploadProfilePic(String profilePicName, Part profilePicPart, Properties prop) {
		try(InputStream in = profilePicPart.getInputStream()) {
			String dirPath = prop.getProperty("upload.profile.location");
			File dir = new File(dirPath);
			if(dir.exists()) {
				String fileName = profilePicName + ".png";
				//if no profile pic is specified
				if(JSFUtil.getFilename(profilePicPart).equals("")){
					fileName = prop.getProperty("upload.profile.default.name");
					System.out.println("naziv profilne slike: " + fileName);
					return fileName;
				}
				
				System.out.println("naziv profilne slike: " + fileName);
				if(fileName.endsWith(".jpg") || fileName.endsWith(".png")
						|| fileName.endsWith(".JPG") || fileName.endsWith(".PNG")) {
					String filePath = dirPath + File.separator + fileName;
					File f = new File(filePath);
					if (!f.exists()) {
						Files.copy(in, new File(filePath).toPath());
						System.out.println("Uploaded profile file: " + filePath);
						return fileName;
					} else {
						System.out.println("Upload file \"" + fileName + "\" already exists");
						return fileName;
					}
				} else {
					System.out.println("Wrong upload file format!");
					return null;
				}
			} else {
				System.out.println("Directory \"" + dirPath + "\" for upload does not exist");
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Something is wrong with Part file: ");
			System.out.println(profilePicName);
			return null;
		}
	}
	

	/**
	 * Uploads event picture on server.
	 * @param eventPicName name of the event picture to be saved.
	 * @param eventPicPart object with info about picture. 
	 * @param prop file with info about file location, etc.
	 * @return event picture name with extension.
	 */
	public static String uploadEventPic(Part eventPicPart, Properties prop) {
		try(InputStream in = eventPicPart.getInputStream()) {
			String dirPath = prop.getProperty("upload.event.location");
			File dir = new File(dirPath);
			if(dir.exists()) {
				String fileName = JSFUtil.getFilename(eventPicPart);	
				System.out.println("naziv slike sa dogadaja: " + fileName);
				if(fileName.endsWith(".jpg") || fileName.endsWith(".png")
						|| fileName.endsWith(".JPG") || fileName.endsWith(".PNG")) {
					String filePath = dirPath + File.separator + fileName;
					File f = new File(filePath);
					if (!f.exists()) {
						Files.copy(in, new File(filePath).toPath());
						System.out.println("Uploaded event file: " + filePath);
						return fileName;
					} else {
						System.out.println("Upload file \"" + fileName + "\" already exists");
						return fileName;
					}
				} else {
					System.out.println("Wrong upload file format!");
					return null;
				}
			} else {
				System.out.println("Directory \"" + dirPath + "\" for upload does not exist");
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Something is wrong with Part object: ");
			return null;
		}
	
	}
	
	
	

}
