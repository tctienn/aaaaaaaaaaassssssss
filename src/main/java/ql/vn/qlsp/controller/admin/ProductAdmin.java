package ql.vn.qlsp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ql.vn.qlsp.dto.ProductEntityDto;
import ql.vn.qlsp.entity.ProductEntity;
import ql.vn.qlsp.repository.ProductRepository;
import ql.vn.qlsp.service.ProductService;
import ql.vn.qlsp.service.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin/product")
public class ProductAdmin {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UploadFile uploadFile;

    @PostMapping("add-product")
    public ProductEntity getProducts(@RequestBody ProductEntityDto productEntityDto){

//        System.out.printf("data: "+ productEntityDto.toString());
                return productService.addProduct(productEntityDto);
//        return new ProductEntity();


    }

    @PostMapping("upload-file-image")
    public String uploadFileImage( @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {

        return uploadFile.uploadFile(multipartFile);

    }
    @PostMapping("remove-file-image-by-name")
    public String removeFIleImage( @RequestParam String fileName) throws IOException {
        return uploadFile.removeFile(fileName);
    }
}
