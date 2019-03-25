package com.dd.blog;

public class Test {

	public static void main(String[] args) {
		/*
		 * String unformattedXml = "<html><body><p>debi</p></body></html>";
		 * 
		 * System.out.println(new Test().format(unformattedXml));
		 */
	}

	/*
	 * public String format(String unformattedXml) { try { final Document document =
	 * parseXmlFile(unformattedXml);
	 * 
	 * OutputFormat format = new OutputFormat(document); format.setLineWidth(65);
	 * format.setIndenting(true); format.setIndent(2); Writer out = new
	 * StringWriter(); XMLSerializer serializer = new XMLSerializer(out, format);
	 * serializer.serialize(document);
	 * 
	 * return out.toString(); } catch (IOException e) { throw new
	 * RuntimeException(e); } }
	 * 
	 * private Document parseXmlFile(String in) { try { DocumentBuilderFactory dbf =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder db =
	 * dbf.newDocumentBuilder(); InputSource is = new InputSource(new
	 * StringReader(in)); return db.parse(is); } catch (ParserConfigurationException
	 * e) { throw new RuntimeException(e); } catch (SAXException e) { throw new
	 * RuntimeException(e); } catch (IOException e) { throw new RuntimeException(e);
	 * } }
	 */

}
