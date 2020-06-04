package site.jsun999.web.controller;

import site.jsun999.common.constant.PageConstant;
import site.jsun999.common.utils.IPUtil;
import site.jsun999.common.utils.JsonUtil;
import site.jsun999.common.utils.MarkdownUtil;
import site.jsun999.common.utils.ParamUtil;
import site.jsun999.common.vo.*;
import site.jsun999.component.GeetestService;
import site.jsun999.component.LuceneService;
import site.jsun999.model.*;
import site.jsun999.service.*;
import site.jsun999.web.exception.GlobalException;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * 博客主页相关
 */
@Controller
public class PortalController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AboutMeService aboutMeService;
    @Autowired
    private LuceneService luceneService;
    @Autowired
    private GeetestService geetestService;
    @Autowired
    private CoverService coverService;
    @Autowired
    private MusicService musicService;
    /**
     * 首页列表
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/index.html","index"})
    public String index(Model model) throws Exception {
        List<Post> postList = this.postService.getListPyPage(1, PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
//        model.addAttribute("pageInfo", this.getPageVo(PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE, postList));
        model.addAttribute("pageInfo", new PageInfo<>(postList, 10));
        List<Cover> coverList = this.coverService.getListPyPage(1,PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
        if(coverList.size()>5){
            model.addAttribute("coverInfo", coverList.subList(0,5));
        }else{
            model.addAttribute("coverInfo", coverList.subList(0,coverList.size()));
        }
        return render(model, "portal/indexNew");
    }

    @GetMapping("/page/{pageNum}/")
    public String page(@PathVariable Integer pageNum, Model model) throws Exception {
        List<Post> postList = this.postService.getListPyPage(1, pageNum, PageConstant.PAGE_SIZE);
        model.addAttribute("pageInfo", new PageInfo<>(postList, 10));
//        model.addAttribute("pageInfo", this.getPageVo(PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE, postList));
        List<Cover> coverList = this.coverService.getListPyPage(1,PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
        if(coverList.size()>5){
            model.addAttribute("coverInfo", coverList.subList(0,5));
        }else{
            model.addAttribute("coverInfo", coverList.subList(0,coverList.size()));
        }
        return render(model, "portal/indexNew");
    }

    /**
     * 归档列表
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/archives/")
    public String archive(Model model) throws Exception {
        List<Post> postList = this.postService.getArchiveList();
        model.addAttribute("pageInfo", this.getArchivePageVo(PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE, postList));
        return render(model, "portal/archiveNew");
    }

    @GetMapping(value = "/archives/page/{pageNum}/")
    public String archive(@PathVariable("pageNum") Integer pageNum, Model model) throws Exception {
        List<Post> postList = this.postService.getArchiveList();
        model.addAttribute("pageInfo", this.getArchivePageVo(pageNum, PageConstant.PAGE_SIZE, postList));
        return render(model, "portal/archiveNew");
    }

    /**
     * 分类列表
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/categories/")
    public String category(Model model) throws Exception {

        List<Map<String, Object>> categoryList = this.categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return render(model, "portal/categoryNew");
    }


    @GetMapping(value = "/categories/{categoryName}/")
    public String categoryList(@PathVariable String categoryName, Model model) throws Exception {
        List<Post> postList = this.postService.queryByCategory(categoryName, PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
        model.addAttribute("pageInfo", new PageInfo<>(postList, 10));
        model.addAttribute("name", categoryName);
        return render(model, "portal/postlistNew");
    }

    @GetMapping("/categories/{categoryName}/page/{pageNum}/")
    public String categoryListPage(@PathVariable String categoryName, @PathVariable Integer pageNum, Model model) throws Exception {
        List<Post> postList = this.postService.queryByCategory(categoryName, pageNum, PageConstant.PAGE_SIZE);
        model.addAttribute("pageInfo", new PageInfo<>(postList, 10));
        model.addAttribute("name", categoryName);
        return render(model, "portal/postlistNew");
    }

    /**
     * 文章内容，URL 的配置格式是为了兼容 hexo
     *
     * @param model
     * @return
     */
    @GetMapping("/{year}/{month}/{day}/{title}/")
    public String post(@PathVariable("year") String year,
                       @PathVariable("month") String month,
                       @PathVariable("day") String day,
                       @PathVariable("title") String title,
                       Model model) throws Exception {

        String postUrl = year + "/" + month + "/" + day + "/" + title + "/";
        Post post = this.postService.getByPostUrl(postUrl);

        if (post == null || post.getStatus() == 0) {
            model.addAttribute("msg", "该文章不存在");
            return render(model, "portal/error");
        }

        Post previous = this.postService.getPreviousInfo(post.getId());
        Post next = this.postService.getNextInfo(post.getId());

        model.addAttribute("post", post);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("md", MarkdownUtil.class);

        return render(model, "portal/detailNew");
    }


    /**
     * 搜索
     *
     * @param keyword
     * @param model
     * @return
     */
    @GetMapping("/search/")
    public String search(String keyword,     Model model) throws Exception {
        if (StringUtils.isEmpty(keyword)) {
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageInfo", new PageVo(PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE, 0, null));
            return render(model, "portal/search");
        }
        // 从数据库中查询
//        List<Post> postList = this.postService.queryByKeyworld(keyword.trim());
        // 使用 lucene 查询
        try {
            PageVo pageVo = this.luceneService.queryByPage(keyword.trim(), PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
            model.addAttribute("pageInfo", pageVo);
        } catch (GlobalException e) {
            model.addAttribute("pageInfo", new PageVo(PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE, 0, null));
        }
        model.addAttribute("keyword", keyword.trim());
        return render(model, "portal/search");
    }

    @GetMapping("/search/page/{pageNum}/")
    public String search(@PathVariable("pageNum") int pageNum, String keyword, Model model) throws Exception {
        if (StringUtils.isEmpty(keyword)) {
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageInfo", null);
            return render(model, "portal/search");
        }
        // 使用 lucene 查询
        try {
            PageVo pageVo = this.luceneService.queryByPage(keyword.trim(), pageNum, PageConstant.PAGE_SIZE);
            model.addAttribute("pageInfo", pageVo);
        } catch (GlobalException e) {
            model.addAttribute("pageInfo", new PageVo(pageNum, PageConstant.PAGE_SIZE, 0, null));
        }
        model.addAttribute("keyword", keyword.trim());
        return render(model, "portal/search");
    }

    /**
     * 留言板
     *
     * @param model
     * @return
     */
//    @GetMapping("/comment/")
//    public String comment(Model model) throws Exception {
//        List<Comment> list = this.commentervice.getListPyPage(0,1, PageConstant.PAGE_NUM, PageConstant.PAGE_SIZE);
//        Integer totalCount = this.commentervice.getTotalCount(0);
//        model.addAttribute("pageInfo", new PageInfo<>(list, 10));
//        model.addAttribute("totalCount",totalCount);
//        return render(model, "portal/comment");
//    }
//
//    @GetMapping("/comment/page/{pageNum}/")
//    public String comment(@PathVariable Integer pageNum, Model model) throws Exception {
//        List<Comment> list = this.commentervice.getListPyPage(0,1, pageNum, PageConstant.PAGE_SIZE);
//        Integer totalCount = this.commentervice.getTotalCount(0);
//        model.addAttribute("pageInfo", new PageInfo<>(list, 10));
//        model.addAttribute("totalCount",totalCount);
//        return render(model, "portal/comment");
//    }

    /**
     * 留言板 发言
     *
     * @param comment
     * @return
     */
    @PostMapping("/postComment")
    @ResponseBody
    public Result saveComment(@Valid Comment comment, HttpServletRequest request) throws Exception {
        comment.setIp(IPUtil.getIpAddr(request));
        String city = IPUtil.getCity(comment.getIp());
        comment.setIpAddr(city == null ? "未知" : city);
        this.commentService.save(comment);
        return Result.success();
    }
    @GetMapping("/commentList/{postId}/{pageNum}")
    @ResponseBody
    public Result getCommentByPostId(@PathVariable("postId") Integer postId , @PathVariable("pageNum") int pageNum){
        List<Comment> commentList = this.commentService.getByPostId(postId, pageNum, PageConstant.PAGE_SIZE);
        return Result.success(this.getPageVo(pageNum, PageConstant.PAGE_SIZE, commentList));
    }

    private PageVo<List<?>> getPageVo(Integer pageNum, Integer pageSize, List<?> commentList) {
        if (commentList.isEmpty()) {
            return new PageVo(pageNum, pageSize, commentList.size(), null);
        }

        // 逻辑分页
        int start = (pageNum - 1) * pageSize;

        if (start > commentList.size()) {
            return new PageVo(pageNum, pageSize, commentList.size(), null);
        }

        int end;
        end = getEnd(pageSize, start, commentList.size());
        List<?> subList = commentList.subList(start, end);
        PageVo result= new PageVo(pageNum, pageSize, commentList.size(), subList);
        return result;
    }

    private int getEnd(Integer pageSize, int start, int size) {
        int end;
        if ((size - start) > pageSize) {
            end = start + pageSize;
        } else {
            int tmp = (size - start);
            if (tmp % pageSize == 0) {
                end = start + pageSize;
            } else {
                end = start + tmp;
            }
        }
        return end;
    }

    /**
     * 获取极验验证码
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getCaptcha")
    @ResponseBody
    public Result getCaptcha(HttpServletRequest request) throws Exception {

        if (!ParamUtil.checkParameter(3)) {
            throw new GlobalException(500, "未配置极验参数");
        }

        String captchaStr = this.geetestService.StartCaptcha(request);
        GeetestVO geetestVO = JsonUtil.string2Obj(captchaStr, GeetestVO.class);
        return Result.success(geetestVO); // 返回极验验证码
    }

    /**
     * 留言板 发言(极验)
     *
     * @param comment
     * @return
     */
//    @PostMapping("/comment-gt")
//    @ResponseBody
//    public Result saveCommentGt(@Valid Comment comment, HttpServletRequest request) throws Exception {
//
//        if (!ParamUtil.checkParameter(ParamConstant.GEETEST)) {
//            throw new GlobalException(500, "未配置极验参数");
//        }
//
//        if (!this.geetestService.verifyCaptcha(request)) {
//            throw new GlobalException(500, "验证错误");
//        }
//
//        comment.setIp(IPUtil.getIpAddr(request));
//        String city = IPUtil.getCity(comment.getIp());
//        comment.setIpAddr(city == null ? "未知" : city);
//        this.commentService.save(comment);
//        return Result.success();
//    }

    /**
     * 关于我
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/about/")
    public String aboutMe(Model model) throws Exception {
        AboutMe aboutMe = this.aboutMeService.getAboutMe(1);
        model.addAttribute("aboutMe", aboutMe);
        model.addAttribute("md", MarkdownUtil.class);
        return render(model, "portal/aboutMeNew");
    }


    @GetMapping("/music/")
    public String music(Model model) throws Exception {
        return render(model, "portal/music");
    }

    @GetMapping(value = "/game/")
    public String game(Model model) throws Exception {
        return render(model, "portal/game");
    }

    @GetMapping("/album/")
    public String album(Model model) throws Exception {
        return render(model, "portal/album");
    }

    @GetMapping("/musicAll/{album}")
    @ResponseBody
    public Result listAll(@PathVariable String album) {
        try {
            List<MusicVo> list = this.musicService.getList(album);
            return Result.success(list);
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    @GetMapping("/postList")
    @ResponseBody
    public Result postList() throws Exception{
        List<Post> allPostList = postService.getAllPostList();
        return Result.success(allPostList);
    }



    private String render(Model model, String path) {
        model.addAttribute("menu", path.substring(path.indexOf("/") + 1, path.length()));
        return path;
    }

    private PageVo getArchivePageVo(Integer pageNum, Integer pageSize, List<Post> postList) {

        if (postList.isEmpty()) {
            return new PageVo(pageNum, pageSize, postList.size(), null);
        }

        // 逻辑分页
        int start = (pageNum - 1) * pageSize;

        if (start > postList.size()) {
            return new PageVo(pageNum, pageSize, postList.size(), null);
        }

        int end;
        end = getEnd(pageSize, start, postList.size());

        List<Post> subList = postList.subList(start, end);

        // 通过日期分组
        Map<String, List<PostVo>> map = new LinkedHashMap<>();
        PostVo postVo;
        for (Post post : subList) {
            postVo = new PostVo();
            postVo.setId(post.getId())
                    .setTitle(post.getTitle())
                    .setPublishDate(post.getPublishDate())
                    .setPostUrl(post.getPostUrl())
                    .setImgUrl(post.getImgUrl());

            String key = post.getYear() + "-" + post.getMonth();

            if (map.containsKey(key)) {
                map.get(key).add(postVo);
            } else {
                List<PostVo> list = new ArrayList<>();
                list.add(postVo);
                map.put(key, list);
            }

        }

        return new PageVo(pageNum, pageSize, postList.size(), map);
    }
}
