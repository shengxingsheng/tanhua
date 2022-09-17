package com.tanhua.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-17 14:31
 */
@Data
public class MovementDto implements Serializable {
    private String textContent;
    private String location;
    private String longitude;
    private String latitude;
}
