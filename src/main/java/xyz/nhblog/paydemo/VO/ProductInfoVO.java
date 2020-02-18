package xyz.nhblog.paydemo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -3164443475433055160L;
    @JsonProperty("id")
    private String productId;

    /** 商品名 */
    @JsonProperty("name")
    private String productName;

    /** 商品单价 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品小图 */
    @JsonProperty("icon")
    private String productIcon;

}
