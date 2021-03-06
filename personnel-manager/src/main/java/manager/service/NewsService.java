package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import manager.mapper.NewsMapper;
import manager.pojo.News;
import manager.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.Code;
import util.PException;
import util.PageResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Description:    新闻信息服务层管理
* @Author:         2571169797   yang meng bo
* @CreateDate:     2019/8/3 0003 下午 13:37
* @UpdateUser:
* @UpdateDate:     2019/8/3 0003 下午 13:37
* @UpdateRemark:   修改内容
* @Version:        1.0
*/

@Service
public class NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private UserService userService;

    public void addNews(News news){

        news.setUid(userService.get().getUid());
        news.setCreateTime(new Date());
        news.setStatus("1");
        news.setUpdateTime(new Date());
        newsMapper.insert(news);
    }

    public void deleteNews(Long uid){
        News news = newsMapper.selectByPrimaryKey(uid);
        if(news == null){
            throw new PException(Code.NEWS_NOT_EXIST,"新闻信息不存在");
        }
        news.setStatus("0");
        newsMapper.updateByPrimaryKey(news);
    }

    public void updateNews(News news){

        if(newsMapper.selectByPrimaryKey(news) == null){
            throw new PException(Code.NEWS_NOT_EXIST,"新闻信息不存在");
        }
        news.setUpdateTime(new Date());
        newsMapper.updateByPrimaryKey(news);
    }

    public PageResult<News> findAllNews(int rows, int size){
        PageHelper.startPage(rows,size);
        Example example = new Example(News.class);
        example.setOrderByClause("create_time desc");//降序
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Page<News> newsList = (Page<News>)newsMapper.selectByExample(example);
        List<News> news = packageUser(newsList.getResult());
        return new PageResult<News>(newsList.getTotal(), news);

    }

    private List<News> packageUser(List<News> result) {
        List<News> targetLists = new ArrayList<>();
        for (News news : result) {
            User user = userService.findByUid(news.getUid());
            news.setAuthorName(user.getName());
            targetLists.add(news);
        }
        return targetLists;
    }

    public News findById(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if(news == null){
            throw new PException(Code.NEWS_NOT_EXIST,"新闻信息不存在");
        }
        return news;
    }
}
