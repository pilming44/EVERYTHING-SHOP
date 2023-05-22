package study.toy.everythingshop.entity.h2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEntity {
    private Long orderProductNum;
    private Long orderNum;
    private Long productNum;
    private Long quantity;
    private String registerDt;
    private String changeDt;
}
