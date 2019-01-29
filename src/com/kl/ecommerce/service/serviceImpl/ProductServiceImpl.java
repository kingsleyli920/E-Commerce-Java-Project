package com.kl.ecommerce.service.serviceImpl;

import com.kl.ecommerce.dao.ProductDao;
import com.kl.ecommerce.dao.daoImpl.ProductDaoImpl;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = new ProductDaoImpl();

    @Override
    public List<Product> findHots() throws Exception {
        return productDao.findHots();
    }

    @Override
    public List<Product> findNews() throws Exception {
        return productDao.findNews();
    }

    @Override
    public Product findProductByPid(String pid) throws Exception {
        return productDao.findProductByPid(pid);
    }

    @Override
    public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
        int totalRecord = productDao.findTotalProductsByCid(cid);
        PageModel pageModel = new PageModel(curNum, totalRecord, 12);

        List list = productDao.findProductByCidWithPage(cid, pageModel.getStartIndex(), pageModel.getPageSize());
        pageModel.setList(list);
        pageModel.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=" + cid);
        return pageModel;
    }
}
