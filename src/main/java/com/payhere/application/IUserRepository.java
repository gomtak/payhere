package com.payhere.application;

import com.payhere.domain.User;
import com.payhere.domain.model.UserDataModel;

public interface IUserRepository {
    UserDataModel save(UserDataModel userDataModel);

    UserDataModel find(String phoneNumber);
}
