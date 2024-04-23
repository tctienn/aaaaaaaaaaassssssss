package ql.vn.qlsp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ql.vn.qlsp.dto.TopUser;
import ql.vn.qlsp.entity.InvoiceEntity;
import ql.vn.qlsp.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("invoice/get-invoices-by-week")
    public List<InvoiceEntity> getCurrentUserName() {
        return invoiceService.getInvoiceByweek();
    }

    @GetMapping("invoice/get-invoices")
    public Page<InvoiceEntity> getInvoices(Pageable pageable){
        return invoiceService.getInvoices(pageable);
    }

    @GetMapping("invoice/get-top-user")
    public List<TopUser> getTopUsers(){
        return invoiceService.getTopUserPay();
    }



}
