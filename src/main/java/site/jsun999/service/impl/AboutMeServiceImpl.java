package site.jsun999.service.impl;

import site.jsun999.mapper.AboutMeMapper;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.model.AboutMe;
import site.jsun999.service.AboutMeService;
import site.jsun999.web.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutMeServiceImpl extends BaseServiceImpl<AboutMe> implements AboutMeService {

    @Autowired
    private AboutMeMapper aboutMeMapper;

    @Override
    public BaseMapper<AboutMe> getBaseMapper() throws GlobalException {
        return this.aboutMeMapper;
    }

    @Override
    public AboutMe getAboutMe(Integer status) throws GlobalException {
        return this.aboutMeMapper.getByStatus(status);
    }
}
