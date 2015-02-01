package com.coderui.gkhntDispatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.coderui.gkhntLogic.LogicThread;
import com.coderui.gkhntUI.ModelActivity;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;

@SuppressLint("HandlerLeak")
public class DispatchService extends Service {
	String TAG="DispatchService";
	public static Queue<Task> tasks = new LinkedList<Task>();// �����б�
	public static List<Activity> activities = new ArrayList<Activity>();
	public boolean isRun;// �Ƿ������߳�

	private static Socket socket;
	private static InputStream is;
	private static OutputStream os;
	private ModelActivity model;
	
	//������Ϣ��������
	private void sendInfo(String xml){
		try {
			DispatchService.os.write(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�������Է�����������
	private void acceptInfo(Message msg){
		try {
			byte[] buf=new byte[20480];
			int length=is.read(buf);
			String receive=new String(buf,0,length,"UTF-8");
//			String receive = "";
//			int messageSize=0;
//			byte[] buffer = new byte[1024];
//			while ((messageSize = is.read(buffer)) != -1) {
//				Log.v(TAG,String.valueOf(is.read(buffer)));
//				String xml = new String(buffer, 0, messageSize, "UTF-8");
//				receive = receive + xml;
//				Log.v(TAG,"xmlxml");
//			}
			Log.v(TAG,"���յ���Ϣ");
			msg.obj = receive;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// ������Ϣ��ͬ����ͬ���߼�ҵ��
			switch (msg.what) {
			case CharacterUtil.LOG_IN:
				model = (ModelActivity) getActivityByName("GhkntActivity");
				model.refreshUI(msg.obj);
				break;
			case CharacterUtil.ACCEPTTASKLIST:
				model = (ModelActivity) getActivityByName("TaskListActivity");
				model.refreshUI(msg.obj);
				break;
			case CharacterUtil.ACCEPTQUERYRESULT:
				model = (ModelActivity) getActivityByName("QueryDetialActivity");
				Log.v(TAG,"ˢ��UI");
				model.refreshUI(msg.obj);
			case CharacterUtil.ACCPETADVANCE:
				model = (ModelActivity) getActivityByName("AdvancelActivity");
				Log.v(TAG,"ˢ��UI");
				model.refreshUI(msg.obj);
			default:
				break;
			}
		}
	};

	// ��activity���뵽�б���
	public static void addActivity(Activity a) {
		activities.add(a);
	}

	// ����activity�����ֻ�ȡactivity�Ķ���
	public Activity getActivityByName(String name) {
		if (activities != null) {
			for (Activity activity : activities) {
				if (activity != null) {
					if (activity.getClass().getName().indexOf(name) > 0) {
						activities.remove(0);//�Ƴ�activity��������ܻ����Ѿ��˳���activity
						return activity;
					}
				}
			}
		}
		return null;
	}

	// ������������ж���
	public static void addTask(Task t) {
		tasks.add(t);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		isRun = true;
		Thread thread = new LogicThread(this);
		thread.start();
	}

	// ��������,���ظ�����Ϣ�����߳�
	public void doTask(Task t) {
		Message msg = handler.obtainMessage();
		msg.what = t.getTask_id();
		Map<String, Object> taskParams = new HashMap<String, Object>();
		switch (t.getTask_id()) {
		/**
		 * ��½��������
		 */
		case CharacterUtil.LOG_IN:
			taskParams = t.getTaskParams();
			String ipAddress = (String) taskParams.get("ipAdress");
			int port = (Integer) taskParams.get("port");
			try {
				DispatchService.socket = new Socket(ipAddress, port);
				DispatchService.is = socket.getInputStream();
				DispatchService.os = socket.getOutputStream();
				DispatchService.os.write("Client has connected".getBytes("UTF-8"));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		/**
		 * ���������б�
		 */
		case CharacterUtil.REQUESTTASTLIST:
			taskParams = t.getTaskParams();
			String send_xml = (String) taskParams.get("xml");
			sendInfo(send_xml);
			break;
		/**
		 * ���շ���������Ϣ
		 */
		case CharacterUtil.ACCEPTTASKLIST:
			acceptInfo(msg);
			break;
			/**
			 * ���Ͳ�ѯ��������
			 */
		case CharacterUtil.SENDQUERYREQUEST:
			taskParams = t.getTaskParams();
			String query_xml = (String) taskParams.get("xml");
			sendInfo(query_xml);
			break;
			/**
			 * ���շ������Ĳ�ѯ���
			 */
		case CharacterUtil.ACCEPTQUERYRESULT:
			acceptInfo(msg);
			break;
		case CharacterUtil.ACCPETADVANCE:
			acceptInfo(msg);
			break;
		default:
			break;
		}
		handler.sendMessage(msg);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
