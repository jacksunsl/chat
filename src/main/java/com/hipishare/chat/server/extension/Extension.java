package com.hipishare.chat.server.extension;

import java.util.Set;

import com.hipishare.chat.server.codec.ActionScriptObject;
import com.hipishare.chat.server.domain.User;

public abstract class Extension {
	public void init(){};
	public void destroy(){};
	
	public abstract void request(String cmd,ActionScriptObject ao,User sender,String fromRoom);

	public void sendResponse(ActionScriptObject ao,Set<User> recipients){
		for(User user:recipients){
			if(user.getChannel().isOpen()){
				user.getChannel().write(ao.getObj());
			}
		}
	}
	
	public void sendResponse(ActionScriptObject ao,User recipient){
		recipient.getChannel().write(ao.getObj());
	}
	
	protected ActionScriptObject createAsObject(int cmd){
		ActionScriptObject ao = new ActionScriptObject();
		ao.setCmd(cmd);
		return ao;
	}
}
