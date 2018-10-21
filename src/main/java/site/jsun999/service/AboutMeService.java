package site.jsun999.service;

import site.jsun999.model.AboutMe;
import site.jsun999.web.exception.GlobalException;

public interface AboutMeService extends BaseService<AboutMe> {

    AboutMe getAboutMe(Integer status) throws GlobalException;
}
