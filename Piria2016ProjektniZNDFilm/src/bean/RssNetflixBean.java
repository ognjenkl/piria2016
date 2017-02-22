package bean;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dao.MovieDAO;
import dto.MovieDTO;
import dto.RSSTop100Item;


@ManagedBean(name="rssNetflix")
@ViewScoped
public class RssNetflixBean {

	private List<RSSTop100Item> top100List;
	private List<MovieDTO> bestRatedMoviesList;
	private String rssFeed;
	
	@PostConstruct
	public void init() {
		top100List = load();
		bestRatedMoviesList = MovieDAO.getBestRated(5);
//		int i = 1;
//		for(MovieDTO m : bestRatedMoviesList)
//			System.out.println("RssNetflixBean init: " + i++ + ". " + m.getTitle() + " " + m.getRate());
//		System.out.println();
		
		rssFeed = createRSSDocument();
		
	}
	
	
	public List<RSSTop100Item> load() {
		List<RSSTop100Item> rssTop100ItemsList = new ArrayList<>();
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("http://dvd.netflix.com/Top100RSS");
			doc.normalize();
			
//			System.out.println(doc.getChildNodes().item(0).getChildNodes().item(1).getNodeName());
			NodeList channelNodes = doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes();
//			System.out.println(channelNodes.item(1).getNodeName());
			for (int i = 0; i < channelNodes.getLength(); i++) {
				Node itemNode = channelNodes.item(i);
				if(itemNode.getNodeType() == Node.ELEMENT_NODE && itemNode.getNodeName().equals("item")){
					RSSTop100Item rssTop100Item = new RSSTop100Item();
					NodeList itemNodes = itemNode.getChildNodes();
					for (int j = 0; j < itemNodes.getLength(); j++) {
						Node itemElement = itemNodes.item(j);
//						System.out.println(i + ". item element: " + itemElement.getNodeName());
						if (itemElement.getNodeType() == Node.ELEMENT_NODE && itemElement.getNodeName().equals("title"))
							rssTop100Item.setTitle(itemElement.getTextContent());
						if (itemElement.getNodeType() == Node.ELEMENT_NODE && itemElement.getNodeName().equals("link"))
							rssTop100Item.setLink(itemElement.getTextContent());
						if (itemElement.getNodeType() == Node.ELEMENT_NODE && itemElement.getNodeName().equals("guid"))
							rssTop100Item.setGuid(itemElement.getTextContent());
						if (itemElement.getNodeType() == Node.ELEMENT_NODE && itemElement.getNodeName().equals("description")) {
							rssTop100Item.setDescription(itemElement.getTextContent());
//							System.out.println(rssTop100Item.getDescription().replaceFirst("<a.*</a><br>", ""));
							String regex = "<a.*</a><br>";
							String replaceWith = "";
							rssTop100Item.setDescription(rssTop100Item.getDescription().replaceFirst(regex, replaceWith));
							rssTop100ItemsList.add(rssTop100Item);
						}
					}
				}
			}
			
			return rssTop100ItemsList;

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rssTop100ItemsList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rssTop100ItemsList;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rssTop100ItemsList;
		}
		
	}
	
	public String createRSSDocument() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			doc.setXmlStandalone(true);
			Element rssNode = doc.createElement("rss");
			rssNode.setAttribute("version", "2.0");
//			ProcessingInstruction pi = doc.createProcessingInstruction("xml", "version=\"1.0\" encodint=\"UTF-8\" ");
			doc.appendChild(rssNode);
//			doc.insertBefore(pi, rssNode);
			Element channelNode = doc.createElement("channel");
			rssNode.appendChild(channelNode);
			Element titleNode = doc.createElement("title");
			titleNode.setTextContent("Title text contetnt");
			Element linkNode = doc.createElement("link");
			linkNode.setTextContent("Link text content");
			Element descriptionNode = doc.createElement("description");
			descriptionNode.setTextContent("Description text content");
			channelNode.appendChild(titleNode);
			channelNode.appendChild(linkNode);
			channelNode.appendChild(descriptionNode);
			
			for(MovieDTO m : bestRatedMoviesList) {
				Element itemNode = doc.createElement("item");
				Element itemTitleNode = doc.createElement("title");
				itemTitleNode.setTextContent(m.getTitle());
				Element itemDescriptionNode = doc.createElement("description");
				itemDescriptionNode.setTextContent(m.getStoryline());
				itemNode.appendChild(itemTitleNode);
				itemNode.appendChild(itemDescriptionNode);
				channelNode.appendChild(itemNode);
			}

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", 4);
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.toString());
//			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			
			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			transformer.transform(domSource, streamResult);
			String xmlString = stringWriter.toString();
			
//			PrintWriter out = new PrintWriter(
//				new BufferedWriter(
//						new FileWriter(
//							FacesContext.getCurrentInstance().
//								getExternalContext().getRequestContextPath()
//									+ "/WebContent/" + "bestRated.xml")));
//			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bestRated.xml")));
//			out.print(xmlString);
//			out.close();
			
//			System.out.println("path requestcontext: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
//			System.out.println("path real path: " + FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
//			System.out.println("path application context path: " + FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
//			System.out.println("xml string: ");
//			System.out.println();
//			System.out.println(xmlString);
			
			return xmlString;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
		
	}
	
	
	
	
	public List<RSSTop100Item> getTop100List() {
		return top100List;
	}

	public void setTop100List(List<RSSTop100Item> top100List) {
		this.top100List = top100List;
	}


	public String getRssFeed() {
		return rssFeed;
	}


	public void setRssFeed(String rssFeed) {
		this.rssFeed = rssFeed;
	}
	
	
	
}
