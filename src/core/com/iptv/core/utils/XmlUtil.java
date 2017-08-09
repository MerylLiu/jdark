package com.iptv.core.utils;

import java.util.Map;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class XmlUtil {
	/**
	 * xml转map 不带属性
	 * 
	 * @param xmlStr
	 * @param needRootKey
	 *            是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = xml2map(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map;
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 带属性
	 * 
	 * @param xmlStr
	 * @param needRootKey
	 *            是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xml2mapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = xml2mapWithAttr(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map; // 根节点只有一个文本内容
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 不带属性
	 * 
	 * @param e
	 * @return
	 */
	private static Map xml2map(Element e) {
		Map map = new LinkedHashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = xml2map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * xml转map 带属性
	 * 
	 * @param e
	 * @return
	 */
	private static Map xml2mapWithAttr(Element element) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		List<Element> list = element.elements();
		List<Attribute> listAttr0 = element.attributes(); // 当前节点的所有属性的list
		for (Attribute attr : listAttr0) {
			map.put("@" + attr.getName(), attr.getValue());
		}
		if (list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				Element iter = list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = xml2mapWithAttr(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {

					List<Attribute> listAttr = iter.attributes(); // 当前节点的所有属性的list
					Map<String, Object> attrMap = null;
					boolean hasAttributes = false;
					if (listAttr.size() > 0) {
						hasAttributes = true;
						attrMap = new LinkedHashMap<String, Object>();
						for (Attribute attr : listAttr) {
							attrMap.put("@" + attr.getName(), attr.getValue());
						}
					}

					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							// mapList.add(iter.getText());
							if (hasAttributes) {
								attrMap.put("#text", iter.getText());
								mapList.add(attrMap);
							} else {
								mapList.add(iter.getText());
							}
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							// mapList.add(iter.getText());
							if (hasAttributes) {
								attrMap.put("#text", iter.getText());
								mapList.add(attrMap);
							} else {
								mapList.add(iter.getText());
							}
						}
						map.put(iter.getName(), mapList);
					} else {
						// map.put(iter.getName(), iter.getText());
						if (hasAttributes) {
							attrMap.put("#text", iter.getText());
							map.put(iter.getName(), attrMap);
						} else {
							map.put(iter.getName(), iter.getText());
						}
					}
				}
			}
		} else {
			// 根节点的
			if (listAttr0.size() > 0) {
				map.put("#text", element.getText());
			} else {
				map.put(element.getName(), element.getText());
			}
		}
		return map;
	}

	/**
	 * map转xml map中没有根节点的键
	 * 
	 * @param map
	 * @param rootName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement(rootName);
		doc.add(root);
		map2xml(map, root);
		// System.out.println(doc.asXML());
		// System.out.println(formatXml(doc));
		return doc;
	}

	/**
	 * map转xml map中含有根节点的键
	 * 
	 * @param map
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document map2xml(Map<String, Object> map) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("Data");
		doc.add(root);

		map2xml(map, root);

		return doc;
	}

	@SuppressWarnings("unused")
	private static Document map2xml(Map.Entry<String, Object> entry, Document doc) {
		String key = entry.getKey();
		Object value = entry.getValue();

		Element body = DocumentHelper.createElement(entry.getKey());
		doc.add(body);

		if (key.startsWith("@")) { // 属性
			body.addAttribute(key.substring(1, key.length()), value.toString());
		} else if (key.equals("#text")) { // 有属性时的文本
			body.setText(value.toString());
		} else {
			if (value instanceof java.util.List) {
				List list = (List) value;
				Object obj;
				for (int i = 0; i < list.size(); i++) {
					obj = list.get(i);
					// list里是map或String，不会存在list里直接是list的，
					if (obj instanceof java.util.Map) {
						Element subElement = body.addElement(key);
						map2xml((Map) list.get(i), subElement);
					} else {
						body.addElement(key).setText((String) list.get(i));
					}
				}
			} else if (value instanceof java.util.Map) {
				Element subElement = body.addElement(key);
				map2xml((Map) value, subElement);
			} else {
				body.addElement(key).setText(value.toString());
			}
		}

		return doc;
	}

	/**
	 * map转xml
	 * 
	 * @param map
	 * @param body
	 *            xml元素
	 * @return
	 */
	private static Element map2xml(Map<String, Object> map, Element body) {
		Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.startsWith("@")) { // 属性
				body.addAttribute(key.substring(1, key.length()), value.toString());
			} else if (key.equals("#text")) { // 有属性时的文本
				body.setText(value.toString());
			} else {
				if (value instanceof java.util.List) {
					List list = (List) value;
					Object obj;
					Element subElement = body.addElement(key);
					for (int i = 0; i < list.size(); i++) {
						obj = list.get(i);
						// list里是map或String，不会存在list里直接是list的，
						if (obj instanceof java.util.Map) {
							map2xml((Map) list.get(i), subElement);
						} else {
							body.addElement(key).setText((String) list.get(i));
						}
					}
				} else if (value instanceof java.util.Map) {
					Element subElement = body.addElement(key);
					map2xml((Map) value, subElement);
				} else {
					body.addElement(key).setText(value.toString());
				}
			}
			// System.out.println("Key = " + entry.getKey() + ", Value = " +
			// entry.getValue());
		}
		return body;
	}

	public static Document list2xml(List<Map> data) {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("Data");
		doc.add(root);

		Element rows = root.addElement("Rows");
		for (Map item : data) {
			item2xml(item, rows);
		}

		return doc;
	}

	private static Element item2xml(Map<String, Object> map, Element body) {
		Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
		body= body.addElement("Row");

		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.startsWith("@")) { // 属性
				body.addAttribute(key.substring(1, key.length()), value.toString());
			} else if (key.equals("#text")) { // 有属性时的文本
				body.setText(value.toString());
			} else {
				if (value instanceof java.util.List) {
					List list = (List) value;
					Object obj;
					Element subElement = body.addElement(key);
					subElement = subElement.addElement("Rows");

					for (int i = 0; i < list.size(); i++) {
						obj = list.get(i);
						// list里是map或String，不会存在list里直接是list的，
						if (obj instanceof java.util.Map) {
							item2xml((Map) list.get(i), subElement);
						} else {
							body.addElement(key).setText((String) list.get(i));
						}
					}
				} else if (value instanceof java.util.Map) {
					Element subElement = body.addElement(key);
					map2xml((Map) value, subElement);
				} else {
					body.addElement(key).setText(value.toString());
				}
			}
			// System.out.println("Key = " + entry.getKey() + ", Value = " +
			// entry.getValue());
		}
		return body;
	}

	/**
	 * 格式化输出xml
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXml(String xmlStr) {
		Document document;
		try {
			document = DocumentHelper.parseText(xmlStr);
			String res = formatXml(document);
			return res;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化输出xml
	 * 
	 * @param document
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXml(Document document) {
		// 格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		// 格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		// 将document写入到输出流
		try {
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer.toString();
	}

	// public static void main(String[] args) {
	// Map map = new HashMap();
	// map.put("result", true);
	//
	// Map map1 = new HashMap();
	// map1.put("test", true);
	// Map map2 = new HashMap();
	// map2.put("test2", true);
	// List list = new ArrayList();
	// list.add(map1);
	// list.add(map2);
	//
	// map.put("data", list);
	//
	// String dd = list2xml(list).asXML();
	// System.out.println(dd);
	// }
}
