package com.welab.k8s_backend_post.common.exception;

public class NotFound extends ClientError {
    public NotFound(String errorMessage) {
        this.errorCode = "NotFound";
        this.errorMessage = errorMessage;
    }
}