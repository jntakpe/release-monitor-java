package com.github.jntakpe.releasemonitorjava.web;

import org.bson.types.ObjectId;
import org.springframework.web.reactive.function.server.ServerRequest;

final class RequestUtils {

    private RequestUtils() {
    }

    static ObjectId idFromPath(ServerRequest serverRequest) {
        return new ObjectId(serverRequest.pathVariable("id"));
    }

}
