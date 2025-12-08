package com.klim.birukou.book_hub.service;

import org.springframework.web.client.RestClient;

public class AbstractPageableClientService {

    protected final RestClient restClient;

    public AbstractPageableClientService(RestClient restClient) {
        this.restClient = restClient;
    }
}
