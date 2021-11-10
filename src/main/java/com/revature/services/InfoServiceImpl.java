package com.revature.services;

import com.revature.models.Info;
import com.revature.repositories.InfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InfoServiceImpl implements InfoService{

    @Autowired
    private InfoRepo infoRepo;


    @Override
    public Info getInfoByProfileId(Integer id) {
        return infoRepo.getInfoByProfilePid(id);
    }

    @Override
    public Info updateInfo(Info info) {
        Info newInfo = infoRepo.getInfoByProfilePid(info.getProfile().getPid());
        newInfo.setAboutMe(info.getAboutMe());
        newInfo.setPronouns(info.getPronouns());
        newInfo.setBirthday(info.getBirthday());
        return infoRepo.save(newInfo);
    }

    @Override
    public Info createInfo(Info info) {
        return infoRepo.save(info);
    }
}
