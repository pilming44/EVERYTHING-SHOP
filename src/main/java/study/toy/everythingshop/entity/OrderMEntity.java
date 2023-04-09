package study.toy.everythingshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMEntity {
    private Long orderNum;
    private Long userNum;
    private String orderStts;
    private String registerDt;
    private String changeDt;
}
