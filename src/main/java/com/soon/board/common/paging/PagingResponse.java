package com.soon.board.common.paging;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PagingResponse<T> {
	private List<T> list = new ArrayList<>();
    private Pagination pagination;

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list.addAll(list);
        this.pagination = pagination;
    }
}
