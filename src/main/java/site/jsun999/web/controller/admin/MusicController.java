package site.jsun999.web.controller.admin;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.jsun999.aspect.SysLog;
import site.jsun999.common.constant.PageConstant;
import site.jsun999.common.vo.Result;
import site.jsun999.model.Music;
import site.jsun999.service.MusicService;
import site.jsun999.web.exception.GlobalException;

import javax.validation.Valid;
import java.util.List;

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
    @SysLog("保存目录")
    @PostMapping("/save")
    public Result save(@Valid Music music) {
        try {
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
