package ql.vn.qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ql.vn.qlsp.entity.BlogEntity;
import ql.vn.qlsp.entity.BlogRenderEntity;
import ql.vn.qlsp.repository.BlogRenderRepository;
import ql.vn.qlsp.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRenderRepository blogRenderRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CheckDateNow checkDateNow;
    @Autowired
    private UploadFile uploadFile;

    // lấy toàn bộ blog
    public Page<BlogEntity> getBlogs(Pageable pageable){

        return blogRepository.findAll(pageable);
    }

    public BlogEntity addBlog(BlogEntity blogEntityParam){
        if(blogEntityParam.getId()==null){
            return null;
        }
        return blogRepository.save(blogEntityParam);

    }

    public Page<BlogRenderEntity> getBlogRenders(Pageable pageable){

        return blogRenderRepository.findAll(pageable);
    }
    public BlogRenderEntity addBlogRender(BlogRenderEntity blogRenderEntityParam){
        if(blogRenderEntityParam.getId()==null){
            return null;
        }
        if(blogRepository.getAllById(blogRenderEntityParam.getBlog().getId())==null)
        {
            return null;
        }
        return blogRenderRepository.save(new BlogRenderEntity());


    }


}
