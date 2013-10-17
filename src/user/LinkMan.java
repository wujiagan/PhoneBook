package user;

public class LinkMan {
	
	public final static int 
		DEFAULT = 0,
		FAMILY = 1,
		FRIEND = 2,
		COLLEAGUE = 3,
		STRANGER = 4;
		
	
	private String name;
	private String mobilePhone;
	private String address;
	private String email;
	private int ground;
	private String remark;
	
	public LinkMan(){
		
	}
	
	public  LinkMan(String[] info){
		this.ground = Integer.parseInt(info[0]);
		if(this.ground < 0 || this.ground > 4)
			this.ground = 0;
		this.name = info[1];
		this.mobilePhone = info[2];
		this.email = info[3];
		this.address = info[4];
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGround() {
		return ground;
	}
	public void setGround(int ground) {
		this.ground = ground;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int hashCode() {
		return this.name.hashCode() * this.address.hashCode() ;
	}
	
	public boolean equals(Object anObject) {
		if (this == anObject) {
		    return true;
		}
		if (anObject instanceof LinkMan) {
		    LinkMan anotherLinkMan = (LinkMan)anObject;
			if(this.name.equals(anotherLinkMan.getName())
					&& this.address.equals(anotherLinkMan.getAddress())
					&& this.mobilePhone.equals(anotherLinkMan.getMobilePhone())
					&& this.email.equals(anotherLinkMan.getEmail())
					&& this.ground == anotherLinkMan.getGround())
			return true;
		    }
		return false;
	}
	
	public String toString(){
		return this.name + "," + this.mobilePhone + "," + 
			this.email + "," + this.address ;
	}
	
	public Object[] toArray(){
		String[] profile = {this.ground + "", this.name, this.mobilePhone, this.email , this.address};
		return profile;
	}

}
