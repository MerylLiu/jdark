package com.iptv.app.Utils;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class LocXmlUtil {

	public static String BuildXml(Map data){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		xml += "  <Objects>";
		xml += "     <Object ElementType=\"Program\" ID=\"1001\" Action=\"REGIST\" Code=\"code1001\">";
		xml += "	    <Property Name=\"Name\">迷尚</Property>";
		xml += "	    <Property Name=\"Language\">中文</Property>";
		xml += "	    <Property Name=\"Type\">时尚生活</Property>";
		xml += "	    <Property Name=\"DefinitionFlag\">2<Property>";
		xml += "	    <Property Name=\"ReleaseYear\">2017<Property>";
		xml += "	    <Property Name=\"Description\">迷尚德国酸菜捞</Property>";
		xml += "	 </Object>";
		xml += "     <Object ElementType=\"Movie\" ID=\"2001\" Action=\"REGIST\" Code=\"code2001\">";
		xml += "	    <Property Name=\"Type\">1</Property>";
		xml += "	    <Property Name=\"FileURL\">ftp://ftpuser:111111@115.28.77.76:22000/test1.mp4<Property>";
		xml += "	 </Object>";
		xml += "     <Object ElementType=\"Movie\" ID=\"2002\" Action=\"REGIST\" Code=\"code2002\">";
		xml += "	    <Property Name=\"Type\">2</Property>";
		xml += "	    <Property Name=\"FileURL\">ftp://ftpuser:111111@115.28.77.76:22000/test1.mp4<Property>";
		xml += "	 </Object>";
		xml += "     <Object ElementType=\"Picture\" ID=\"3001\" Action=\"REGIST\" Code=\"code3001\">";
		xml += "	    <Property Name=\"FileURL\">ftp://ftpuser:111111@115.28.77.76:22000/test.png<Property>";
		xml += "	 </Object>";
		xml += "  </Objects>";
		xml += "  <Mappings>";
		xml += "      <Mapping ParentType=\"Program\" ParentID=\"1001\" ElementType=\"Movie\" ElementID=\"2001\" ParentCode=\"code1001\" ElementCode=\"code2001\" Action=\"REGIST\"></Mapping>";
		xml += "      <Mapping ParentType=\"Program\" ParentID=\"1001\" ElementType=\"Movie\" ElementID=\"2002\" ParentCode=\"code1001\" ElementCode=\"code2002\" Action=\"REGIST\"></Mapping>";
		xml += "      <Mapping ParentType=\"Picture\" ParentID=\"3001\" ElementType=\"Program\" ElementID=\"1002\" ParentCode=\"code3001\" ElementCode=\"code1002\" Action=\"REGIST\">";
		xml += "	    <Property Name=\"Type\">2</Property>";
		xml += "	    <Property Name=\"Sequence\">123</Property>";
		xml += "      </Mapping>";
		xml += "  </Mappings>";

		return null;
	}
}
