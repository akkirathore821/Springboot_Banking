package com.bank.jwt_service_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SongResponse {
    private String songName;
}
