package com.shard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shard.domain.ItemVO;
import com.shard.service.CartService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/order/*")
@Log4j
public class CartController {

   @Autowired
   private CartService cartService;

   @PostMapping("/cart")
   @PreAuthorize("isAuthenticated()")
   public void cart(@RequestParam String email, Model model) {
      log.info("email >>>>" + email);
      model.addAttribute("email", email);

      Integer cartNum = cartService.getCartnum(email);
      if(cartNum == null) {
         cartService.cartInsert(email);
      }
      log.info("cartNum >>" + cartNum);
      model.addAttribute("cartNum", cartNum);

      // cart에 있는 상품 정보 가져오기
      cartNum = cartService.getCartnum(email);
      List<ItemVO> ivo = cartService.detailCartItems(cartNum);
      List<Integer> sales = cartService.extractSales(ivo);
      model.addAttribute("itemList", ivo);
      log.info("itemList>>" + ivo);
      
      List<Integer> itemNumList = cartService.detailCartItemstest(cartNum);
      model.addAttribute("itemNumList", itemNumList);
      log.info("itemNumList >>" + itemNumList);
      
        // 중복을 제거한 itemNum 리스트
        List<Integer> uniqueItemNum = new ArrayList<>(new HashSet<>(itemNumList));

        // itemNum과 sales를 매칭한 Map 생성
        Map<Integer, Integer> itemNumToSaleMap = new HashMap<>();
        for (int i = 0; i < uniqueItemNum.size(); i++) {
            int currentItemNum = uniqueItemNum.get(i);
            int currentSale = sales.get(i % sales.size());
            itemNumToSaleMap.put(currentItemNum, currentSale);
        }

        // 매칭된 결과로 saleList 생성
        List<Integer> saleList = new ArrayList<>();
        for (Integer currentItemNum : itemNumList) {
            saleList.add(itemNumToSaleMap.get(currentItemNum));
        }
        log.info("saleList >>" + saleList);
        model.addAttribute("sale",saleList);
        
      int memNum = cartService.getMembership("email10@gmail.com");
      log.info("getMembership view >>" + cartService.getMembership("email10@gmail.com"));

      int pointRate = cartService.getPointRate(memNum);
      int deliveryCharge = cartService.deliveryCharge(memNum);
      log.info("getPointRate view >>" + pointRate);
      log.info("deliveryCharge view >>" + deliveryCharge);
      model.addAttribute("deliveryCharge", deliveryCharge);
      model.addAttribute("pointRate", pointRate);

      List<Integer> extractCartItemCnts = cartService.extractCartItemCnts(cartNum);
      //List<Integer> sales = cartService.extractSales(ivo);
      List<String> cartSizes = cartService.extractCartItemSize(cartNum);
      log.info("extractCartItemCnts >>" + extractCartItemCnts);
      log.info("sales >>" + sales);
      model.addAttribute("extractCartItemCnts", extractCartItemCnts);
      model.addAttribute("sales", sales);
      model.addAttribute("sizes", cartSizes);

      // 인덱스의 범위를 생성하고, 각 인덱스에 대해 두 리스트를 곱하여 새로운 리스트를 생성
      List<Integer> totalPriceList = cartService.totalPriceList(saleList, extractCartItemCnts);
      log.info("totalPriceList" + totalPriceList);
      model.addAttribute("totalPriceList", totalPriceList);

      int totalPrice = cartService.calculateTotalPrice(saleList, extractCartItemCnts);
      log.info("totalPrice >>" + totalPrice);
      model.addAttribute("totalPrice", totalPrice);

      int TotalPriceWithShipping = deliveryCharge + totalPrice;
      log.info(TotalPriceWithShipping);
      model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);

      List<Integer> itemRewardPoints = cartService.itemRewardPoints(saleList, pointRate, extractCartItemCnts);
      log.info("itemRewardPoints >>" + itemRewardPoints);

      model.addAttribute("itemRewardPoints", itemRewardPoints);
   }

   @PostMapping("/addCart")
   @PreAuthorize("isAuthenticated()")
   public String addCart(@RequestParam String email, @RequestParam int itemNum,
                     @RequestParam List<Integer> cnts,
                     @RequestParam List<String> sizes, Model model) {
      System.out.println("addCart 들어옴");
      System.out.println(email);
      System.out.println(itemNum);
      System.out.println(cnts);
      System.out.println(sizes);
      
      // detailCart추가
      cartService.initCart(email, itemNum, cnts, sizes);
      
      log.info("email >>>>" + email);
      model.addAttribute("email", email);
      Integer cartNum = cartService.getCartnum(email);
      if(cartNum == null) {
         cartService.cartInsert(email);
      }
      log.info("cartNum >>" + cartNum);
      model.addAttribute("cartNum", cartNum);

      // cart에 있는 상품 정보 가져오기
      cartNum = cartService.getCartnum(email);
      List<ItemVO> ivo = cartService.detailCartItems(cartNum);
      List<Integer> sales = cartService.extractSales(ivo);
      model.addAttribute("itemList", ivo);
      log.info("itemList>>" + ivo);
      
      List<Integer> itemNumList = cartService.detailCartItemstest(cartNum);
      model.addAttribute("itemNumList", itemNumList);
      log.info("itemNumList >>" + itemNumList);
      
        // 중복을 제거한 itemNum 리스트
        List<Integer> uniqueItemNum = new ArrayList<>(new HashSet<>(itemNumList));

        // itemNum과 sales를 매칭한 Map 생성
        Map<Integer, Integer> itemNumToSaleMap = new HashMap<>();
        for (int i = 0; i < uniqueItemNum.size(); i++) {
            int currentItemNum = uniqueItemNum.get(i);
            int currentSale = sales.get(i % sales.size());
            itemNumToSaleMap.put(currentItemNum, currentSale);
        }

        // 매칭된 결과로 saleList 생성
        List<Integer> saleList = new ArrayList<>();
        for (Integer currentItemNum : itemNumList) {
            saleList.add(itemNumToSaleMap.get(currentItemNum));
        }
        log.info("saleList >>" + saleList);
        System.out.println("itemNum: " + uniqueItemNum);
        System.out.println("sales: " + sales);
        System.out.println("saleList: " + saleList);
        model.addAttribute("sale",saleList);
        
      int memNum = cartService.getMembership("email10@gmail.com");
      log.info("getMembership view >>" + cartService.getMembership("email10@gmail.com"));

      int pointRate = cartService.getPointRate(memNum);
      int deliveryCharge = cartService.deliveryCharge(memNum);
      log.info("getPointRate view >>" + pointRate);
      log.info("deliveryCharge view >>" + deliveryCharge);
      model.addAttribute("deliveryCharge", deliveryCharge);
      model.addAttribute("pointRate", pointRate);

      List<Integer> extractCartItemCnts = cartService.extractCartItemCnts(cartNum);
      //List<Integer> sales = cartService.extractSales(ivo);
      List<String> cartSizes = cartService.extractCartItemSize(cartNum);
      log.info("extractCartItemCnts >>" + extractCartItemCnts);
      log.info("sales >>" + sales);
      model.addAttribute("extractCartItemCnts", extractCartItemCnts);
      model.addAttribute("sales", sales);
      model.addAttribute("sizes", cartSizes);

      // 인덱스의 범위를 생성하고, 각 인덱스에 대해 두 리스트를 곱하여 새로운 리스트를 생성
      List<Integer> totalPriceList = cartService.totalPriceList(saleList, extractCartItemCnts);
      log.info("totalPriceList" + totalPriceList);
      model.addAttribute("totalPriceList", totalPriceList);

      int totalPrice = cartService.calculateTotalPrice(saleList, extractCartItemCnts);
      log.info("totalPrice >>" + totalPrice);
      model.addAttribute("totalPrice", totalPrice);

      int TotalPriceWithShipping = deliveryCharge + totalPrice;
      log.info(TotalPriceWithShipping);
      model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);

      List<Integer> itemRewardPoints = cartService.itemRewardPoints(saleList, pointRate, extractCartItemCnts);
      log.info("itemRewardPoints >>" + itemRewardPoints);

      model.addAttribute("itemRewardPoints", itemRewardPoints);

      return "/order/cart";
   }

   
   @PostMapping("/chooseDetailCartDelete")
   @ResponseBody
   public String chooseDetailCartDelete(@RequestBody Map<String, Object> requestData) {
      log.info(requestData);
      // JSON 배열을 List<Integer>로 변환
       ObjectMapper objectMapper = new ObjectMapper();
       List<Integer> selectedItems = objectMapper.convertValue(requestData.get("selectedItems"), new TypeReference<List<Integer>>() {});
       int cartNum = (int) requestData.get("cartNum");
       String size = (String) requestData.get("size");
       log.info(selectedItems);
       log.info(cartNum);
       log.info(size);
       cartService.chooseDetailCartDelete(selectedItems, cartNum, size);
      
      return "success";
   }

   @PostMapping("/allDetailCartDelete")
   public void allDetailCartDelete(@RequestParam("cartNum") int cartNum) {
      cartService.allDetailCartDelete(cartNum);
   }

   @PostMapping("/updateExpectedPlusAmount")
   public ResponseEntity<String> updateExpectedPlusAmount(@RequestParam("itemNum") int itemNum, @RequestParam("size")String size) {
      System.out.println("여기 오면서 itemNum : " + itemNum);
      System.out.println("여기 오면서 itemNum : " + size);
      cartService.detailCartCntPlusUpdate(itemNum, size);
      return new ResponseEntity<String>("success", HttpStatus.OK);
   }

   @PostMapping("/updateExpectedMinusAmount")
   public ResponseEntity<String> updateExpectedMinusAmount(@RequestParam("itemNum") int itemNum, @RequestParam("size")String size) {
      System.out.println("여기 오면서 itemNum : " + itemNum);
      System.out.println("여기 오면서 size : " + size);
      cartService.detailCartCntMinusUpdate(itemNum, size);
      return new ResponseEntity<String>("success", HttpStatus.OK);
   }

}