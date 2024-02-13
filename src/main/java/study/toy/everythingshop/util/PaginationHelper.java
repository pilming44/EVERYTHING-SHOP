package study.toy.everythingshop.util;

import study.toy.everythingshop.dto.SuperClass;

public class PaginationHelper {
    private static final int DEFAULT_RECORD_COUNT_PER_PAGE = 10;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static PaginationInfo configurePagination(SuperClass dto) {
        dto.setCurrentPageNo(Math.max(dto.getCurrentPageNo(), 1));
        dto.setRecordCountPerPage(Math.max(dto.getRecordCountPerPage(), DEFAULT_RECORD_COUNT_PER_PAGE));
        dto.setPageSize(Math.max(dto.getPageSize(), DEFAULT_PAGE_SIZE));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(dto.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(dto.getRecordCountPerPage());
        paginationInfo.setPageSize(dto.getPageSize());
        paginationInfo.setTotalRecordCount(dto.getTotalRecordCount());

        dto.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        dto.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        dto.setTotalPageCount(paginationInfo.getTotalPageCount());

        return paginationInfo;
    }
}
