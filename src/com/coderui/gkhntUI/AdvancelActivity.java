package com.coderui.gkhntUI;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.coderui.gkhntDispatch.DispatchService;
import com.coderui.gkhntUIadapter.AdvanceAdapter;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;
import com.coderui.gkhntutil.XMLuntil;

public class AdvancelActivity extends Activity implements ModelActivity{
	String TAG="AdvancelActivity";
	private GridView gv_advance;
	private AdvanceAdapter adapter;
	private Map<String,Object> taskParams;
	/**
	 * 0.ɾ�����ݿ�
	 * 1.�������ݿ�
	 * 2.��ȡ�������
	 * 3.��Ϣ����
	 * 4.���������
	 * 5.�رռ����
	 */
	private int task_id=0;//ѡ�������
	private String message=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advance);
		
		gv_advance=(GridView) findViewById(R.id.gv_advance);
		adapter=new AdvanceAdapter(this);
		gv_advance.setAdapter(adapter);
		
		gv_advance.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				task_id=arg2;
				if(task_id+5==CharacterUtil.MESSAGE){
					final EditText editText=new EditText(AdvancelActivity.this);
					new AlertDialog.Builder(AdvancelActivity.this).setTitle("������Ϣ����")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(editText)
					.setPositiveButton("ȷ��", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							message=editText.getText().toString();
							Toast.makeText(AdvancelActivity.this, "���ڷ�����Ϣ...", Toast.LENGTH_SHORT).show();
						}
					})
					.setNegativeButton("ȡ��", null).show();
				}else{
					Toast.makeText(AdvancelActivity.this, "���ڷ�������...", Toast.LENGTH_SHORT).show();
					init();
				}
				Log.v(TAG,"ѡ����"+task_id);
				
			}
		});
		
	}
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		DispatchService.addActivity(this);
		String xml=XMLuntil.structRequestXml(task_id+5, message);
		taskParams = new HashMap<String, Object>();
		taskParams.put("xml", xml);
		// task_id==CharacterUtil.SENDQUERYREQUEST
		Task task_send = new Task(CharacterUtil.SENDQUERYREQUEST, taskParams);
		DispatchService.addTask(task_send);
		//task_id=CharacterUtil.ACCEPTQUERYRESULT
		Task task_accept =new Task(CharacterUtil.ACCPETADVANCE,null);
		DispatchService.addTask(task_accept);
	}

	@Override
	public void refreshUI(Object... objects) {
		// TODO Auto-generated method stub
		String xml = (String) objects[0];
		Toast.makeText(AdvancelActivity.this, xml, Toast.LENGTH_SHORT).show();
	}
}
