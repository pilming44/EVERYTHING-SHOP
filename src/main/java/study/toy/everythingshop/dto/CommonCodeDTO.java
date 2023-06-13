package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeDTO {
    private String codeClass;   //코드분류
    private String codeNum;     //코드번호
    private String codeNm;      //코드명
    private String registerDt;  //등록일자
    private String changeDt;    //수정일자
    private String order;       //정렬순서 (ASC/DESC)
}
