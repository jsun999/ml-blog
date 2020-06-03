package site.jsun999.service.impl;

import site.jsun999.mapper.BaseMapper;
import site.jsun999.service.BaseService;
import site.jsun999.web.exception.GlobalException;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract BaseMapper<T> getBaseMapper();

    @Override
    public void save(T model)  throws GlobalException {
        this.getBaseMapper().insertSelective(model);
    }

    @Override
    public void update(T model) throws GlobalException  {
        this.getBaseMapper().updateByPrimaryKeySelective(model);
    }

    @Override
    public void delete(Integer id)  throws GlobalException  {
        this.getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public T getById(Integer id)  throws GlobalException  {
        return this.getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> getList()  throws GlobalException  {
        return this.getBaseMapper().selectAll();
    }

    @Override
    public List<T> getPyPage(Integer pageNum, Integer pageSize)   throws GlobalException {
        PageHelper.startPage(pageNum,pageSize);
        return this.getList();
    }

    @Override
    public List<T> getPyExamplePage(Example example, Integer pageNum, Integer pageSize)  throws GlobalException {
        PageHelper.startPage(pageNum,pageSize);
        return this.getBaseMapper().selectByExample(example);
    }
}
