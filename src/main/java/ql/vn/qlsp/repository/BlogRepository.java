package ql.vn.qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ql.vn.qlsp.entity.BlogEntity;

public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {
    BlogEntity getAllById(int id);
}
