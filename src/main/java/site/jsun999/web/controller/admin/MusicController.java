package site.jsun999.web.controller.admin;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.jsun999.aspect.SysLog;
import site.jsun999.common.constant.PageConstant;
import site.jsun999.common.utils.HttpClientUtil;
import site.jsun999.common.vo.Result;
import site.jsun999.model.Music;
import site.jsun999.service.MusicService;
import site.jsun999.web.exception.GlobalException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 音乐相关
 */
@RestController
@RequestMapping("/admin/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 分页列表
     * @param pageNum
     * @return
     */
    @GetMapping("/list/{pageNum}")
    public Result list(@PathVariable Integer pageNum) {
        try {
            List<Music> list = this.musicService.getPyPage(pageNum, PageConstant.PAGE_SIZE);
            return Result.success(new PageInfo<>(list,10));
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    @GetMapping("/listAll")
    public Result listAll() {
        try {
            List<Music> list = this.musicService.getList();
            return Result.success(list);
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 获取信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Integer id) {
        try {
            Music music = this.musicService.getById(id);
            return Result.success(music);
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 保存
     * @param music
     * @return
     */
    @SysLog("保存音乐")
    @PostMapping("/save")
    public Result save(@Valid Music music) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("music_input", music.getLink());
            map.put("music_filter", "url");
            map.put("music_type", "_");
            String str = HttpClientUtil.sendPost("http://music.xf1433.com/", map);
            Gson g = new Gson();
            JsonObject obj = g.fromJson(str, JsonObject.class);
            if (obj.get("code").getAsInt() == 200) {
                JsonArray data = g.fromJson(obj.get("data"), JsonArray.class);
                JsonElement jsonElement = data.get(0);
                JsonObject jsonObject = g.fromJson(jsonElement, JsonObject.class);
                music.setAuthor(jsonObject.get("author").getAsString());
                music.setMusic(jsonObject.get("music") != null && !jsonObject.get("music").isJsonNull()? jsonObject.get("music").getAsString() : null);
                music.setType(jsonObject.get("type").getAsString());
                music.setSongid(jsonObject.get("songid").getAsString());
                music.setName(jsonObject.get("name").getAsString());
                music.setPic(jsonObject.get("pic").getAsString());
            }
            if (music.getId() == null || music.getId() == 0) {
                this.musicService.save(music);
            } else {
                this.musicService.update(music);
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysLog("删除目录")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            this.musicService.delete(id);
            return Result.success();
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }
}
