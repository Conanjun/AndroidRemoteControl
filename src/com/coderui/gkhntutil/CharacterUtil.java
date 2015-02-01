package com.coderui.gkhntutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.coderui.gkhntUI.R;

public class CharacterUtil {
	public static final String[] FORMFIELD = { "���񵥺�", "��ͬ��", "��λ����", "��������",
			"���̵�ַ", "ʩ����λ", "���;���", "��Ⱥ�", "�ƻ�����", "�������", "�ۼƳ���", "����Ա",
			"��������", "����ʱ��", "����״̬", "��ע" };
	public static final Integer[] IMAGES = { R.drawable.delete,
			R.drawable.backup, R.drawable.contacts, R.drawable.message,
			R.drawable.shartdown, R.drawable.restart };
	public static final String[] FUNCTIONS={"ɾ�����ݿ�","�������ݿ�","����û�","��������","�رռ����","���������"};
	/**
	 * task_id
	 */
	public static final int LOG_IN = 0;// �ͻ������ӳɹ�
	public static final int REQUESTTASTLIST = 1;// ����������������б�
	public static final int ACCEPTTASKLIST = 2;// ���շ������������б�
	public static final int SENDQUERYREQUEST = 3;// ��ͻ��˷��Ͳ�ѯ����
	public static final int ACCEPTQUERYRESULT = 4;// ���ղ�ѯ���
	public static final int DELETEDATABASE=5;//�������ݿ�
	public static final int BACKUPDATABASE=6;//�������ݿ�
	public static final int CONTROLUSERS=7;//����û��б�
	public static final int MESSAGE=8;//������Ϣ
	public static final int SHARTDOWN=9;//�رռ����
	public static final int RESTARTCOPUTER=10;//���������
	public static final int ACCPETADVANCE=11;//���ո߼�������Ӧ

	public static final int PORT = 7000;// �˿ں�

	private static final String dateFrom = "yyyy-MM-dd";// ��������ڸ�ʽ

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFrom);
		Date curentDate = new Date(System.currentTimeMillis());
		return sdf.format(curentDate);
	}
}
