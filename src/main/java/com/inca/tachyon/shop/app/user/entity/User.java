package com.inca.tachyon.shop.app.user.entity;

import com.inca.tachyon.shop.auth.entity.Authable;
import com.inca.tachyon.shop.auth.entity.Roleable;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Authable {
    long userNo;
    String email;
    List<Roleable> roles;
    String password;
    Date updatedAt;
    Date createdAt;
}
