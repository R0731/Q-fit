package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;

public interface MembershipService {
	
	public String registMember(User user);
	
    public boolean removeMember(int id, String userType);                 
    
}
