package com.pinsheng.dialog;

import java.util.Map;

import com.pinsheng.app.HELPApllication;
import com.pinsheng.help.HELPFragment;
import com.pinsheng.help.MainActivity;
import com.pinsheng.help.R;
import com.pinsheng.mode.Person;
import com.pinsheng.util.Util;
import com.slider.slider_switch.Slider_Switch;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogSetupFragment extends DialogFragment implements OnClickListener {
	private ImageView mAddressImageView;
	private TextView mDeleteButton;
	private TextView mSaveButton;
	private Slider_Switch mPhoneSwitch;
	private Slider_Switch mMessageSwitch;
	private EditText mNameEdit;
	private EditText mPhoneEdit;
	private EditText mMessageContent;
	private Person mNewperson;
	private Person person;
	private Intent intent;
	private String mGetTag;
	
	private static final String ADDTAG = "0";

	public DialogSetupFragment(Person person) {
		this.person = person;
		mNewperson = new Person();
		if(person!=null){
			mNewperson.updateData(person);
		}
	}

	public DialogSetupFragment(){
		this(null);
	}

	@Override
	public void onResume() {
		super.onResume();
		getDialog().getWindow().setLayout(Util.getScreenWidth(getActivity()) - Util.dip2px(getActivity(), 16), LayoutParams.WRAP_CONTENT);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.set_up_dialog, container, false);
		mPhoneSwitch = (Slider_Switch) view.findViewById(R.id.phone_switch);
		mMessageSwitch = (Slider_Switch) view.findViewById(R.id.message_switch);
		mAddressImageView = (ImageView) view.findViewById(R.id.phone_address);
		mDeleteButton = (TextView) view.findViewById(R.id.phone_delete);
		mSaveButton = (TextView) view.findViewById(R.id.phone_save);
		mNameEdit = (EditText) view.findViewById(R.id.name_edit);
		mPhoneEdit = (EditText) view.findViewById(R.id.phone_edit);
		mMessageContent = (EditText) view.findViewById(R.id.message_content);
		
		mGetTag = this.getTag();
		initView();
		return view;
	}

	private void initView() {
		mNameEdit.setText(mNewperson.getName());
		mPhoneEdit.setText(mNewperson.getTel());
		if (mNewperson.getName() != null && mNewperson.getTel() != null) {
			mNameEdit.setSelection(mNewperson.getName().length());
			mPhoneEdit.setSelection(mNewperson.getTel().length());
		}
		
		if (mNewperson.getMessage_content() != null) {
			mMessageContent.setHint("当前："+mNewperson.getMessage_content());
		} else {
			mMessageContent.setHint("当前："+"我可能遇到了危险，现在不方便打电话，如果10分钟内我没联系你，请帮助我!");
		}
		mPhoneSwitch.setChecked(mNewperson.isPhone());
		mMessageSwitch.setChecked(mNewperson.isMessage());
		mAddressImageView.setOnClickListener(this);
		mDeleteButton.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);
		
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		return dialog;
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.phone_address:
			intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setData(ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, 1);
			break;
		case R.id.phone_delete:
			((HELPApllication)(getActivity().getApplication())).deletPerson(mNewperson);
			dismiss();
			break;
		case R.id.phone_save:
			mNewperson.setName(mNameEdit.getText().toString());
			if (!TextUtils.isEmpty(mMessageContent.getText().toString())) {
				mNewperson.setMessage_content(mMessageContent.getText().toString());
			} else {
				if (TextUtils.isEmpty(mNewperson.getMessage_content())) {
					mNewperson.setMessage_content("我可能遇到了危险，现在不方便打电话，如果10分钟内我没联系你，请帮助我!");
				}
			}
			mNewperson.setTel(mPhoneEdit.getText().toString());
			mNewperson.setMessage(mMessageSwitch.isChecked());
			mNewperson.setPhone(mPhoneSwitch.isChecked());
			if (TextUtils.isEmpty(mNewperson.getName())) {
				Util.Toast(getActivity(), "请补全姓名！！");
			} else if (TextUtils.isEmpty(mNewperson.getTel())) {
				Util.Toast(getActivity(), "请补全电话！！");
			} else {
				if(mNewperson.isPhone()){
					((HELPApllication)(getActivity().getApplication())).cleanPersonPhone();
				}
				if (mGetTag.equals(ADDTAG)) {
					((HELPApllication)(getActivity().getApplication())).addPerson(mNewperson);
				} else {
					if (!((HELPApllication)(getActivity().getApplication())).JudgeHavePeople(mNewperson)) {
						InitializePerson(person);
						Log.i("cao", mNewperson.getName());
						((HELPApllication)(getActivity().getApplication())).insteadPerson(mNewperson);
					}
				}
				dismiss();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		//((HELPFragment) ((MainActivity)getActivity()).getFragmentManager().findFragmentByTag("help")).refreshPerson();
		if(MainActivity.helpFragment!=null){
			MainActivity.helpFragment.refreshPerson();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Uri result = data.getData();
			Map<String,String> map = Util.getPhoneContacts(getActivity(), result);
			mNameEdit.setText(map.get("name"));
			mPhoneEdit.setText(map.get("number"));
			mNameEdit.setSelection(map.get("name").length());
			mPhoneEdit.setSelection(map.get("number").length());
			Person newperson = ((HELPApllication)(getActivity().getApplication())).findPerson(map.get("name"), map.get("number"));
			if(newperson!=null){
				mNewperson = newperson;
				initView();
			}
			
		}
	}
	
	private void InitializePerson(Person person) {
		person.setName("");
		person.setTel("");
	}

}
