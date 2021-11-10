package com.revature.services;

import com.revature.models.Info;

public interface InfoService {

    public Info getInfoByProfileId(Integer id);

    public Info updateInfo(Info info);

    public Info createInfo(Info info);

}
