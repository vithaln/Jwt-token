package com.vitu.test.payload;

import java.util.List;

import com.vitu.test.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagebleResponse<T> {

private List<UserDto> contents;
private int pageNumber;
private int pageSize;
private long totalElements;
private long totalPages;
private boolean lastPage;
}
