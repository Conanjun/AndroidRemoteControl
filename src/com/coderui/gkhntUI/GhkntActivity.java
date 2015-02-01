package com.coderui.gkhntUI;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coderui.gkhntDispatch.DispatchService;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;

public class GhkntActivity extends Activity implements ModelActivity {
	private Button btn_link;
	private EditText ipaddress;
	private Map<String, Object> taskParams;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		init();
		taskParams = new HashMap<String, Object>();

		ipaddress = (EditText) findViewById(R.id.ipaddress);
		ipaddress.setText("169.254.171.11");
		btn_link = (Button) findViewById(R.id.button1);
		taskParams.put("ipAdress", ipaddress.getText().toString());
		taskParams.put("port", CharacterUtil.PORT);
		btn_link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_link.setText("�Ͽ�������");
				// ��������뵽�����ж���
				Task task = new Task(CharacterUtil.LOG_IN, taskParams);
				DispatchService.addTask(task);
			}
		});
		DispatchService.addActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_contrl_speed, menu);
		return true;
	}

	@Override
	public void init() {
		// ��������
		Intent service_intent = new Intent(GhkntActivity.this, DispatchService.class);
		startService(service_intent);
	}

	@Override
	public void refreshUI(Object... objects) {
		// TODO Auto-generated method stub
		Toast.makeText(GhkntActivity.this, "���������ӳɹ�", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(GhkntActivity.this,OperatorDataActivity.class);
		startActivity(intent);
	}
}
