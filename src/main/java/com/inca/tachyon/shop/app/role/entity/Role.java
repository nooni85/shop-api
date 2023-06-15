package com.inca.tachyon.shop.app.role.entity;

import com.inca.tachyon.shop.auth.entity.Roleable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Role implements Roleable {
    Long roleNo;
    String name;

    Date updatedAt;
    Date createdAt;
}
