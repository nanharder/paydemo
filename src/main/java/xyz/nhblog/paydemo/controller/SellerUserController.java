package xyz.nhblog.paydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.nhblog.paydemo.constant.CookieConstant;
import xyz.nhblog.paydemo.constant.RedisConstant;
import xyz.nhblog.paydemo.dataobject.SellerInfo;
import xyz.nhblog.paydemo.enums.ResultEnum;
import xyz.nhblog.paydemo.service.SellerService;
import xyz.nhblog.paydemo.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);

        // 验证是否有卖家信息
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        // redis缓存token
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // cookie保存token
        CookieUtil.set(response, CookieConstant.Token, token, CookieConstant.EXPIRE);

        return new ModelAndView("redirect:http://selldemonh.natapp1.cc//sell/seller/order/list", map);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
    // 1.从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.Token);
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            CookieUtil.set(response, CookieConstant.Token, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }
}
