package com.jie.file;

import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainInterfaceBean {
	/**
	 * ����֮ǰ����Ʋ���� ���µ��һЩ�հ����򲻻ᴥ���¼� ���Ծ����޲�
	 */
	private ImageButton conversation;
	private ImageButton contact;
	private ImageButton me;

	/**
	 * �޲��������relativelayout
	 * 
	 * @return
	 */

	private RelativeLayout rlConversation;
	private RelativeLayout rlContact;
	private RelativeLayout rlMe;

	public RelativeLayout getRlConversation() {
		return rlConversation;
	}

	public void setRlConversation(RelativeLayout rlConversation) {
		this.rlConversation = rlConversation;
	}

	public RelativeLayout getRlContact() {
		return rlContact;
	}

	public void setRlContact(RelativeLayout rlContact) {
		this.rlContact = rlContact;
	}

	public RelativeLayout getRlMe() {
		return rlMe;
	}

	public void setRlMe(RelativeLayout rlMe) {
		this.rlMe = rlMe;
	}

	public ImageButton getConversation() {
		return conversation;
	}

	public void setConversation(ImageButton conversation) {
		this.conversation = conversation;
	}

	public ImageButton getContact() {
		return contact;
	}

	public void setContact(ImageButton contact) {
		this.contact = contact;
	}

	public ImageButton getMe() {
		return me;
	}

	public void setMe(ImageButton me) {
		this.me = me;
	}

}
