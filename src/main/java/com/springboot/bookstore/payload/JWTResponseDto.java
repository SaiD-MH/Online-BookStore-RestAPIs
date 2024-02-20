package com.springboot.bookstore.payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";


}
