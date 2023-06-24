package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SuperClass {
    private int rowNum;

    /*페이징용 변수들 시작*/
    private int recordCountPerPage; //한 페이지에 보여주는 데이터 건수
    private int pageSize;           //한번에 보여주는 페이지수
    private int currentPageNo;      //현재페이지
    private int totalRecordCount;   //총 데이터 건수
    private int totalPageCount;     //총페이지수
    private int firstRecordIndex;   //시작인덱스
    private int lastRecordIndex;    //마지막인덱스
    /*페이징용 변수들 끝*/

    private String registerDt;          //등록일자
    private String changeDt;            //수정일자
}
