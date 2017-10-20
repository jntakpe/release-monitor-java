package com.github.jntakpe.releasemonitorjava.web;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUtilsTest {

    @Test
    public void idFromPath_shouldRetrieveId() {
        ObjectId id = new ObjectId();
        MockServerRequest request = MockServerRequest.builder().pathVariable("id", id.toString()).build();
        assertThat(RequestUtils.idFromPath(request)).isEqualTo(id);
    }

}