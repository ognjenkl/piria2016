package util;

import java.util.Locale;
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

}
