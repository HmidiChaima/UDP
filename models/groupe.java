package tp1Udp.models;

import java.util.*;

public class groupe {
	@Override
	public String toString() {
		return "groupe [list count =" + list.size() + ", titre=" + titre + "]";
	}

	public HashSet<student> list;
	public ArrayList<String> messagesList;
	public String titre;
	
	
	public groupe(String titre) {
		this.titre=titre;
		list=new HashSet<student>();
	}
	
	public void add(student s) {
		list.add(s);
	}

	public HashSet<student> getList() {
		return list;
	}

	public void setList(HashSet<student> list) {
		this.list = list;
	}

	public ArrayList<String> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(ArrayList<String> messagesList) {
		this.messagesList = messagesList;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

}
