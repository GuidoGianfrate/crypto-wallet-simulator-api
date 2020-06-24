package com.softvision.crypto.wallets.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class PaginationResponse {
    int total;
    LinkedHashMap coins;
    int offset;
    int limit;
}
