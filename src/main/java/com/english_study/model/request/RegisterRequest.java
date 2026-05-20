package com.english_study.model.request;


public record RegisterRequest(String username,
                              String fullName,
                              String email,
                              String password) {

}
