package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;

public interface MembershipService {
	
	public boolean idCheck(String userId);
	
	public String registMember(User user, String userType);
	
    public boolean removeMember(int id, int userType);                 
    
}
