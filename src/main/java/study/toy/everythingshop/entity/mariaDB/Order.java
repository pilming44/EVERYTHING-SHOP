package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderNum;       //주문번호
    private Integer userNum;        //사용자번호
    private String orderStatusCd;   //주문상태코드[COM1005]
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자
}
