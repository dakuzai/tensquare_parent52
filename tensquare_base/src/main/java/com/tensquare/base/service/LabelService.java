package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LabelService
 * @Author admin
 * @Date 2019/11/11 10:54
 **/
@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     *
     * @param label
     */
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 更新
     *
     * @return
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除
     *
     * @return
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * Specification:封装条件查询
     *
     * @param label
     * @return
     */
    public List<Label> findsearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象，也就是要把条件封装到哪个对象中。类似where 类名=label.getId
             * @param query 封装的都是查询关键字，比如group by， order by等
             * @param cb 用来封装条件对象的 如果直接返回null 表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //获得类名：root.get("labelname") 指定类型：.as(String.class)
                    cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//类似生成的sql语句：where labelneme like "%达酷%"
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    //获得类名：root.get("state") 指定类型：.as(String.class)
                    cb.like(root.get("state").as(String.class), label.getState());//类似生成的sql语句：state = "1"
                }
                //new一个数组作为最终的返回值条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转成数组
                parr = list.toArray(parr);//where labelneme like "%达酷% and state = "1"
                return cb.and(parr);
            }
        });
    }

    /**
     * 分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //获得类名：root.get("labelname") 指定类型：.as(String.class)
                    cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//类似生成的sql语句：where labelneme like "%达酷%"
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    //获得类名：root.get("state") 指定类型：.as(String.class)
                    cb.like(root.get("state").as(String.class), label.getState());//类似生成的sql语句：state = "1"
                }
                //new一个数组作为最终的返回值条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转成数组
                parr = list.toArray(parr);//where labelneme like "%达酷% and state = "1"
                return cb.and(parr);
            }
        }, pageable);
    }
}
