package com.padelsync.padelsync_core.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority")String role) {

    }

}
