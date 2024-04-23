package ql.vn.qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ql.vn.qlsp.dto.ProductEntityDto;
import ql.vn.qlsp.entity.ImagesEntity;
import ql.vn.qlsp.entity.ProductEntity;
import ql.vn.qlsp.repository.ImagesRepository;
import ql.vn.qlsp.repository.ProductRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private CheckDateNow checkDateNow;


    @Transactional // tạo 1 giao dịch nếu trường hợp hàm chạy có vấn đề rollback về trạng thái chưa chạy (
    public ProductEntity addProduct(ProductEntityDto productEntityDto){
        if(productEntityDto.getId()!= null)
        {
            return new ProductEntity();
        }
        productEntityDto.setCreateAt(checkDateNow.getDateNow());
        productEntityDto.setUpdateAt(checkDateNow.getDateNow());
        ProductEntity productEntity = new ProductEntity(productEntityDto);
//        System.out.println("datadto"+productEntityDto.toString());
        if(productRepository.save(productEntity)!=null){ // lưu product thành công
            List<ImagesEntity> images = new ArrayList<>();
            for(ImagesEntity e : productEntityDto.getImages()){
                // cần thêm một hàm chuyển đổi list file sang list text rồi set cho dto (có lẽ đéo cần chỉ cần bắt người dùng chuyển img sang text từ fontend)
                e.setIdProduct(productEntity.getId());
                images.add(e);
            }
            imagesRepository.saveAll(images);
            return productEntity;
        }
        return null;


    }

    public Page<ProductEntity> getProductByPageNumber(Pageable pageable){

        return productRepository.findAll(pageable);

    }

    public Page<ProductEntity> getProductPageByBrandId(int id, Pageable pageable){
        return productRepository.findAllByBrand_Id(id,pageable);
    }

    public Page<ProductEntity> getProductPageBytagId(int id, Pageable pageable){
        return productRepository.findAllByTag_Id(id,pageable);
    }

    public Page<ProductEntity> getProductByName(String name, Pageable pageable){
        return productRepository.findAllByNameContaining(name, pageable);
    }

}
