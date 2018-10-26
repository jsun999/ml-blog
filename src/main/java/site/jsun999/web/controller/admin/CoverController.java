package site.jsun999.web.controller.admin;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.jsun999.aspect.SysLog;
import site.jsun999.common.constant.PageConstant;
import site.jsun999.common.vo.Result;
import site.jsun999.model.Cover;
import site.jsun999.service.CoverService;
import site.jsun999.web.exception.GlobalException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/cover")
public class CoverController {

    @Autowired
    private CoverService coverService;

    /**
     * 分页列表
     * @param pageNum
     * @return
     */
    @GetMapping("/list/{categoryId}/{pageNum}")
    public Result list(@PathVariable Integer categoryId, @PathVariable Integer pageNum, String title) {
        try {
            List<Cover> list = this.coverService.getPyCategoryId(categoryId,pageNum, PageConstant.PAGE_SIZE, title);
            return Result.success(new PageInfo<>(list,10));
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
            Cover cover = this.coverService.getById(id);
            return Result.success(cover);
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 保存
     * @param cover
     * @return
     */
    @SysLog("保存文章")
    @PostMapping("/save")
    public Result save(@Valid Cover cover) {
        try {
            if (cover.getId() == null || cover.getId() == 0) {
                this.coverService.save(cover);
            } else {
                this.coverService.update(cover);
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
    @SysLog("删除文章")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            this.coverService.delete(id);
            return Result.success();
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    @SysLog("批量删除文章")
    @PostMapping("/deleteBatch/{ids}")
    public Result deleteBatch(@PathVariable String ids) {
        try {
            this.coverService.deleteBatch(ids);
            return Result.success();
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }
}
