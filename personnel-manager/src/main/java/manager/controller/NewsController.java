package manager.controller;

import manager.pojo.News;
import manager.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.PageResult;
import util.Result;

/**
* @Description:    新闻信息的管理
* @Author:         2571169797   yang meng bo
* @CreateDate:     2019/8/3 0003 下午 14:11
* @UpdateUser:
* @UpdateDate:     2019/8/3 0003 下午 14:11
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("news")
public class NewsController {
   @Autowired
   private NewsService newsService;

    @PostMapping("addNews")
    public Result addNews(@RequestBody News news){
        newsService.addNews(news);
        return new Result(null);
    }

    @DeleteMapping("deleteNews/{id}")
    public Result deleteNews(@PathVariable(value = "id")long id ){
        newsService.deleteNews(id);
        return new Result(null);
    }

    @PutMapping("updateNews")
    public Result updateNews(@RequestBody News news){
        newsService.updateNews(news);
        return new Result(null);
    }

    @GetMapping("findAllNews/{rows}/{size}")
    public PageResult<News> findAllNews(@PathVariable(value = "rows")int rows,@PathVariable(value = "size")int size){
        PageResult<News> allNews = newsService.findAllNews(rows, size);
        return allNews;
    }
}
