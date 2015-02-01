package com.coderui.gkhntutil;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import com.coderui.gkhntUI.TaskListActivity;

import android.util.Log;
import android.util.Xml;

public class XMLuntil{
	
	private static String TAG="XMLutil";
	/**
	 * ����������������
	 * <request>
	 * 		<type>1</type> 1��ʾ���������б�
	 * 		<params></params>
	 * </request>
	 * 
	 */
	public static String structRequestXml(int type,Object params){
		String xml="";
		StringWriter stringWriter=new StringWriter();
		XmlSerializer xmlSerializer=Xml.newSerializer();
		try{
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("UTF-8", true);
			xmlSerializer.startTag(xml, "request");
			xmlSerializer.startTag(xml, "type");
			xmlSerializer.text(String.valueOf(type));
			xmlSerializer.endTag(xml, "type");
			xmlSerializer.startTag(xml, "params");
			xmlSerializer.text(String.valueOf(params));
			xmlSerializer.endTag(xml, "params");
			xmlSerializer.endTag(xml, "request");
			xmlSerializer.endDocument();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return stringWriter.toString();
	}
	
	/**
	 * �����ѯ���ݵ�xml
	 * <query>
	 * 		<type>3</type>
	 * 		<querytype>queryType</querytype>
	 * 		<task>queryTaskNumber</task>
	 * 		<start>start_date</start>
	 * 		<end>end_date</end>
	 * </query>
	 * @return
	 */
	public static String constructQueryXml(int queryType,String queryTaskNumber,String start_date,String end_date){
		String xml="";
		StringWriter stringWriter=new StringWriter();
		XmlSerializer xmlSerializer=Xml.newSerializer();
		try{
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("UTF-8", true);
			xmlSerializer.startTag(xml, "query");
			xmlSerializer.startTag(xml, "type");
			xmlSerializer.text(String.valueOf(3));
			xmlSerializer.endTag(xml, "type");
			xmlSerializer.startTag(xml, "querytype");
			xmlSerializer.text(String.valueOf(queryType));
			xmlSerializer.endTag(xml, "querytype");
			xmlSerializer.startTag(xml, "task");
			xmlSerializer.text(queryTaskNumber);
			xmlSerializer.endTag(xml, "task");
			xmlSerializer.startTag(xml, "start");
			xmlSerializer.text(start_date);
			xmlSerializer.endTag(xml, "start");
			xmlSerializer.startTag(xml, "end");
			xmlSerializer.text(end_date);
			xmlSerializer.endTag(xml, "end");
			xmlSerializer.endTag(xml, "query");
			xmlSerializer.endDocument();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return stringWriter.toString();
	}
	
	//��pull������������
	public static List<Map<String,String>> extractTaskList(String xml){
		List<Map<String,String>> list=null;
		Map<String,String> map=null;
		ByteArrayInputStream is=null;
		try{
			if(xml!=null&& !xml.trim().equals("")){
				is=new ByteArrayInputStream(xml.getBytes("UTF-8"));
			}
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(is,"UTF-8");//����������
			/*
			 * pull����xml�� ��������
			 * ��ȡ��xml��������������0 START_DOCUMENT;
			 * ��ȡ��xml�Ľ�����������1 END_DOCUMENT ;
			 * ��ȡ��xml�Ŀ�ʼ��ǩ��������2 START_TAG
			 * ��ȡ��xml�Ľ�����ǩ��������3 END_TAG
			 * ��ȡ��xml���ı���������4 TEXT
			 */
			int type=parser.getEventType();
			while(type!=XmlPullParser.END_DOCUMENT){
				switch(type){
				case XmlPullParser.START_DOCUMENT:
					list=TaskListActivity.list;
					//list=new ArrayList<Map<String,String>>();
					break;
				case XmlPullParser.START_TAG:
					if("task".equals(parser.getName())){
						map=new HashMap<String,String>();
					}else if("messages".equals(parser.getName())){
						String receivetype=parser.getAttributeValue(0);
						Log.v(TAG, "��ȡmessages�ڵ�"+receivetype);
					}else{
						String element=parser.getName();
						type=parser.next();
						String elementText=parser.getText();
						map.put(element,elementText);
					}
					break;
				case XmlPullParser.END_TAG:
					if("task".equals(parser.getName())){
						list.add(map);
						map=null;
					}
					break;
				}
				type=parser.next();
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���������������������xml
	 * <message>
	 * 		<summy></summy>
	 * </message>
	 * @param xml
	 * @return
	 */
	public static String extractTaskListProduct(String xml){
		ByteArrayInputStream is=null;
		String summy="0.0";
		try{
			if(xml!=null&& !xml.trim().equals("")){
				is=new ByteArrayInputStream(xml.getBytes("UTF-8"));
			}
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(is,"UTF-8");//����������
			int type=parser.getEventType();
			while(type!=XmlPullParser.END_DOCUMENT){
				switch(type){
				case XmlPullParser.START_TAG:
					if("summy".equals(parser.getName())){
						summy=parser.getText();
					}
					break;
				}
				type=parser.next();
			}
			return summy;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
