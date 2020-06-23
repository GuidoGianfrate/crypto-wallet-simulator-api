package com.softvision.spring_demo.students.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
public class PaginationResponse {
    int total;
    LinkedHashMap coins;
    int offset;
    int limit;
}
