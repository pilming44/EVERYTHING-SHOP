package study.toy.everythingshop.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class CommonCode {
    private String codeClass;   //코드분류
    private String codeNum;     //코드번호
    private String codeNm;      //코드명
    private String registerDt;  //등록일자
    private String changeDt;    //수정일자
}
