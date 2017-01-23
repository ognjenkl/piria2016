package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dto.RSSTop100Item;


@ManagedBean(name="rssNetflix")
@ViewScoped
public class RssNetflixBean {

	List<RSSTop100Item> top100List;

	
	@PostConstruct
	public void init() {
		top100List = load();
		int i = 1;
		for (RSSTop100Item rssTop100Item : top100List) {
			System.out.println(i+++ ". " + rssTop100Item.getTitle());
		}
	}
	
	public List<RSSTop100Item> load() {
		List<RSSTop100Item> rssTop100ItemsList = new ArrayList<>();
//		BufferedReader in = null;
//		try {
//			in = new BufferedReader(new InputStreamReader(new URL("http://dvd.netflix.com/Top100RSS").openStream()));
//			String line = null;
//			StringBuilder sb = new StringBuilder();
//			while ((line = in.readLine()) != null)
//				sb.append(line);
//			
//			System.out.println(sb.toString());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			rssTop100ItemsList = null;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			rssTop100ItemsList = null;
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//		}
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
	
	
	
	
	public List<RSSTop100Item> getTop100List() {
		return top100List;
	}

	public void setTop100List(List<RSSTop100Item> top100List) {
		this.top100List = top100List;
	}
	
	
	
}
