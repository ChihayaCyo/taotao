package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 接收商品的id和商品的数量，商品数量如果为空默认为1.调用Service向购物车添加商品。
   展示添加成功页面。
   请求的url：http://localhost:8082/cart/add/144141542125084.html
 * Created by zh on 3/10/2017.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
        return "redirect:/cart/success.html";//接口设计
    }

    @RequestMapping("/success")
    public String showSuccess(){
        return "cartSuccess";
    }

    /**
     * 调用Service取购物车商品列表，把购物车商品传递给jsp。在购物车页面展示商品列表。
     请求的url：http://localhost:8082/cart/cart.html
     返回jsp页面：cart.jsp
     */
    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> list = cartService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "cart";
    }

    /*接收商品id，调用Service删除购物车商品，返回购物车页面。
    请求的url：/cart/delete/${cart.id}.html*/
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        cartService.deleteCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }

}