package  org.jmask.web.controller.user;

import java.util.HashMap;

import flex.messaging.FlexContext;

public class resultManager {
	public HashMap<String, Object> getResult(){
		return userManager.getInstance().getUser(FlexContext.getFlexClient().getId()).getResult();
	}
}
