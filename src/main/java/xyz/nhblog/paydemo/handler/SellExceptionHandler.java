package xyz.nhblog.paydemo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.nhblog.paydemo.VO.ResultVO;
import xyz.nhblog.paydemo.exception.SellException;
import xyz.nhblog.paydemo.exception.SellerAuthorizeException;
import xyz.nhblog.paydemo.utils.ResultVOUtil;

@ControllerAdvice
public class SellExceptionHandler {

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat("http://selldemonh.natapp1.cc//sell/seller/login"));
    }
}
