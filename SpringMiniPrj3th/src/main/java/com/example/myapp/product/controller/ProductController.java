package com.example.myapp.product.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadImage;
import com.example.myapp.product.model.UploadProduct;
import com.example.myapp.product.service.IProductService;
import com.example.myapp.review.model.Review;
import com.example.myapp.review.service.IReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
   static final Logger logger = LoggerFactory.getLogger(ProductController.class);
   
   @Autowired
   IProductService productService;
   
   @Autowired
   IReviewService reviewService;
   
   @RequestMapping("/shop")
   public String getAllProduct(@RequestParam(required=false, defaultValue= "-1")int id, 
		   @RequestParam(required=false, defaultValue= "전체") String name , HttpSession session , Model model) {
      return getAllProduct(1, id, name, session, model);
   }
   
   @RequestMapping("/shop/{page}")
   public String getAllProduct(@PathVariable int page, @RequestParam(required=false, defaultValue= "-1")int id, 
		   @RequestParam(required=false, defaultValue= "전체") String name, HttpSession session, Model model) {
	   	  session.setAttribute("page", page);
		  List<Category> categoryList = productService.selectAllCategory();
		  int productCnt = 0;
		  if(id == -1) {
			  List<Product> list = productService.selectPagingProduct(-1, page);		
			  productCnt = productService.selectCountUseProduct(-1);
			  model.addAttribute("productList", list);
		  }else {
			  List<Product> list = productService.selectPagingProduct(id, page);
			  productCnt = productService.selectCountUseProduct(id);
			  model.addAttribute("productList", list);
		  }
		  
		  int totalPage = 0;
		  if(productCnt > 0) {
			  totalPage = (int)Math.ceil(productCnt/9.0);
		  }
		  int totalPageBlock = (int)Math.ceil(totalPage/9.0);
		  int nowPageBlock = (int)Math.ceil(page/9.0);
		  int startPage = (nowPageBlock-1)*9 + 1;
		  int endPage = 0;
		  if(totalPage > nowPageBlock * 9) {
			  endPage = nowPageBlock * 9;
		  }else {
			  endPage = totalPage;
		  }
		  model.addAttribute("totalPageCount", totalPage);
		  model.addAttribute("nowPage", page);
		  model.addAttribute("totalPageBlcok", totalPageBlock);
		  model.addAttribute("nowPageBlock", nowPageBlock);
		  model.addAttribute("startPage", startPage);
		  model.addAttribute("endPage", endPage);
		  
		  model.addAttribute("categoryId", id);
		  model.addAttribute("categoryName", name);
	      model.addAttribute("categoryList", categoryList);
	   return "/product/shop";
   }
   
   @RequestMapping("/file/{fileId}")
   public ResponseEntity<byte[]> getFile(@PathVariable int fileId){
      UploadImage file = productService.getFile(fileId);
      logger.info("getFile" + file.toString());
      final HttpHeaders headers = new HttpHeaders();
      String[] mtypes = file.getImageType().split("/");
      headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
      headers.setContentLength(file.getImageSize());
      try {
         String encodedFileName = URLEncoder.encode(file.getImageName(), "UTF-8");
         headers.setContentDispositionFormData("attachment", encodedFileName);
      } catch(UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
      return new ResponseEntity<byte[]>(file.getImageData(), headers, HttpStatus.OK);
      
   }
   
   @RequestMapping(value="/product/productInsert", method=RequestMethod.GET)
   public String productFactory(Model model) {
	  List<Category> list = productService.selectAllCategory();
	  List<Product> productList = productService.selectAllProduct(-1);
	  List<Category> categoryPagingList = productService.selectPagingCategory(0, 3);
	  int cCount = productService.selectCountCategory();
	  int totalPage = 0;
	  if(cCount > 0) {
		  totalPage = (int)Math.ceil(cCount/3);
	  }
	  if(cCount % 3 > 0) {
		  totalPage++;
	  }

	  model.addAttribute("totalPage", totalPage);
	  model.addAttribute("categoryList", list);
	  model.addAttribute("productList", productList);
	  model.addAttribute("categoryPagingList", categoryPagingList);
	  return "product/productAdministration"; 
   }
   

   
   @RequestMapping(value="/product/productManage", method=RequestMethod.GET)
   public String manage(HttpSession session, Model model) {
	  int state = (int) session.getAttribute("userState");
	  if(state == 2) {
		 List<Category> list = productService.selectAllCategory();
		 List<Product> productList = productService.selectAllProduct(-1);
		 model.addAttribute("categoryList", list);
		 model.addAttribute("productList", productList);
		 return "product/product";
	  }else {
		  return "/index";
	  }
 
   }
   
//  @RequestMapping(value="/category/insert", method=RequestMethod.POST)
 //  public String insertCategory(String categoryName) {
//	   System.out.println(categoryName);
//	  int cnt = productService.checkCategory(categoryName);
//	  if(cnt > 0) {
//		  return "Error";
		  //model.addAttribute("sameNameError", "Error");
//	  }else {
//		  int row = productService.insertCategory(categoryName);
//		  return "OK";
		  //model.addAttribute("sameNameError", "OK");
//	  }
      //return productFactory(model);
//   }
   
   @RequestMapping(value = "/category/insert", method = RequestMethod.POST)
   public ResponseEntity<String> insertCategory(@RequestParam("categoryName") String categoryName) {
       int cnt = productService.checkCategory(categoryName);
       if (cnt > 0) {
           return ResponseEntity.ok("Error");
       } else {
           int row = productService.insertCategory(categoryName);
           return ResponseEntity.ok("Success");
       }
   }
   
   @RequestMapping(value="/category/paging", method=RequestMethod.GET)
   public ResponseEntity<List<Category>> categoryPaging(Model model, @RequestParam("page") int page) {
	   int min = (page - 1) * 3;
	   int max = page * 3;
	   List<Category> list = productService.selectPagingCategory(min, max);
	   return ResponseEntity.ok(list);
   }
   
   @RequestMapping(value="/category/delete", method=RequestMethod.POST)
   public String deleteCategory(@RequestParam List<Integer> categoryId, Model model) {
      if(categoryId.size() > 0) {   
         for(int i=0; i<categoryId.size(); i++) {
            int row = productService.deleteCategory(categoryId.get(i));            
         }
      }
      return productFactory(model);
   }
   
   @RequestMapping(value="/product/insert", method=RequestMethod.POST)
   public String insertProduct(UploadProduct product, RedirectAttributes redirectAttrs) throws IOException {
      logger.info(product.getFile().getOriginalFilename());
      try {
         MultipartFile mfile = product.getFile();
         if(mfile != null && !mfile.isEmpty()) {
            UploadImage uploadImage = new UploadImage();
            uploadImage.setImageName(mfile.getOriginalFilename());
            uploadImage.setImageSize(mfile.getSize());
            uploadImage.setImageType(mfile.getContentType());
            uploadImage.setImageData(mfile.getBytes());
            productService.insertProducts(product, uploadImage);
         }else {
            productService.insertProducts(product);
         }
      }catch(Exception e) {
         logger.error(e.getMessage());
         redirectAttrs.addFlashAttribute("message", e.getMessage());
      }
      return "redirect:/product/productManage";
   }
   
   @RequestMapping(value="/product/update", method=RequestMethod.POST)
   public String updateProduct(UploadProduct product, RedirectAttributes redirectAttrs) throws IOException{
      logger.info(product.getFile().getOriginalFilename());
      try {
         MultipartFile mfile = product.getFile();
         if(mfile != null && !mfile.isEmpty()) {
            UploadImage uploadImage = new UploadImage();
            uploadImage.setImageName(mfile.getOriginalFilename());
            uploadImage.setImageSize(mfile.getSize());
            uploadImage.setImageType(mfile.getContentType());
            uploadImage.setImageData(mfile.getBytes());
            int row = productService.updateProduct(product, uploadImage);
         }else {
            int row = productService.updateProduct(product);
         }
      }catch(Exception e) {
         logger.error(e.getMessage());
         redirectAttrs.addFlashAttribute("message", e.getMessage());
      }
      return "redirect:/product/productManage";
   }
   
   @RequestMapping(value="/product/delete", method=RequestMethod.POST)
   public String deleteProduct(@RequestParam(required=false, defaultValue="") List<Integer> productId) {
      if(productId.size() > 0) {
         for(int i=0; i<productId.size(); i++) {
            int row = productService.deleteProduct(productId.get(i));
         }
      }
      return "redirect:/product/productManage";
   }
   
   @RequestMapping(value="/product/deleteBack", method=RequestMethod.POST)
   public String deleteBackProduct(@RequestParam(required=false, defaultValue="") List<Integer> productId) {
      if(productId.size() > 0) {
         for(int i=0; i<productId.size(); i++) {
            int row = productService.deleteBackProduct(productId.get(i));
         }
      }
      return "redirect:/product/productManage";
   }
   
   @RequestMapping(value="/product/update/{productId}", method=RequestMethod.GET)
   public String updateProduct(@PathVariable int productId) {
      Product product = productService.selectProduct(productId);
      return "redirect:/product/productManage";
   }

   @RequestMapping(value="/product/productDetail/{productId}", method=RequestMethod.GET)
   public String selectProduct(@PathVariable int productId, Model model) {
      Product product = productService.selectProduct(productId);
      List<Review> reviewList = reviewService.selectReviewList(productId);
      model.addAttribute("product", product);
      model.addAttribute("reviewList", reviewList);
      return "/product/shop-single";
   }
}
