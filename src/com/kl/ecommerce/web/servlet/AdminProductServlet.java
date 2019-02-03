package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.service.CategoryService;
import com.kl.ecommerce.service.ProductService;
import com.kl.ecommerce.service.serviceImpl.CategoryServiceImpl;
import com.kl.ecommerce.service.serviceImpl.ProductServiceImpl;
import com.kl.ecommerce.utils.UUIDUtils;
import com.kl.ecommerce.utils.UploadUtils;
import com.kl.ecommerce.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminProductServlet", urlPatterns = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

    /**
     * 按照分页查找所有商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int curNum = Integer.parseInt(request.getParameter("num"));
        ProductService productService = new ProductServiceImpl();
        PageModel pageModel = productService.findAllProductsWithPage(curNum);
        request.setAttribute("pageModel", pageModel);
        return "/admin/product/list.jsp";
    }

    /**
     * 进入添加商品页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryService categoryService = new CategoryServiceImpl();

        List<Category> list = categoryService.getAllCates();

        request.setAttribute("allCates", list);
        return "/admin/product/add.jsp";
    }
    /**
     * 添加商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        Product product = new Product();
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
            List<FileItem> list = upload.parseRequest(request);

            for (FileItem item:
                 list) {
                if (item.isFormField()) {
                    map.put(item.getFieldName(), item.getString("utf-8"));
                } else {
                    String oldFileName = item.getName();
                    String newFileName = UploadUtils.getUUIDName(oldFileName);
                    InputStream inputStream = item.getInputStream();
                    String realPath = getServletContext().getRealPath("/products/3");
                    String dir = UploadUtils.getDir(newFileName);
                    String path = realPath + dir;
                    File newDir = new File(path);
                    if (!newDir.exists()) {
                        newDir.mkdirs();
                    }

                    File targetFile = new File(newDir, newFileName);
                    if (!targetFile.exists()) {
                        targetFile.createNewFile();
                    }
                    OutputStream outputStream = new FileOutputStream(targetFile);
                    IOUtils.copy(inputStream, outputStream);

                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);

                    map.put("pimage", "/products/3" + dir + "/" + newFileName);
                }
            }

            BeanUtils.populate(product, map);
            product.setPid(UUIDUtils.getId());
            product.setPdate(new Date(new java.util.Date().getTime()));
            product.setPflag(0);

            ProductService productService = new ProductServiceImpl();
            productService.saveProduct(product);

            response.sendRedirect("/ECommerce/AdminProductServlet?method=findAllProductsWithPage&num=1");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
