package xyz.nhblog.paydemo.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -2872324598775114876L;

    private Integer code;

    private String msg;

    private T data;

}
