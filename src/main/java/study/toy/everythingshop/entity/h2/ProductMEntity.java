package study.toy.everythingshop.entity.h2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class ProductMEntity {
    private Long productNum;
    private Long userNum;
    private String productName;
    private Long quantity;
    private Long price;
    private String productStts;
    private String registerDt;
    private String changeDt;
}
