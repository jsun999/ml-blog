package site.jsun999.web.Task;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import site.jsun999.common.utils.HttpClientUtil;
import site.jsun999.model.Music;
import site.jsun999.service.MusicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class StaticScheduleTask {

    @Autowired
    MusicService musicService;

    //3.添加定时任务每小时
    @Scheduled(cron = "0 */30 * * * ?")
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        log.info("music task start .....");
        List<Music> list = musicService.getList();
        list.forEach(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("music_input", a.getLink());
            map.put("music_filter", "url");
            map.put("music_type", "_");
            String str = HttpClientUtil.sendPost("http://music.xf1433.com/", map);
            Gson g = new Gson();
            JsonObject obj = g.fromJson(str, JsonObject.class);
            if (obj.get("code").getAsInt() == 200) {
                JsonArray data = g.fromJson(obj.get("data"), JsonArray.class);
                JsonElement jsonElement = data.get(0);
                JsonObject jsonObject = g.fromJson(jsonElement, JsonObject.class);
//                a.setAuthor(jsonObject.get("author").getAsString());
                a.setMusic(jsonObject.get("music") != null && !jsonObject.get("music").isJsonNull() ? jsonObject.get("music").getAsString() : null);
//                a.setType(jsonObject.get("type").getAsString());
//                a.setSongid(jsonObject.get("songid").getAsString());
//                a.setName(jsonObject.get("name").getAsString());
//                a.setPic(jsonObject.get("pic").getAsString());
            }
            this.musicService.update(a);
        });
        log.info("music task end .....");
    }
}