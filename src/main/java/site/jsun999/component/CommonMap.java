package site.jsun999.component;

import site.jsun999.model.Param;
import site.jsun999.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class CommonMap extends HashMap<String,Object> {

    @Autowired
    private ParamService paramService;

    /**
     * 项目启动加载参数
     */
    @PostConstruct
    public void init() {
        List<Param> paramList = null;

        try {
            paramList = this.paramService.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Param param : paramList) {
            this.put(param.getParamName(), param.getParamValue());
        }
    }

}
