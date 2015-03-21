package com.pinsheng.mode;

public class Person {
	private String name;
	private String tel;
	private boolean phone;
	private boolean message;
	private String message_content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean isPhone() {
		return phone;
	}
	public void setPhone(boolean phone) {
		this.phone = phone;
	}
	public boolean isMessage() {
		return message;
	}
	public void setMessage(boolean message) {
		this.message = message;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public void updateData(Person person){
		name = person.name;
		tel = person.tel;
		phone = person.phone;
		message = person.message;
		message_content = person.message_content;
	}
	
	@Override
	public boolean equals(Object o) {
		if(name.equals(((Person)o).name)&&tel.equals(((Person)o).tel)){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public int hashCode() {
		return Integer.parseInt(tel);
	}

}
