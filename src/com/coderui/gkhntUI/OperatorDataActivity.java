package com.coderui.gkhntUI;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class OperatorDataActivity extends TabActivity {
	private TabHost myTabhost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operator_data_xml);
		
		myTabhost = getTabHost();
		Intent intent_task_list = new Intent(this, TaskListActivity.class);
		createTab("����",this.getResources().getDrawable(R.drawable.task_list),intent_task_list);
		Intent intent_query=new Intent(this,QueryDetialActivity.class);
		createTab("��ѯ",this.getResources().getDrawable(R.drawable.search_task_detial),intent_query);
		Intent intent_advance=new Intent(this,AdvancelActivity.class);
		createTab("�߼�",this.getResources().getDrawable(R.drawable.advance),intent_advance);
		Intent intent_settings=new Intent(this,SettingsActivity.class);
		createTab("����",this.getResources().getDrawable(R.drawable.settings),intent_settings);
	}

	private void createTab(String intentname,Drawable drawable,Intent intent){
		myTabhost.addTab(myTabhost.newTabSpec(intentname).setIndicator(intentname, drawable).setContent(intent));
	}
}
