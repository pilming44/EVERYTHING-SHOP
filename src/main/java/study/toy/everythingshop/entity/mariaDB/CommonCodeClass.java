package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeClass {
    private String codeClass;       //코드분류
    private String codeClassNm;     //코드분류명
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자
}
