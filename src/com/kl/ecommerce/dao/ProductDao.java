package com.kl.ecommerce.dao;

import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findHots() throws Exception;

    List<Product> findNews() throws Exception;

    Product findProductByPid(String pid) throws Exception;

    int findTotalProductsByCid(String cid) throws Exception;

    List findProductByCidWithPage(String cid, int startIndex, int pageSize) throws Exception;
}
