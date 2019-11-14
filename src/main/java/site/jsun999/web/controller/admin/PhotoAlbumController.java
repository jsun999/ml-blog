package site.jsun999.web.controller.admin;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.jsun999.aspect.SysLog;
import site.jsun999.common.constant.ParamConstant;
import site.jsun999.common.utils.ParamUtil;
import site.jsun999.common.vo.Result;
import site.jsun999.component.CommonMap;
import site.jsun999.component.FileService;
import site.jsun999.service.PhotoAlbumService;
import site.jsun999.web.exception.GlobalException;

import java.io.IOException;
import java.util.List;

import static site.jsun999.common.constant.UserConstant.blog_prefix;

@RestController
@RequestMapping("/admin/photoAlbum")
@Slf4j
public class PhotoAlbumController {
    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommonMap commonMap;

    /**
     * 相册文件上传
     * @param files
     * @return
     * @throws IOException
     */
    @SysLog("uploadPhotos 文件上传")
    @PostMapping("/uploadPhotos")
    @ResponseBody
    public Result uploadPhotos(List<MultipartFile> files){
        try {

            if (!ParamUtil.checkParameter(ParamConstant.QINIU)) {
                throw new GlobalException(500,"未配置七牛云参数");
            }

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file =  files.get(i);

                Response response = this.fileService.upload(file.getInputStream(), blog_prefix+file.getOriginalFilename());
                if (!response.isOK()) {
                    log.error("文件上传失败 -> url:{},Response:{}","/uploadfile",response);
                    throw new GlobalException(500,"文件上传失败");
                }
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String resultKey = putRet.key == null ? putRet.hash : putRet.key;
            }
            return Result.success();

        } catch (GlobalException e) {
            throw new GlobalException(500,e.getMessage());
        } catch (Exception e) {
            throw new GlobalException(500,e.toString());
        }
    }
}
